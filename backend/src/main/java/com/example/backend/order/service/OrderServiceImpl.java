package com.example.backend.order.service;

import static com.example.backend.order.dto.CartResponse.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.example.backend.common.annotation.RedLock;
import com.example.backend.common.client.toss.TossWebClient;
import com.example.backend.common.client.toss.dto.TossPaymentRequestDto;
import com.example.backend.common.client.toss.dto.TossPaymentResponseDto;
import com.example.backend.common.enums.SimpleResponseMessage;
import com.example.backend.common.enums.UseStatus;
import com.example.backend.common.exception.ValidationException;
import com.example.backend.common.util.RandomUtil;
import com.example.backend.common.util.TimeUtil;
import com.example.backend.dish.entity.Dish;
import com.example.backend.dish.entity.Choice;
import com.example.backend.dish.entity.DishCategory;
import com.example.backend.dish.repository.DishRepository;
import com.example.backend.dish.repository.ChoiceRepository;
import com.example.backend.etc.entity.Restaurant;
import com.example.backend.order.dto.*;
import com.example.backend.order.dto.CartRequest.*;
import com.example.backend.order.dto.OrderRequest.*;
import com.example.backend.order.dto.OrderResponse.*;
import com.example.backend.order.entity.*;
import com.example.backend.order.enums.OrderStatus;
import com.example.backend.order.enums.OrderStatusGroup;
import com.example.backend.order.enums.PaymentMethod;
import com.example.backend.order.enums.PaymentStatus;
import com.example.backend.order.repository.*;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.example.backend.common.exception.ErrorCode;
import com.example.backend.common.exception.JDQRException;
import com.example.backend.common.redis.repository.RedisHashRepository;
import com.example.backend.common.util.GenerateLink;
import com.example.backend.table.entity.Table;
import com.example.backend.table.repository.TableRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final TableRepository tableRepository;
    private final GenerateLink generateLink;
    private final RedisHashRepository redisHashRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemChoiceRepository orderItemChoiceRepository;
    private final DishRepository dishRepository;
    private final ChoiceRepository choiceRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentDetailRepository paymentDetailRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final TossWebClient tossWebClient;
    private final ParentOrderRepository parentOrderRepository;

    /**
     * tableName으로 qrCode를 찾아서, 해당 코드에 token을 더한 주소를 반환
     *
     * @param tableId
     * @return
     */
    @Override
    public String redirectUrl(String tableId, String uuid) {
        //1. table을 찾는다
        Table table = tableRepository.findById(tableId)
            .orElseThrow(() -> new JDQRException(ErrorCode.FUCKED_UP_QR));

        log.warn("table : {}", table);

        // 현재 url이 유효하지 않다면 예외를 반환한다
        String targetUrl = GenerateLink.AUTH_PREFIX + "/" + tableId + "/" + uuid;
        if (!targetUrl.equals(table.getQrCode())) {
            throw new JDQRException(ErrorCode.FUCKED_UP_QR);
        }

        //2. table의 링크에 token을 생성한다
        String authLink = generateLink.createAuthLink(table.getId());

        return authLink;
    }

    /**
     * 장바구니에 물품을 담는 메서드.
     * 현재 테이블의 장바구니에 유저별로 담은 음식을 Redis에 저장한다
     * 현재 Redis의 저장형식은 다음과 같다
     * <p>
     * <tableId,<userId,<hashCode,CartDto>>>
     *
     * @param tableId
     * @param productInfo
     * @throws JDQRException if order status is PAY_WAITING
     */

    @Override
    @Transactional
    // @RedLock(key = "'table:' + #tableId", waitTime = 5000L,leaseTime = 1000L)
    public void addItem(String tableId, CartDto productInfo) {

        // 0. validation 로직 추가
        // 현재 테이블의 parentOrder가 결제 대기 중인 상태가 아니어야 함
        Optional<ParentOrder> optionalParentOrder = parentOrderRepository.findFirstByTableIdOrderByIdDesc(tableId);
        if (optionalParentOrder.isPresent() && optionalParentOrder.get().getOrderStatus().equals(OrderStatus.PAY_WAITING)) {
            throw new JDQRException(ErrorCode.INVALID_ORDER);
        }

        // log.warn("productInfo : {}", productInfo);
        // 1. productInfo에서 id를 꺼내서 그런 메뉴가 있는지부터 확인
        Dish dish = dishRepository.findById(productInfo.getDishId())
            .orElseThrow(() -> new ValidationException(List.of("존재하지 않는 메뉴입니다")));

        // 1.5 그런 메뉴가 있으면, 식당에 그런 메뉴를 파는지도 확인
        Table table = tableRepository.findById(tableId)
                .orElseThrow(() -> new ValidationException(List.of("존재하지 않는 테이블입니다")));

        int restaurantId = table.getRestaurantId();
        DishCategory dishCategory = dish.getDishCategory();
        Restaurant restaurant = dishCategory.getRestaurant();
        if(restaurant.getId() != restaurantId){
            throw new ValidationException(List.of("존재하지 않는 메뉴입니다"));
        }

        // 1.6. 테이블이 비어 있을 경우, 테이블의 상태를 '이용 중'으로 변경
        if (table.getUseStatus().equals(UseStatus.AVAILABLE)) {
            table.setUseStatus(UseStatus.OCCUPIED);
            tableRepository.save(table);
        }

        // 2. 테이블 장바구니에 물품을 담는다
        //<tableId,<userId,<hashCode,항목>>>
        String key = "table::" + tableId;
        // log.warn("key : {}",key);
        // log.warn("userID : {}",productInfo.getUserId());
        Map<Integer, CartDto> cachedCartData = redisHashRepository.getCartDatas(key, productInfo.getUserId());

        UserCartItemDto userCartItemDto = new UserCartItemDto();

        // log.warn("cachedCartData : {}",cachedCartData);
        if (!ObjectUtils.isEmpty(cachedCartData) && cachedCartData.containsKey(productInfo.hashCode())) { // 1. 기존에 동일한 물품이 있어서 거기에 더해지는 경우 -> 지금 담는 hashCode와 비교하여 동일한 것을 찾아서 추가

            CartDto cartData = cachedCartData.get(productInfo.hashCode());
            int currentQuantity = cartData.getQuantity();
            int newQuantity = currentQuantity + productInfo.getQuantity();
            if (newQuantity < 0) newQuantity = 0;
            // log.warn("newQuantity : {}",newQuantity);
            cartData.setQuantity(newQuantity);
            // log.warn("productInfo.hashCode() : {}",productInfo.hashCode());
            // log.warn("cartData : {}",cartData);

            cachedCartData.put(productInfo.hashCode(), cartData);

            redisHashRepository.saveHashData(key, cartData.getUserId(), cachedCartData, 20L);
        } else { // 2. 새로운 물품인 경우 -> 리스트에 추가
            if (ObjectUtils.isEmpty(cachedCartData)) cachedCartData = new ConcurrentHashMap<>();

            // 여기서 저장한 품목에 대한 가격을 받아서 채워넣어야 함
            setDishInfo(productInfo);

            // 새롭게 추가된 품목을 표시
            userCartItemDto.setItem(productInfo);
            userCartItemDto.setUserId(productInfo.getUserId());

            // log.warn("userCartItemDto : {}", userCartItemDto);

            cachedCartData.put(productInfo.hashCode(), productInfo);
            redisHashRepository.saveHashData(key, productInfo.getUserId(), cachedCartData, 20L);
        }
        // 3. 전송할 데이터를 생성한다

        // 3-1. 현재 테이블의 장바구니를 가지고온다.
        // 맵은 (userId,해당 유저가 담은 항목들) 의 형식
        Map<String, Map<Integer, CartDto>> allCartDatas = redisHashRepository.getAllCartDatas(tableId);

        // 3-2. 현재 테이블과 연결된 사람수를 받아온다
        Integer subscriberSize = redisHashRepository.getCurrentUserCnt(tableId);

		// // 3-3. 현재 테이블의 이름을 가져오기위해 테이블을 조회한다
		// Table table = tableRepository.findById(tableId)
		// 	.orElseThrow(() -> new ValidationException(List.of("해당 테이블이 존재하지 않습니다.")));
        //
        // log.warn("table : {}", table);

        // 3-4. 최종적으로 전송할 데이터
        List<CartDto> cartList = allCartDatas.values().stream()
            .flatMap(map -> map.values().stream())
            .sorted(Comparator.comparing(CartDto::getOrderedAt).reversed())
            .collect(Collectors.toList());

        int totalPrice = 0;
        int totalQuantity = 0;
        for (CartDto cartData : cartList) {
            totalPrice += cartData.getPrice() * cartData.getQuantity();
            totalQuantity += cartData.getQuantity();
        }

        CartInfo sendData = CartInfo.of(cartList, table.getName(), subscriberSize, totalPrice, totalQuantity, userCartItemDto);

        // notificationService.sentToClient(tableId,sendData);
        // log.warn("sendData : {}", sendData);
        messagingTemplate.convertAndSend("/sub/cart/" + tableId, sendData);
    }

    private void setDishInfo(CartDto productInfo) {
        Dish dish = dishRepository.findById(productInfo.getDishId())
            .orElseThrow(() -> new JDQRException(ErrorCode.DISH_NOT_FOUND));

        // log.warn("dish : {}", dish);

        // log.warn("choiceIds : {}", productInfo.getChoiceIds());
        List<Choice> choices = choiceRepository.findAllById(productInfo.getChoiceIds());
        List<String> choiceNames = choices.stream().map(Choice::getName)
            .toList();

        int choicePrice = choices.stream().map(Choice::getPrice).mapToInt(p -> p).sum();
        int price = dish.getPrice() + choicePrice;

        productInfo.setPrice(price);
        productInfo.setDishName(dish.getName());
        productInfo.setDishCategoryName(dish.getDishCategory().getName());
        productInfo.setDishCategoryId(dish.getDishCategory().getId());
        productInfo.setChoiceNames(choiceNames);
    }

    /**
     * 테이블의 장바구니에서 productInfo를 가진 품목을 제거한다
     *
     * @param tableId
     * @param productInfo
     */
    @Override
    // @RedLock(key = "'table:' + #tableId",waitTime = 5000L, leaseTime = 1000L)
    public void deleteItem(String tableId, CartDto productInfo) {

        // 1. productInfo에서 id를 꺼내서 그런 메뉴가 있는지부터 확인
        dishRepository.findById(productInfo.getDishId())
            .orElseThrow(() -> new ValidationException(List.of("존재하지 않는 메뉴입니다")));

        // 2. 해당 유저의 장바구니에서 제거한다
        //<tableId,<userId,<hashCode,항목>>>
        String key = "table::" + tableId;
        // log.warn("userID : {}",productInfo.getUserId());

        // 유저가 담은 <hashCode,품목> 의 맵을 가지고온다
        Map<Integer, CartDto> cachedCartData = redisHashRepository.getCartDatas(key, productInfo.getUserId());
        // log.warn("cachedCartData : {}",cachedCartData);

        // log.warn("productInfo.hashCode() : {}",productInfo.hashCode());
        CartDto findData = cachedCartData.getOrDefault(productInfo.hashCode(), null);
        // log.warn("findData : {}",findData);

        if (!ObjectUtils.isEmpty(findData)) {
            // 해당 유저의 장바구니에 품목이 있다면 삭제한다
            cachedCartData.remove(productInfo.hashCode());
            // log.warn("cachedCardData After : {}",cachedCartData);
            redisHashRepository.saveHashData(key, productInfo.getUserId(), cachedCartData, 20L);

        } else {
            throw new ValidationException(List.of("장바구니에 존재하지 않는 품목입니다"));
        }
        // 3. 전송할 데이터를 생성한다

        // 3-1. 현재 테이블의 장바구니를 가지고온다.
        // 맵은 (userId,해당 유저가 담은 항목들) 의 형식
        Map<String, Map<Integer, CartDto>> allCartDatas = redisHashRepository.getAllCartDatas(tableId);
        // log.warn("allCartDatas : {}",allCartDatas);

        // 3-2. 현재 테이블과 연결된 사람수를 받아온다
        Integer subscriberSize = redisHashRepository.getCurrentUserCnt(tableId);

        // 3-3. 현재 테이블의 이름을 가져오기위해 테이블을 조회한다
        Table table = tableRepository.findById(tableId).orElseThrow(() -> new ValidationException(List.of("존재하지 않는 테이블입니다")));

        // 3-4. 최종적으로 전송할 데이터
        List<CartDto> cartList = allCartDatas.values().stream()
            .flatMap(map -> map.values().stream())
            .collect(Collectors.toList());

        int totalPrice = 0;
        int totalQuantity = 0;
        for (CartDto cartData : cartList) {
            totalPrice += cartData.getPrice() * cartData.getQuantity();
            totalQuantity += cartData.getQuantity();
        }

        CartInfo sendData = CartInfo.of(cartList, table.getName(), subscriberSize, totalPrice, totalQuantity, null);

        // notificationService.sentToClient(tableId,sendData);
        messagingTemplate.convertAndSend("/sub/cart/" + tableId, sendData);
    }

    /**
     * 유저가 담은 상품의 정보를 db에 저장한다
     * orders, order_items, order_item_options 테이블 전체를 포함
     *
     * @param tableId : 유저가 주문하는 테이블의 id
     * @return : 성공/실패 여부를 담은 문자열
     */
    @Override
    @Transactional
    public SimpleResponseMessage saveWholeOrder(String tableId) {

        Map<String, Map<Integer, CartDto>> allCartDatas = redisHashRepository.getAllCartDatas(tableId);

        List<CartDto> cartDatas = new ArrayList<>();
        for (Map<Integer, CartDto> cartMap : allCartDatas.values()) {
            cartDatas.addAll(cartMap.values());
        }

        // redis에 아직 데이터가 없을 경우
        if (cartDatas == null || cartDatas.isEmpty()) {
            return SimpleResponseMessage.ORDER_ITEM_EMPTY;
        }

        // 0. parentOrders를 가져온다. 없을 경우, 새로 만든다.
        ParentOrder parentOrder;
        Optional<ParentOrder> optionalParentOrder = parentOrderRepository.findFirstByTableIdOrderByIdDesc(tableId);
        if (optionalParentOrder.isPresent() && optionalParentOrder.get().getOrderStatus().equals(OrderStatus.PENDING)) {
            parentOrder = optionalParentOrder.get();
        } else {
            parentOrder = ParentOrder.builder()
                .tableId(tableId)
                .orderStatus(OrderStatus.PENDING)
                .paymentMethod(PaymentMethod.UNDEFINED)
                .build();

            parentOrder = parentOrderRepository.save(parentOrder);
        }

        // 1. orders table에 데이터를 추가한다
        Order order = saveOrder(cartDatas, tableId, parentOrder);

		// 2. order_items에 데이터를 추가한다
		List<OrderItem> orderItems = saveOrderItems(order, cartDatas);

		// 3. order_item_options에 데이터를 추가한다
		saveOrderItemOptions(orderItems, cartDatas);

		// 4. redis에서 정보를 제거한다
		String key = "table::" + tableId;
		redisHashRepository.removeKey(key);

		return SimpleResponseMessage.ORDER_SUCCESS;
	}

    /**
     * 결제 방식에 따라 결제를 수행한 후, 성공 여부를 반환하는 api
     *
     * @param tableId           : 결제를 시도하는 고객의 tableId
     * @param paymentRequestDto : 결제에 필요한 정보를 담고 있는 request dto
     * @return : 결제 성공 여부를 담은 메시지
     */
    @Override
    @Transactional
    public InitialPaymentResponseDto payForOrder(String tableId, PaymentRequestDto paymentRequestDto) {
        // 1. 결제 방식을 확인한다
        PaymentMethod paymentMethod = paymentRequestDto.type();
        Integer serveNum = paymentRequestDto.serveNum();
        Integer peopleNum = paymentRequestDto.peopleNum();

        // 2. 해당 테이블의 가장 최근 parentOrder를 확인하고, 결제 방식을 업데이트시킨다
        ParentOrder parentOrder = updatePaymentMethodOfOrder(tableId, paymentMethod, serveNum, peopleNum);

        // 3. paymentMethod 에 따라 다르게 재고 관리를 시행
        Payment payment;
        // 3-1. paymentMethod 가 MONEY_DIVIDE 일 경우
        if (paymentMethod.equals(PaymentMethod.MONEY_DIVIDE)) {
            Integer totalPurchaseAmount = getTotalPurchaseAmount(parentOrder);
            payment = createBasePaymentForMoneyDivide(parentOrder, totalPurchaseAmount, paymentRequestDto);
        }
        // 3-2. paymentMethod 가 MENU_DIVIDE 일 경우
        else {
            payment = createBasePaymentForMenuDivide(paymentRequestDto);
        }

        return InitialPaymentResponseDto.builder()
            .tossOrderId(payment.getTossOrderId())
            .amount(payment.getAmount())
            .build();
    }

    /**
     * 클라이언트에서 toss 결제 위젯으로 성공적으로 결제를 한 뒤, 반환되는 parameter들을 받아 결제 완료 처리를 한다.
     * 해당 결제 내역에 맞게 DB의 상태를 변경 후, 토스 결제 확정 api를 호출
     *
     * @param tableId                     : 결제를 시도한 table의 id
     * @param tossOrderId                 : toss 주문번호
     * @param status                      : 결제 결과
     * @param simpleTossPaymentRequestDto : toss paymentKey + amount(결제 금액)
     * @return : 반환 메시지
     */
    @Override
    @Transactional
    public SimpleResponseMessage finishPayment(String tableId, String tossOrderId, String status, SimpleTossPaymentRequestDto simpleTossPaymentRequestDto) {
        if (status.equals("success")) {

            // 1. 주어진 결제 내역에 맞게 DB의 상태를 변경
            SimpleResponseMessage simpleResponseMessage = updatePaymentStatusToSuccess(tossOrderId, simpleTossPaymentRequestDto);

            // DB 업데이트가 성공적으로 동작하지 않은 경우, Error Message 반환
            if (!simpleResponseMessage.equals(SimpleResponseMessage.PAYMENT_SUCCESS)) return simpleResponseMessage;

            // 2. toss confirm api 호출
            TossPaymentRequestDto tossPaymentRequestDto = TossPaymentRequestDto.from(simpleTossPaymentRequestDto, tossOrderId);
            TossPaymentResponseDto tossPaymentResponseDto = tossWebClient.requestPayment(tossPaymentRequestDto);

            // toss api가 실패할 경우 -> 에러 던지기
            if (!tossPaymentResponseDto.getSuccess()) return SimpleResponseMessage.PAYMENT_FAILED;

            // 3. 주문에 대한 결제가 모두 끝났는지를 체크
            return checkOrderIsFinished(tableId, tossOrderId);
        } else {
            return SimpleResponseMessage.PAYMENT_FAILED;
        }
    }

    @Override
    public TotalOrderInfoResponseDto getOrderInfo(String tableId) {

        // 1. 현재 조회하기를 원하는 테이블의 정보 받아오기
        Table table = tableRepository.findById(tableId).orElseThrow(() -> new JDQRException(ErrorCode.TABLE_NOT_FOUND));

        //
        Optional<ParentOrder> optionalParentOrder = parentOrderRepository.findFirstByTableIdOrderByIdDesc(tableId);
        if (optionalParentOrder.isEmpty() || optionalParentOrder.get().getOrderStatus().getGroup().equals(OrderStatusGroup.FINISHED)) {
            return getBaseOrderInfo(table);
        }

        ParentOrder parentOrder = optionalParentOrder.get();

        // 2. 테이블을 join한 결과 받아오기
        List<OrderResponseVo> orderResponseVos = orderRepository.findWholeOrderInfos(parentOrder);


        // 3. stream의 groupingBy 옵션을 이용하여 join된 결과물을 종류별로 분리
        // orderId -> orderItemId List<OrderResponseVo> 구조
        Map<Integer, Map<Integer, List<OrderResponseVo>>> orderGroup = orderResponseVos.stream()
            .collect(Collectors.groupingBy(OrderResponseVo::getOrderId,
                Collectors.groupingBy(OrderResponseVo::getOrderItemId)));

        // 4. table과 orderResponseVos를 이용하여, TotalOrderInfoResponseDto 구하기
        // 4-1. OrderInfoResponseDto 데이터 만들기
        List<OrderInfoResponseDto> orderInfoResponseDtos = new ArrayList<>(orderGroup.entrySet().stream()
            .map(entry -> {
                Integer orderId = entry.getKey();
                Map<Integer, List<OrderResponseVo>> orderGroupByOrderItemId = entry.getValue();

                // 4-2-1. dishInfoResponseDtos 리스트 생성
                List<DishInfoResponseDto> dishInfoResponseDtos = orderGroupByOrderItemId.entrySet().stream()
                    .map(orderItemEntry -> {
                        Integer orderItemId = orderItemEntry.getKey();
                        List<OrderResponseVo> values = orderItemEntry.getValue();

                        // 메뉴에 대한 기본 정보를 담고 있는 class
                        OrderResponseVo baseOrder = values.get(0);

                        // 옵션들에 대한 정보를 담는 list 구하기
                        List<OptionDetailDto> options = new ArrayList<>();

                        for (OrderResponseVo option : values) {
                            if (option.getOptionId() != null) {
                                options.add(OptionDetailDto.builder()
                                    .optionId(option.getOptionId())
                                    .optionName(option.getOptionName())
                                    .choiceId(option.getChoiceId())
                                    .choiceName(option.getChoiceName())
                                    .price(option.getChoicePrice())
                                    .build()
                                );
                            }
                        }


                        int optionPrice = options.stream()
                            .mapToInt(OptionDetailDto::getPrice)
                            .sum();

                        // baseOrder와 options를 결합하여 DishInfoResponseDto 만들기
                        return DishInfoResponseDto.builder()
                            .orderItemId(orderItemId)
                            .dishId(baseOrder.getDishId())
                            .userId(baseOrder.getUserId())
                            .dishName(baseOrder.getDishName())
                            .dishCategoryId(baseOrder.getDishCategoryId())
                            .dishCategoryName(baseOrder.getDishCategoryName())
                            .price(baseOrder.getDishPrice())
							.totalPrice(baseOrder.getDishPrice() + optionPrice)
                            .options(options)
                            .quantity(baseOrder.getQuantity())
                            .build();
                    })
                    .toList();

                // 현재 order의 price 계산
                int orderPrice = dishInfoResponseDtos.stream()
                    .mapToInt(dishInfoResponseDto -> dishInfoResponseDto.totalPrice() * dishInfoResponseDto.quantity())
                    .sum();

                int dishCnt = dishInfoResponseDtos.stream()
                    .mapToInt(DishInfoResponseDto::quantity)
                    .sum();

                // orderId와 dishInfoResponseDtos를 결합
                return OrderInfoResponseDto.builder()
                    .orderId(orderId)
                    .price(orderPrice)
                    .dishCnt(dishCnt)
                    .dishes(dishInfoResponseDtos)
                    .build();
            })
            .toList());

        // orders 정렬
        orderInfoResponseDtos.sort((Comparator.comparingInt(OrderInfoResponseDto::orderId)));

        // totalDishCount, totalPrice 구하기
        int totalDishCount = orderInfoResponseDtos.stream()
            .mapToInt(OrderInfoResponseDto::dishCnt)
            .sum();
        int totalPrice = orderInfoResponseDtos.stream()
            .mapToInt(OrderInfoResponseDto::price)
            .sum();

        // table과 orderInfoResponseDtos를 결합
        return TotalOrderInfoResponseDto.builder()
            .tableName(table.getName())
            .dishCnt(totalDishCount)
            .price(totalPrice)
            .orders(orderInfoResponseDtos)
            .build();
    }

    @Override
    public TotalOrderInfoResponseDto getTotalPaymentInfo(String tableId) {
        // 1. 현재 조회하기를 원하는 테이블의 정보 받아오기
        Table table = tableRepository.findById(tableId).orElseThrow(() -> new JDQRException(ErrorCode.TABLE_NOT_FOUND));

        // 주문이 비어 있거나, 이미 결제된 주문일 경우 error throw
        Optional<ParentOrder> optionalParentOrder = parentOrderRepository.findFirstByTableIdOrderByIdDesc(tableId);
        if (optionalParentOrder.isEmpty() || optionalParentOrder.get().getOrderStatus().getGroup().equals(OrderStatusGroup.FINISHED)) {
            throw new JDQRException(ErrorCode.ORDER_ALREADY_PAID);
        }

        ParentOrder parentOrder = optionalParentOrder.get();

        // 2. 테이블을 join한 결과 받아오기
        // 결제 방식에 따라 다르게 join 해와야 함
        PaymentMethod paymentMethod = parentOrder.getPaymentMethod();

        List<OrderResponseVo> orderResponseVos = orderRepository.findWholeOrderInfos(parentOrder);

        int userCnt = orderResponseVos.stream()
            .map(OrderResponseVo::getUserId)
            .collect(Collectors.toSet())
            .size();


        // 2-1. N빵 결제 방식일 경우
        if (paymentMethod.equals(PaymentMethod.MONEY_DIVIDE)) {
            List<Payment> payments = paymentRepository.findAllByParentOrder(parentOrder);
            int paidAmount = payments.stream()
                .mapToInt(Payment::getAmount)
                .sum();

            // 3. stream의 groupingBy 옵션을 이용하여 join된 결과물을 종류별로 분리
            // orderId -> dishId -> List<OrderResponseVo> 구조
            Map<Integer, Map<Integer, List<OrderResponseVo>>> orderGroup = orderResponseVos.stream()
                .collect(Collectors.groupingBy(OrderResponseVo::getOrderId,
                    Collectors.groupingBy(OrderResponseVo::getOrderItemId)));

            // 4. table과 orderResponseVos를 이용하여, TotalOrderInfoResponseDto 구하기
            // 4-1. OrderInfoResponseDto 데이터 만들기
            List<OrderInfoResponseDto> orderInfoResponseDtos = new ArrayList<>(orderGroup.entrySet().stream()
                .map(entry -> {
                    Integer orderId = entry.getKey();
                    Map<Integer, List<OrderResponseVo>> orderGroupByOrderItemId = entry.getValue();

                    // 4-2-1. dishInfoResponseDtos 리스트 생성
                    List<DishInfoResponseDto> dishInfoResponseDtos = orderGroupByOrderItemId.entrySet().stream()
                        .map(orderItemEntry -> {
                            Integer orderItemId = orderItemEntry.getKey();
                            List<OrderResponseVo> values = orderItemEntry.getValue();

                            // 메뉴에 대한 기본 정보를 담고 있는 class
                            OrderResponseVo baseOrder = values.get(0);

                            // 옵션들에 대한 정보를 담는 list 구하기
                            List<OptionDetailDto> options = new ArrayList<>();

                            for (OrderResponseVo option : values) {
                                if (option.getOptionId() != null) {
                                    options.add(OptionDetailDto.builder()
                                        .optionId(option.getOptionId())
                                        .optionName(option.getOptionName())
                                        .choiceId(option.getChoiceId())
                                        .choiceName(option.getChoiceName())
                                        .price(option.getChoicePrice())
                                        .build()
                                    );
                                }
                            }

                            int optionPrice = options.stream()
                                .mapToInt(OptionDetailDto::getPrice)
                                .sum();

                            // baseOrder와 options를 결합하여 DishInfoResponseDto 만들기
                            return DishInfoResponseDto.builder()
                                .orderItemId(orderItemId)
                                .dishId(baseOrder.getDishId())
                                .userId(baseOrder.getUserId())
                                .dishName(baseOrder.getDishName())
                                .dishCategoryId(baseOrder.getDishCategoryId())
                                .dishCategoryName(baseOrder.getDishCategoryName())
                                .price(baseOrder.getDishPrice())
                                .totalPrice(baseOrder.getDishPrice() + optionPrice)
                                .options(options)
                                .quantity(baseOrder.getQuantity())
                                .build();
                        })
                        .toList();

                    // 현재 order의 price 계산
                    int orderPrice = dishInfoResponseDtos.stream()
                        .mapToInt(dishInfoResponseDto -> dishInfoResponseDto.totalPrice() * dishInfoResponseDto.quantity())
                        .sum();

                    int dishCnt = dishInfoResponseDtos.stream()
                        .mapToInt(DishInfoResponseDto::quantity)
                        .sum();

                    // orderId와 dishInfoResponseDtos를 결합
                    return OrderInfoResponseDto.builder()
                        .orderId(orderId)
                        .price(orderPrice)
                        .dishCnt(dishCnt)
                        .dishes(dishInfoResponseDtos)
                        .build();
                })
                .toList());

            // orders 정렬
            orderInfoResponseDtos.sort((Comparator.comparingInt(OrderInfoResponseDto::orderId)));

            // totalDishCount, totalPrice 구하기
            int totalDishCount = orderInfoResponseDtos.stream()
                .mapToInt(OrderInfoResponseDto::dishCnt)
                .sum();
            int totalPrice = orderInfoResponseDtos.stream()
                .mapToInt(OrderInfoResponseDto::price)
                .sum();

            // table과 orderInfoResponseDtos를 결합
            return TotalOrderInfoResponseDto.builder()
                .tableName(table.getName())
                .dishCnt(totalDishCount)
                .userCnt(userCnt)
                .price(totalPrice)
                .restPrice(totalPrice - paidAmount)
                .orders(orderInfoResponseDtos)
                .build();
        }
        // 2-2. 메뉴별 결제 방식일 경우
        else {
            List<PaymentResponseVo> paymentResponseVos = orderRepository.findWholePaymentInfos(parentOrder);

            HashMap<Integer, Integer> orderItemToPaidQuantityMap = new HashMap<>();
            for (PaymentResponseVo paymentResponseVo : paymentResponseVos) {

                Integer key = paymentResponseVo.getOrderItemId();
                orderItemToPaidQuantityMap.put(key, orderItemToPaidQuantityMap.getOrDefault(key, 0) + paymentResponseVo.getQuantity());
            }

            // 3. stream의 groupingBy 옵션을 이용하여 join된 결과물을 종류별로 분리
            // orderId -> orderItemId -> List<OrderResponseVo> 구조
            Map<Integer, Map<Integer, List<OrderResponseVo>>> orderGroup = orderResponseVos.stream()
                .collect(Collectors.groupingBy(OrderResponseVo::getOrderId,
                    Collectors.groupingBy(OrderResponseVo::getOrderItemId)));

            // 4. table과 orderResponseVos를 이용하여, TotalOrderInfoResponseDto 구하기
            // 4-1. OrderInfoResponseDto 데이터 만들기
            List<OrderInfoResponseDto> orderInfoResponseDtos = new ArrayList<>(orderGroup.entrySet().stream()
                .map(entry -> {
                    Integer orderId = entry.getKey();
                    Map<Integer, List<OrderResponseVo>> orderGroupByOrderItemId = entry.getValue();

                    // 4-2-1. dishInfoResponseDtos 리스트 생성
                    List<DishInfoResponseDto> dishInfoResponseDtos = orderGroupByOrderItemId.entrySet().stream()
                        .map(orderItemEntry -> {
                            Integer orderItemId = orderItemEntry.getKey();
                            List<OrderResponseVo> values = orderItemEntry.getValue();

                            // 메뉴에 대한 기본 정보를 담고 있는 class
                            OrderResponseVo baseOrder = values.get(0);

                            // 옵션들에 대한 정보를 담는 list 구하기
                            List<OptionDetailDto> options = new ArrayList<>();

                            for (OrderResponseVo option : values) {
                                if (option.getOptionId() != null) {
                                    options.add(OptionDetailDto.builder()
                                        .optionId(option.getOptionId())
                                        .optionName(option.getOptionName())
                                        .choiceId(option.getChoiceId())
                                        .choiceName(option.getChoiceName())
                                        .price(option.getChoicePrice())
                                        .build()
                                    );
                                }
                            }

                            int optionPrice = options.stream()
                                .mapToInt(OptionDetailDto::getPrice)
                                .sum();

                            Integer paidQuantity = orderItemToPaidQuantityMap.getOrDefault(orderItemId, 0);


                            // baseOrder와 options를 결합하여 DishInfoResponseDto 만들기
                            return DishInfoResponseDto.builder()
                                .orderItemId(orderItemId)
                                .dishId(baseOrder.getDishId())
                                .userId(baseOrder.getUserId())
                                .dishName(baseOrder.getDishName())
                                .dishCategoryId(baseOrder.getDishCategoryId())
                                .dishCategoryName(baseOrder.getDishCategoryName())
                                .price(baseOrder.getDishPrice())
                                .totalPrice(baseOrder.getDishPrice() + optionPrice)
                                .options(options)
                                .quantity(baseOrder.getQuantity())
                                .restQuantity(baseOrder.getQuantity() - paidQuantity)
                                .build();
                        })
                        .toList();

                    // 현재 order의 price 계산
                    int orderPrice = dishInfoResponseDtos.stream()
                        .mapToInt(dishInfoResponseDto -> dishInfoResponseDto.totalPrice() * dishInfoResponseDto.quantity())
                        .sum();

                    // restPrice 계산
                    int restPrice = dishInfoResponseDtos.stream()
                        .mapToInt(dishInfoResponseDto -> dishInfoResponseDto.totalPrice() * dishInfoResponseDto.restQuantity())
                        .sum();

                    int dishCnt = dishInfoResponseDtos.stream()
                        .mapToInt(DishInfoResponseDto::quantity)
                        .sum();

                    int restDishCnt = dishInfoResponseDtos.stream()
                        .mapToInt(DishInfoResponseDto::restQuantity)
                        .sum();

                    // orderId와 dishInfoResponseDtos를 결합
                    return OrderInfoResponseDto.builder()
                        .orderId(orderId)
                        .price(orderPrice)
                        .restPrice(restPrice)
                        .dishCnt(dishCnt)
                        .restDishCnt(restDishCnt)
                        .dishes(dishInfoResponseDtos)
                        .build();
                })
                .toList());

            // orders 정렬
            orderInfoResponseDtos.sort((Comparator.comparingInt(OrderInfoResponseDto::orderId)));

            // totalDishCount, totalPrice 구하기
            int totalDishCount = orderInfoResponseDtos.stream()
                .mapToInt(OrderInfoResponseDto::dishCnt)
                .sum();

            int restDishCnt = orderInfoResponseDtos.stream()
                .mapToInt(OrderInfoResponseDto::restDishCnt)
                .sum();

            int totalPrice = orderInfoResponseDtos.stream()
                .mapToInt(OrderInfoResponseDto::price)
                .sum();

            int restPrice = orderInfoResponseDtos.stream()
                .mapToInt(OrderInfoResponseDto::restPrice)
                .sum();

            // table과 orderInfoResponseDtos를 결합
            return TotalOrderInfoResponseDto.builder()
                .tableName(table.getName())
                .dishCnt(totalDishCount)
                .userCnt(userCnt)
                .restDishCnt(restDishCnt)
                .price(totalPrice)
                .restPrice(restPrice)
                .orders(orderInfoResponseDtos)
                .build();


        }
    }

    @Transactional
    @Override
    public SimpleResponseMessage initPayment(String tableId) {
        ParentOrder parentOrder = parentOrderRepository.findFirstByTableIdOrderByIdDesc(tableId)
            .orElseThrow(() -> new JDQRException(ErrorCode.PARENT_ORDER_NOT_FOUND));

        if (parentOrder.getOrderStatus() != OrderStatus.PENDING && parentOrder.getOrderStatus() != OrderStatus.PAY_WAITING) {
            throw new JDQRException(ErrorCode.VALIDATION_ERROR_INTERNAL);
        }

        parentOrder.setOrderStatus(OrderStatus.PAY_WAITING);
        parentOrderRepository.save(parentOrder);

        return SimpleResponseMessage.ORDER_STATUS_CHANGED;
    }

    @Transactional(readOnly = true)
    @Override
    public ParentOrderInfoResponseDto getParentOrderInfo(String tableId) {
        Optional<ParentOrder> optionalParentOrder = parentOrderRepository.findFirstByTableIdOrderByIdDesc(tableId);

        if (optionalParentOrder.isEmpty()) {
            return ParentOrderInfoResponseDto.builder()
                .orderStatus(OrderStatus.PENDING)
                .build();
        }

        ParentOrder parentOrder = optionalParentOrder.get();
        return ParentOrderInfoResponseDto.builder()
            .parentOrderId(parentOrder.getId())
            .orderStatus(parentOrder.getOrderStatus())
            .build();
    }

    /**
     * 테이블에 들어간 주문이 없을 때, 기본 OrderInfoResponseDto를 반환
     *
     * @param table : 주문 중인 테이블
     * @return : TotalOrderInfoResponseDto
     */
    private TotalOrderInfoResponseDto getBaseOrderInfo(Table table) {
        return TotalOrderInfoResponseDto.builder()
            .tableName(table.getName())
            .dishCnt(0)
            .price(0)
            .orders(new ArrayList<>())
            .build();
    }

//    @Transactional
//    @Override
//    public void addDummyOrderData(DummyOrderDto dummyOrderDto) {
//        String tableId = dummyOrderDto.getTableId();
//
//        // 1. Orders table에 저장
//        ParentOrder parentOrder = ParentOrder.builder()
//            .tableId(tableId)
//            .orderStatus(OrderStatus.PAID)
//            .paymentMethod(dummyOrderDto.getPaymentMethod())
//            .build();
//
//        ParentOrder savedParentOrder = orderRepository.save(parentOrder);
//        String userId = UUID.randomUUID().toString();
//
//        // 2. order_items table에 저장
//        List<DummyDishDto> dummyDishDtos = dummyOrderDto.getDummyDishDtos();
//        for (DummyDishDto dummyDishDto : dummyDishDtos) {
//            Dish dish = dishRepository.findById(dummyDishDto.getDishId())
//                .orElseThrow(() -> new JDQRException(ErrorCode.DISH_NOT_FOUND));
//
//            OrderItem orderItem = OrderItem.builder()
//                .parentOrder(savedParentOrder)
//                .dish(dish)
//                .userId(userId)
//                .quantity(dummyDishDto.getQuantity())
//                .paidQuantity(dummyDishDto.getQuantity())
//                .orderPrice(dummyDishDto.getPrice())
//                .orderStatus(OrderStatus.PAID)
//                .build();
//
//            List<Integer> choiceIds = dummyDishDto.getChoiceIds();
//            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
//
//            List<Choice> choices = choiceRepository.findAllById(choiceIds);
//            Map<Integer, Choice> choiceIdMap = new HashMap<>(choices.size());
//            for (Choice choice : choices) {
//                choiceIdMap.put(choice.getId(), choice);
//            }
//
//            // orderItemChoices 저장
//            List<OrderItemChoice> orderItemChoices = choiceIds.stream()
//                .map(choiceId -> {
//                    Choice choice = choiceIdMap.get(choiceId);
//                    return OrderItemChoice.builder()
//                        .orderItem(orderItem)
//                        .choice(choice)
//                        .build();
//                })
//                .toList();
//
//            orderItemChoiceRepository.saveAll(orderItemChoices);
//
//        }
//
//
//    }

    /**
     * {tossOrderId}에 해당하는 주문의 결제가 모두 끝났는지를 판단한다.
     *
     * @param tableId     : 현재 주문에 해당하는 tableId
     * @param tossOrderId : 테이블의 가장 최신 주문 id
     */
    private SimpleResponseMessage checkOrderIsFinished(String tableId, String tossOrderId) {
        // 주문한 금액의 총합과 결제한 금액의 총합이 서로 동일할 경우, 결제가 모두 끝났다고 판단
        Payment payment = paymentRepository.findByTossOrderId(tossOrderId);

        // payment의 상태 변경 & 저장
        payment.setPaymentStatus(PaymentStatus.PAID);
        paymentRepository.save(payment);

        ParentOrder parentOrder = payment.getParentOrder();

        // 결제가 다 끝났는지를 확인
        // 총 주문 금액과, 유저가 결제한 금액의 차이가 100원 미만이면 결제가 모두 끝났다고 판정
        boolean flag = Math.abs(getTotalPurchaseAmount(parentOrder) - getCurPaidAmount(parentOrder)) < 100;

        // 결제가 끝났을 경우 : order과 table의 상태 변경
        if (flag) {
            parentOrder.setOrderStatus(OrderStatus.PAID);
            parentOrderRepository.save(parentOrder);

            Table table = tableRepository.findById(tableId)
                .orElseThrow(() -> new JDQRException(ErrorCode.TABLE_NOT_FOUND));
            table.setUseStatus(UseStatus.AVAILABLE);
            tableRepository.save(table);

            return SimpleResponseMessage.WHOLE_PAYMENT_SUCCESS;
        } else {
            return SimpleResponseMessage.PAYMENT_SUCCESS;
        }
    }

    @Transactional
    @RedLock(key = "'payment'")
    protected SimpleResponseMessage updatePaymentStatusToSuccess(String tossOrderId, SimpleTossPaymentRequestDto tossPaymentSimpleResponseDto) {
        Payment payment = paymentRepository.findByTossOrderId(tossOrderId);

        System.out.println("payment = " + payment);

        // paymentKey가 이미 존재하는 경우 -> 이미 결제를 시도한 거래임
        // 에러 반환
        if (payment.getPaymentKey() != null) {
            throw new JDQRException(ErrorCode.ORDER_ALREADY_PAID);
        }
        payment.setPaymentKey(tossPaymentSimpleResponseDto.paymentKey());

        // validation 로직
        // 클라이언트에서 결제 amount를 임의로 조작하는 것을 막기 위해, 서버에 저장해 둔 amount와 body에 들어온 amount를 비교
        if (!payment.getAmount().equals(tossPaymentSimpleResponseDto.amount())) {
            payment.setPaymentStatus(PaymentStatus.CANCELLED);
            paymentRepository.save(payment);
            return SimpleResponseMessage.PAYMENT_EXCEED_PURCHASE_AMOUNT;
        }

        // 이미 처리된 결제인 경우
        if (!payment.getPaymentStatus().equals(PaymentStatus.PENDING))
            return SimpleResponseMessage.PAYMENT_ALREADY_PAID;

        // 1. 우리 DB의 상태 업데이트
        // 현재 결제의 결제 방식 확인
        ParentOrder parentOrder = payment.getParentOrder();
        PaymentMethod paymentMethod = parentOrder.getPaymentMethod();

        // 1-1. 결제 방식이 MONEY_DIVIDE 방식일 경우
        if (paymentMethod.equals(PaymentMethod.MONEY_DIVIDE)) {
            Integer totalPurchaseAmount = getTotalPurchaseAmount(parentOrder);
            Integer curPaidAmount = getCurPaidAmount(parentOrder);

            // 주문한 금액보다 더 많은 양의 금액을 결제하려고 시도하는 경우
            if (curPaidAmount + payment.getAmount() > totalPurchaseAmount) {
                // 현재 결제 상태를 취소 상태로 바꾼다
                payment.setPaymentStatus(PaymentStatus.CANCELLED);
                paymentRepository.save(payment);
                return SimpleResponseMessage.PAYMENT_EXCEED_PURCHASE_AMOUNT;
            }
        }
        // 1-2. 결제 방식이 MENU_DIVIDE 방식일 경우
        else {
            List<PaymentDetail> paymentDetails = paymentDetailRepository.findAllByPayment(payment);

            List<OrderItem> orderItems = new ArrayList<>();
            for (PaymentDetail paymentDetail : paymentDetails) {
                OrderItem orderItem = paymentDetail.getOrderItem();

                // 주문한 메뉴보다 더 많은 양의 메뉴를 결제하려고 시도하는 경우
                if (orderItem.getPaidQuantity() + paymentDetail.getQuantity() > orderItem.getQuantity()) {
                    payment.setPaymentStatus(PaymentStatus.CANCELLED);
                    paymentRepository.save(payment);
                    return SimpleResponseMessage.PAYMENT_EXCEED_MENU_AMOUNT;
                }

                orderItems.add(orderItem);
            }

            for (int i = 0; i < orderItems.size(); i++) {
                OrderItem orderItem = orderItems.get(i);
                PaymentDetail paymentDetail = paymentDetails.get(i);
                orderItem.setPaidQuantity(orderItem.getPaidQuantity() + paymentDetail.getQuantity());
                orderItems.add(orderItem);
            }

            // orderItems 업데이트
            orderItemRepository.saveAll(orderItems);
        }

        // 결제 상태 성공으로 변경
        payment.setPaymentStatus(PaymentStatus.PAID);
        // payment 업데이트
        paymentRepository.save(payment);

        return SimpleResponseMessage.PAYMENT_SUCCESS;
    }

    /**
     * 메뉴별 결제를 할 경우에 해당 결제 정보가 담긴 payment entity를 추가한다.
     * 동시성 처리를 위해 Lock 적용
     *
     * @param paymentRequestDto : 결제 정보가 담긴 record
     */
    @Transactional
    protected Payment createBasePaymentForMenuDivide(PaymentRequestDto paymentRequestDto) {
        List<OrderItemRequestDto> orderItemRequestDtos = paymentRequestDto.orderItemInfos();
        List<Integer> orderItemIds = orderItemRequestDtos.stream()
            .map(OrderItemRequestDto::orderItemId)
            .toList();

        List<OrderItem> orderItems = orderItemRepository.findAllById(orderItemIds);

        Map<Integer, OrderItem> idToOrderItemMap = orderItems.stream()
            .collect(Collectors.toMap(OrderItem::getId, orderItem -> orderItem));

        // orderItemRequestDto에 적혀 있는 숫자만큼 orderItem의 정보를 업데이트
        // 중간에 orderItem의 남아 있는 수량보다, 현재 결제하려고 시도하는 수량이 많을 경우 error throw
        Integer purchasePrice = orderItemRequestDtos.stream()
            .map(orderItemRequestDto -> {
                OrderItem orderItem = idToOrderItemMap.get(orderItemRequestDto.orderItemId());
                int remainQuantity = orderItem.getQuantity() - orderItem.getPaidQuantity();

                if (remainQuantity < orderItemRequestDto.quantity()) {
                    throw new JDQRException(ErrorCode.EXCEED_MENU_PURCHASE_AMOUNT);
                }

                return orderItem.getOrderPrice() * orderItemRequestDto.quantity();
            })
            .reduce(0, Integer::sum);

        String tossOrderId = generateOrderId();

        Payment payment = Payment.builder()
            .tossOrderId(tossOrderId)
            .amount(purchasePrice)
            .paymentStatus(PaymentStatus.PENDING)
            .build();

        // payment entity 저장 후 반환
        Payment savedPayment = paymentRepository.save(payment);

        List<PaymentDetail> paymentDetails = orderItemRequestDtos.stream()
            .map(orderItemRequestDto -> PaymentDetail.builder()
                .payment(payment)
                .orderItem(idToOrderItemMap.get(orderItemRequestDto.orderItemId()))
                .quantity(orderItemRequestDto.quantity())
                .build())
            .toList();

        paymentDetailRepository.saveAll(paymentDetails);

        return savedPayment;
    }

    /**
     * N빵 결제를 할 경우에 해당 결제 정보가 담긴 payment Entity를 추가한다.
     * 동시성 처리를 위해 Lock 적용
     *
     * @param totalPurchaseAmount : 총 주문 금액
     * @param paymentRequestDto   : 결제 정보가 담긴 record
     */
    @Transactional
    protected Payment createBasePaymentForMoneyDivide(ParentOrder parentOrder, Integer totalPurchaseAmount, PaymentRequestDto paymentRequestDto) {
        // 1. 현재 결제가 된 총 금액을 구한다.
        Integer curPaidAmount = getCurPaidAmount(parentOrder);

        // 결제할 금액 구하기
        int paymentAmount = totalPurchaseAmount * paymentRequestDto.serveNum() / paymentRequestDto.peopleNum();
        // validation check : 결제된 금액 + 결제할 금액 > 전체 주문 금액이면 오류 발생
        if (curPaidAmount + paymentAmount > totalPurchaseAmount) {
            throw new JDQRException(ErrorCode.EXCEED_TOTAL_PURCHASE_AMOUNT);
        }

        // 2. 금액을 바탕으로, payment entity를 추가한다.
        // 2-1. orderId 생성
        String tossOrderId = generateOrderId();

        // 2-2. payment entity 생성
        Payment payment = Payment.builder()
            .parentOrder(parentOrder)
            .amount(paymentAmount)
            .tossOrderId(tossOrderId)
            .paymentStatus(PaymentStatus.PENDING)
            .build();

        // 3. entity 저장
        return paymentRepository.save(payment);
    }

    /**
     * @return : toss 결제 api에서 사용하는 주문번호를 반환하는 메서드
     */
    private String generateOrderId() {
        return String.format("%s-%s",
            TimeUtil.convertEpochToDateString(TimeUtil.getCurrentTimeMillisUtc(), "yyMMdd"),
            RandomUtil.generateRandomString());
    }

    /**
     * 주문 기준으로, 현재 결제가 완료된 금액을 구해서 반환한다.
     *
     * @return : 총 결제 금액
     */
    private Integer getCurPaidAmount(ParentOrder parentOrder) {
        return paymentRepository.findPaymentsByOrders(parentOrder).stream()
            .filter(payment -> payment.getPaymentStatus().equals(PaymentStatus.PAID))
            .map(Payment::getAmount)
            .reduce(0, Integer::sum);
    }

    /**
     * @return : 해당 테이블에서 구메한 메뉴들의 가격의 총합
     */
    private Integer getTotalPurchaseAmount(ParentOrder parentOrder) {
        List<Order> orders = orderRepository.findAllByParentOrder(parentOrder);

        List<OrderItem> orderItems = orderItemRepository.findOrderItemByOrder(orders).stream()
            .toList();

        return orderItems.stream()
            .map(orderItem -> orderItem.getOrderPrice() * orderItem.getQuantity())
            .reduce(0, Integer::sum);
    }

    /**
     * 현재 테이블의 결제 방식을 주어진 결제 방식으로 변경한다.
     * 만일 이미 결제 방식이 저장되어 있고, 이것이 주어진 결제 방식과 다를 경우, 에러 발생
     *
     * @param tableId       : 주문하는 테이블의 id
     * @param paymentMethod : 결제하기를 원하는 결제방식
     * @param serveNum
     * @param peopleNum
     */
    @Transactional
    @RedLock(key = "'order_status'")
    protected ParentOrder updatePaymentMethodOfOrder(String tableId, PaymentMethod paymentMethod, Integer serveNum, Integer peopleNum) {
        // 1. validation 체크
        // 현재 테이블의 parentOrder가 이미 결제가 종료된 상태가 아니어야 함
        Optional<ParentOrder> optionalParentOrder = parentOrderRepository.findFirstByTableIdOrderByIdDesc(tableId);
        if (optionalParentOrder.isEmpty()) {
            throw new JDQRException(ErrorCode.PARENT_ORDER_NOT_FOUND);
        }

        ParentOrder parentOrder = optionalParentOrder.get();
        if (parentOrder.getOrderStatus().getGroup().equals(OrderStatusGroup.FINISHED)) {
            throw new JDQRException(ErrorCode.INVALID_ORDER);
        }

        // 2. 테이블을 확인 후 결제 방식(payment_method column)을 업데이트하기
        PaymentMethod oldPaymentMethod = parentOrder.getPaymentMethod();

        // 2-1. 결제 방식이 아직 정해지지 않았을 경우
        if (oldPaymentMethod.equals(PaymentMethod.UNDEFINED)) {
            parentOrder.setPaymentMethod(paymentMethod);
        }
        // 2-2. 결제 방식이 정해진 경우
        // oldPaymentMethod는 항상 paymentMethod와 동일해야 한다
        // 다를 경우 에러를 반환
        else {
            if (!oldPaymentMethod.equals(paymentMethod)) {
                throw new JDQRException(ErrorCode.PAYMENT_INVALID);
            }
        }

        // 3. 결제 방식이 N빵 방식일 경우, serve_num이 동일한지 체크한다
        if (paymentMethod.equals(PaymentMethod.MONEY_DIVIDE)) {
            Integer savedPeopleNum = parentOrder.getPeopleNum();

            if (savedPeopleNum != null && !savedPeopleNum.equals(peopleNum)) {
                throw new JDQRException(ErrorCode.PAYMENT_INVALID);
            }

            parentOrder.setPeopleNum(peopleNum);
        }

        // 3. 바뀐 결제 방식 저장
        return parentOrderRepository.save(parentOrder);
    }

    /**
     * 유저들이 담은 메뉴들의 정보를 바탕으로, order_item_options table에 데이터를 저장한다
     *
     * @param orderItems : order_items table에 저장된 주문 정보
     * @param cartDatas  : 유저들이 담은 메뉴들의 정보
     */
    private void saveOrderItemOptions(List<OrderItem> orderItems, List<CartDto> cartDatas) {

        // validation 체크
        // 두 리스트의 길이가 다르다면 에러 반환
        if (orderItems.size() != cartDatas.size()) {
            throw new JDQRException(ErrorCode.VALIDATION_ERROR_INTERNAL);
        }

        // orderItems와 cartDatas의 같은 위치에 있는 객체는 같은 주문을 나타냄
        List<OrderItemChoice> orderItemChoices = new ArrayList<>();

        for (int i = 0; i < orderItems.size(); i++) {
            OrderItem orderItem = orderItems.get(i);
            CartDto productInfo = cartDatas.get(i);

            List<Integer> optionIds = productInfo.getChoiceIds();

            orderItemChoices.addAll(getOrderItemOptions(orderItem, optionIds));
        }

        // DB에 저장
        orderItemChoiceRepository.saveAll(orderItemChoices);
    }

    // orderItem과 해당 orderItem에 포함된 optionId 리스트를 이용해서, orderItemOption 리스트를 만드는 메서드
    private List<OrderItemChoice> getOrderItemOptions(OrderItem orderItem, List<Integer> optionIds) {
        List<Choice> choices = choiceRepository.findAllById(optionIds);

        return choices.stream()
            .map(choice -> OrderItemChoice.builder()
                .orderItem(orderItem)
                .choice(choice)
                .build())
            .toList();
    }

    /**
     * 유저들이 담은 메뉴들의 정보를 바탕으로, order_items table에 데이터를 저장한다
     *
     * @param order     : orders table에 저장된 주문 정보
     * @param cartDatas : 유저들이 담은 메뉴들의 정보
     * @return : 저장된 entity list
     */
    private List<OrderItem> saveOrderItems(Order order, List<CartDto> cartDatas) {

        List<OrderItem> orderItems = cartDatas.stream()
            .map(cartData -> productInfoToOrderItem(order, cartData))
            .toList();

        return orderItemRepository.saveAll(orderItems);
    }

    /**
     * orderItem에 데이터를 추가하는 메서드
     *
     * @param order       : orderItem이 속해 있는 order
     * @param productInfo : orderItem의 정보를 담고 있는 CartDto
     * @return : 저장된 orderItem entity
     */
    private OrderItem productInfoToOrderItem(Order order, CartDto productInfo) {
        Integer dishId = productInfo.getDishId();
        Dish dish = dishRepository.findById(dishId)
            .orElseThrow(() -> new JDQRException(ErrorCode.DISH_NOT_FOUND));
        String userId = productInfo.getUserId();
        List<Integer> choiceIds = productInfo.getChoiceIds();
        List<Choice> choices = choiceRepository.findAllById(choiceIds);

        Integer orderPrice = getPriceOfDishAndOptions(dish, choices);

        return OrderItem.builder()
            .order(order)
            .dish(dish)
            .userId(userId)
            .quantity(productInfo.getQuantity())
            .paidQuantity(0)
            .orderPrice(orderPrice)
            .orderedAt(productInfo.getOrderedAt())
            .build();
    }

    /**
     * 메뉴와 옵션을 선택했을 때, 해당 메뉴의 전체 가격을 반환한다.
     *
     * @param dish    : 선택한 메뉴 entity
     * @param choices : 선택한 세부 옵션 entity
     * @return
     */
    private Integer getPriceOfDishAndOptions(Dish dish, List<Choice> choices) {
        return dish.getPrice() + choices.stream()
            .mapToInt(Choice::getPrice)
            .sum();
    }

    /**
     * 유저들이 담은 메뉴들의 정보를 바탕으로, orders table에 데이터를 저장한다
     *
     * @param cartDatas : 유저들이 담은 메뉴들의 정보
     * @param tableId
     * @return : 저장된 entity
     */
    private Order saveOrder(List<CartDto> cartDatas, String tableId, ParentOrder parentOrder) {
        Order order = Order.builder()
            .parentOrder(parentOrder)
            .build();

        return orderRepository.save(order);
    }

}
