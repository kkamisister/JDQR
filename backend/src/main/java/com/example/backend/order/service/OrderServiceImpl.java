package com.example.backend.order.service;

import static com.example.backend.order.dto.CartResponse.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.backend.common.annotation.RedLock;
import com.example.backend.common.enums.SimpleResponseMessage;
import com.example.backend.common.util.RandomUtil;
import com.example.backend.common.util.TimeUtil;
import com.example.backend.dish.entity.Dish;
import com.example.backend.dish.entity.Option;
import com.example.backend.dish.repository.DishRepository;
import com.example.backend.dish.repository.OptionRepository;
import com.example.backend.order.dto.CartRequest.*;
import com.example.backend.order.dto.OrderRequest.*;
import com.example.backend.order.entity.Order;
import com.example.backend.order.entity.OrderItem;
import com.example.backend.order.entity.OrderItemOption;
import com.example.backend.order.entity.Payment;
import com.example.backend.order.enums.OrderStatus;
import com.example.backend.order.enums.PaymentMethod;
import com.example.backend.order.enums.PaymentStatus;
import com.example.backend.order.repository.OrderItemOptionRepository;
import com.example.backend.order.repository.OrderItemRepository;
import com.example.backend.order.repository.OrderRepository;
import com.example.backend.order.repository.PaymentRepository;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.example.backend.common.exception.ErrorCode;
import com.example.backend.common.exception.JDQRException;
import com.example.backend.common.redis.repository.RedisHashRepository;
import com.example.backend.common.util.GenerateLink;
import com.example.backend.order.dto.CartDto;
import com.example.backend.notification.service.NotificationService;
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
	private final NotificationService notificationService;
	private final RedisHashRepository redisHashRepository;
	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;
	private final OrderItemOptionRepository orderItemOptionRepository;
	private final DishRepository dishRepository;
	private final OptionRepository optionRepository;
	private final PaymentRepository paymentRepository;
	/**
	 * tableName으로 qrCode를 찾아서, 해당 코드에 token을 더한 주소를 반환
	 * @param tableId
	 * @return
	 */
	@Override
	public String redirectUrl(String tableId,String uuid) {
		//1. table을 찾는다
		Table table = tableRepository.findById(tableId)
			.orElseThrow(() -> new JDQRException(ErrorCode.FUCKED_UP_QR));

		log.warn("table : {}",table);

		// 현재 url이 유효하지 않다면 예외를 반환한다
		String targetUrl = GenerateLink.AUTH_PREFIX + "/"+tableId+"/"+uuid;
		if(!targetUrl.equals(table.getQrCode())){
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
	 *
	 * <tableId,<userId,<hashCode,CartDto>>>
	 * @param tableId
	 * @param productInfo
	 */
	@Override
	@Transactional
	@RedLock(key = "'table:' + #tableId", waitTime = 5000L,leaseTime = 1000L)
	public void addItem(String tableId,CartDto productInfo) {

		// 1. productInfo에서 id를 꺼내서 그런 메뉴가 있는지부터 확인
		dishRepository.findById(productInfo.getDishId())
			.orElseThrow(() -> new JDQRException(ErrorCode.DISH_NOT_FOUND));

		// 2. 테이블 장바구니에 물품을 담는다
		//<tableId,<userId,<hashCode,항목>>>
		String key = "table::"+tableId;
		// log.warn("userID : {}",productInfo.getUserId());
		Map<Integer,CartDto> cachedCartData = redisHashRepository.getCartDatas(key,productInfo.getUserId());
		// log.warn("cachedCartData : {}",cachedCartData);
		if(!ObjectUtils.isEmpty(cachedCartData)){ // 1. 기존에 동일한 물품이 있어서 거기에 더해지는 경우 -> 지금 담는 hashCode와 비교하여 동일한 것을 찾아서 추가

			CartDto cartData = cachedCartData.get(productInfo.hashCode());
			int currentQuantity = cartData.getQuantity();
			int newQuantity = currentQuantity + productInfo.getQuantity();
			if(newQuantity < 0)newQuantity = 0;
			// log.warn("newQuantity : {}",newQuantity);

			cartData.setQuantity(newQuantity);
			// log.warn("productInfo.hashCode() : {}",productInfo.hashCode());
			// log.warn("cartData : {}",cartData);
			cachedCartData.put(productInfo.hashCode(), cartData);

			redisHashRepository.saveHashData(key, cartData.getUserId(), cachedCartData,20L);
		}
		else{ // 2. 새로운 물품인 경우 -> 리스트에 추가
			cachedCartData = new ConcurrentHashMap<>();
			cachedCartData.put(productInfo.hashCode(), productInfo);
			redisHashRepository.saveHashData(key, productInfo.getUserId(), cachedCartData,20L);
		}
		// 3. 전송할 데이터를 생성한다

		// 3-1. 현재 테이블의 장바구니를 가지고온다.
		// 맵은 (userId,해당 유저가 담은 항목들) 의 형식
		Map<String, Map<Integer,CartDto>> allCartDatas = redisHashRepository.getAllCartDatas(tableId);

		// 3-2. 현재 테이블과 연결된 사람수를 받아온다
		int subscriberSize = notificationService.getSubscriberSize(tableId);

		// 3-3. 현재 테이블의 이름을 가져오기위해 테이블을 조회한다
		Table table = tableRepository.findById(tableId).orElseThrow(() -> new JDQRException(ErrorCode.TABLE_NOT_FOUND));


		// 3-4. 최종적으로 전송할 데이터
		List<CartDto> cartList = allCartDatas.values().stream()
			.flatMap(map -> map.values().stream())
			.collect(Collectors.toList());

		CartInfo sendData = CartInfo.of(cartList,table.getName(),subscriberSize);

		// notificationService.sentToClient(tableId,sendData);
		messagingTemplate.convertAndSend("/sub/cart/"+tableId,sendData);
	}
	private final SimpMessagingTemplate messagingTemplate;

	/**
	 * tableName으로 qrCode를 찾아서, 해당 코드에 token을 더한 주소를 반환
	 * @param tableId
	 * @return
	 */
	@Override
	public String redirectUrl(String tableId,String uuid) {
		//1. table을 찾는다
		Table table = tableRepository.findById(tableId)
			.orElseThrow(() -> new JDQRException(ErrorCode.FUCKED_UP_QR));

		log.warn("table : {}",table);

		// 현재 url이 유효하지 않다면 예외를 반환한다
		String targetUrl = GenerateLink.AUTH_PREFIX + "/"+tableId+"/"+uuid;
		if(!targetUrl.equals(table.getQrCode())){
			throw new JDQRException(ErrorCode.FUCKED_UP_QR);
		}

		//2. table의 링크에 token을 생성한다
		String authLink = generateLink.createAuthLink(table.getId());

		return authLink;
	}

	/**
	 * 테이블의 장바구니에서 productInfo를 가진 품목을 제거한다
	 * @param tableId
	 * @param productInfo
	 */
	@Override
	// @RedLock(key = "'table:' + #tableId",waitTime = 5000L, leaseTime = 1000L)
	public void deleteItem(String tableId, CartDto productInfo) {

		// 1. productInfo에서 id를 꺼내서 그런 메뉴가 있는지부터 확인
		dishRepository.findById(productInfo.getDishId())
			.orElseThrow(() -> new JDQRException(ErrorCode.DISH_NOT_FOUND));

		// 2. 해당 유저의 장바구니에서 제거한다
		//<tableId,<userId,<hashCode,항목>>>
		String key = "table::"+tableId;
		// log.warn("userID : {}",productInfo.getUserId());

		// 유저가 담은 <hashCode,품목> 의 맵을 가지고온다
		Map<Integer,CartDto> cachedCartData = redisHashRepository.getCartDatas(key,productInfo.getUserId());
		// log.warn("cachedCartData : {}",cachedCartData);

		// log.warn("productInfo.hashCode() : {}",productInfo.hashCode());
		CartDto findData = cachedCartData.getOrDefault(productInfo.hashCode(), null);
		// log.warn("findData : {}",findData);

		if(!ObjectUtils.isEmpty(findData)){
			// 해당 유저의 장바구니에 품목이 있다면 삭제한다
			cachedCartData.remove(productInfo.hashCode());
			// log.warn("cachedCardData After : {}",cachedCartData);
			redisHashRepository.saveHashData(key,productInfo.getUserId(),cachedCartData,20L);
		}
		else{
			throw new JDQRException(ErrorCode.FUCKED_UP_QR);
		}
		// 3. 전송할 데이터를 생성한다

		// 3-1. 현재 테이블의 장바구니를 가지고온다.
		// 맵은 (userId,해당 유저가 담은 항목들) 의 형식
		Map<String, Map<Integer,CartDto>> allCartDatas = redisHashRepository.getAllCartDatas(tableId);
		// log.warn("allCartDatas : {}",allCartDatas);

		// 3-2. 현재 테이블과 연결된 사람수를 받아온다
		int subscriberSize = notificationService.getSubscriberSize(tableId);

		// 3-3. 현재 테이블의 이름을 가져오기위해 테이블을 조회한다
		Table table = tableRepository.findById(tableId).orElseThrow(() -> new JDQRException(ErrorCode.TABLE_NOT_FOUND));

		// 3-4. 최종적으로 전송할 데이터
		List<CartDto> cartList = allCartDatas.values().stream()
			.flatMap(map -> map.values().stream())
			.collect(Collectors.toList());

		CartInfo sendData = CartInfo.of(cartList,table.getName(),subscriberSize);

		// notificationService.sentToClient(tableId,sendData);
		messagingTemplate.convertAndSend("/sub/cart/"+tableId,sendData);
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

		Map<String, Map<Integer,CartDto>> allCartDatas = redisHashRepository.getAllCartDatas(tableId);

		List<CartDto> cartDatas = new ArrayList<>();
		for(Map<Integer,CartDto> cartMap : allCartDatas.values()){
			cartDatas.addAll(cartMap.values());
		}

		// redis에 아직 데이터가 없을 경우
		if (cartDatas == null || cartDatas.isEmpty()) {
			return SimpleResponseMessage.ORDER_ITEM_EMPTY;
		}

		// 1. orders table에 데이터를 추가한다
		Order order = saveOrder(cartDatas, tableId);

		// 2. order_items에 데이터를 추가한다
		List<OrderItem> orderItems = saveOrderItems(order, cartDatas);

		// 3. order_item_options에 데이터를 추가한다
		saveOrderItemOptions(orderItems, cartDatas);

		// 4. redis에서 정보를 제거한다
		redisHashRepository.removeKey(tableId);

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

		// 2. 해당 테이블의 가장 최근 order를 확인하고, 결제 방식을 업데이트시킨다
		updatePaymentMethodOfOrder(tableId, paymentMethod);

		// 3. paymentMethod 에 따라 다르게 재고 관리를 시행
		Payment payment;
		Order order = orderRepository.findMostRecentOrder(tableId);
		// 3-1. paymentMethod 가 MONEY_DIVIDE 일 경우
		if (paymentMethod.equals(PaymentMethod.MONEY_DIVIDE)) {
			Integer totalPurchaseAmount = getTotalPurchaseAmount(order);
			payment = createBasePaymentForMoneyDivide(order, totalPurchaseAmount, paymentRequestDto);
		}
		// 3-2. paymentMethod 가 MENU_DIVIDE 일 경우
		else {
			payment = createBasePaymentForMenuDivide(order, paymentRequestDto);
		}


		return InitialPaymentResponseDto.builder()
			.tossOrderId(payment.getTossOrderId())
			.amount(payment.getAmount())
			.build();
	}

	/**
	 * 메뉴별 결제를 할 경우에 해당 결제 정보가 담긴 payment entity를 추가한다.
	 * 동시성 처리를 위해 Lock 적용
	 *
	 * @param order               : 결제를 하기를 원하는 order entity
	 * @param paymentRequestDto : 결제 정보가 담긴 record
	 */
	@Transactional
	protected Payment createBasePaymentForMenuDivide(Order order, PaymentRequestDto paymentRequestDto) {
		List<OrderItemRequestDto> orderItemRequestDtos = paymentRequestDto.orderItemInfos();
		List<Integer> orderItemIds = orderItemRequestDtos.stream()
			.map(OrderItemRequestDto::orderItemId)
			.toList();

		List<OrderItem> orderItems = orderItemRepository.findAllById(orderItemIds);

		Map<Integer, OrderItem> orderItemMap = orderItems.stream()
			.collect(Collectors.toMap(OrderItem::getId, orderItem -> orderItem));

		// orderItemRequestDto에 적혀 있는 숫자만큼 orderItem의 정보를 업데이트
		// 중간에 orderItem의 남아 있는 수량보다, 현재 결제하려고 시도하는 수량이 많을 경우 error throw
		Integer purchasePrice = orderItemRequestDtos.stream()
			.map(orderItemRequestDto -> {
				OrderItem orderItem = orderItemMap.get(orderItemRequestDto.orderItemId());
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
			.order(order)
			.paymentStatus(PaymentStatus.PENDING)
			.build();

		// payment entity 저장 후 반환
		return paymentRepository.save(payment);
	}

	/**
	 * N빵 결제를 할 경우에 해당 결제 정보가 담긴 payment Entity를 추가한다.
	 * 동시성 처리를 위해 Lock 적용
	 *
	 * @param order               : 결제를 하기를 원하는 order entity
	 * @param totalPurchaseAmount : 총 주문 금액
	 * @param paymentRequestDto : 결제 정보가 담긴 record
	 */
	@Transactional
	@RedLock(key = "'payment'")
    protected Payment createBasePaymentForMoneyDivide(Order order, Integer totalPurchaseAmount, PaymentRequestDto paymentRequestDto) {
		// 1. 현재 결제가 된 총 금액을 구한다.
		Integer curPaidAmount = getCurPaidAmount(order);

		// 결제할 금액 구하기
		int paymentAmount = totalPurchaseAmount * paymentRequestDto.serveNum() / paymentRequestDto.peopleNum();

		if (curPaidAmount + paymentAmount > totalPurchaseAmount) {
			throw new JDQRException(ErrorCode.EXCEED_TOTAL_PURCHASE_AMOUNT);
		}

		// 2. 금액을 바탕으로, payment entity를 추가한다.
		// 2-1. orderId 생성
		String tossOrderId = generateOrderId();

		// 2-2. payment entity 생성
		Payment payment = Payment.builder()
			.amount(paymentAmount)
			.tossOrderId(tossOrderId)
			.paymentStatus(PaymentStatus.PENDING)
			.order(order)
			.build();

		// 3. entity 저장
		return paymentRepository.save(payment);
	}

	/**
	 * @return : toss 결제 api에서 사용하는 주문번호를 반환하는 메서드
	 */
	private String generateOrderId() {
		return String.format("%s%s",
			TimeUtil.convertEpochToDateString(TimeUtil.getCurrentTimeMillisUtc(), "yyMMdd"),
			RandomUtil.generateRandomString());
	}

	/**
	 * 주문 기준으로, 현재 결제가 완료된 금액을 구해서 반환한다.
	 * @param order : 조회하기를 원하는 order entity
	 * @return : 총 결제 금액
	 */
	private Integer getCurPaidAmount(Order order) {
		return  paymentRepository.findAllByOrder(order).stream()
			.filter(payment -> payment.getPaymentStatus().equals(PaymentStatus.PENDING))
			.map(Payment::getAmount)
			.reduce(0, Integer::sum);
	}

	/**
	 * @param order : 현재 집계하기를 원하는 order id
	 * @return : 해당 테이블에서 구메한 메뉴들의 가격의 총합
	 */
	private Integer getTotalPurchaseAmount(Order order) {
		if (!order.getOrderStatus().equals(OrderStatus.PENDING)) throw new JDQRException(ErrorCode.ORDER_ALREADY_PAID);


		List<OrderItem> orderItems = orderItemRepository.findAllByOrder(order).stream()
			.filter(orderItem -> orderItem.getOrderStatus().equals(OrderStatus.PENDING))
			.toList();

		return orderItems.stream()
			.map(orderItem -> orderItem.getOrderPrice() * orderItem.getQuantity())
			.reduce(0, Integer::sum);
	}

	/**
	 * 현재 테이블의 결제 방식을 주어진 결제 방식으로 변경한다.
	 * 만일 이미 결제 방식이 저장되어 있고, 이것이 주어진 결제 방식과 다를 경우, 에러 발생
	 *
	 * @param tableId : 주문하는 테이블의 id
	 * @param paymentMethod : 결제하기를 원하는 결제방식
	 */
	@Transactional
	@RedLock(key = "'order_status'")
    protected void updatePaymentMethodOfOrder(String tableId, PaymentMethod paymentMethod) {
		// 1. 테이블의 가장 최근 order 가져오기
		Order order = orderRepository.findMostRecentOrder(tableId);
		if (!order.getOrderStatus().equals(OrderStatus.PENDING)) throw new JDQRException(ErrorCode.ORDER_ALREADY_PAID);

		// 2. 테이블을 확인 후 결제 방식(payment_method column)을 업데이트하기
		PaymentMethod oldPaymentMethod = order.getPaymentMethod();

		// 2-1. 결제 방식이 아직 정해지지 않았을 경우
		if (oldPaymentMethod.equals(PaymentMethod.UNDEFINED)) {
			order.setPaymentMethod(paymentMethod);
			orderRepository.save(order);
		}
		// 2-2. 결제 방식이 정해진 경우
		// oldPaymentMethod는 항상 paymentMethod와 동일해야 한다
		// 다를 경우 에러를 반환
		else {
			if (!oldPaymentMethod.equals(paymentMethod)) {
				throw new JDQRException(ErrorCode.PAYMENT_METHOD_NOT_VALID);
			}
		}
	}

	/**
	 * 유저들이 담은 메뉴들의 정보를 바탕으로, order_item_options table에 데이터를 저장한다
	 *
	 * @param orderItems : order_items table에 저장된 주문 정보
	 * @param cartDatas : 유저들이 담은 메뉴들의 정보
	 */
	private void saveOrderItemOptions(List<OrderItem> orderItems, List<CartDto> cartDatas) {

		// validation 체크
		// 두 리스트의 길이가 다르다면 에러 반환
		if (orderItems.size() != cartDatas.size()) {
			throw new JDQRException(ErrorCode.VALIDATION_ERROR_INTERNAL);
		}

		// orderItems와 cartDatas의 같은 위치에 있는 객체는 같은 주문을 나타냄
		List<OrderItemOption> orderItemOptions = new ArrayList<>();

		for (int i = 0; i < orderItems.size(); i++) {
			OrderItem orderItem = orderItems.get(i);
			CartDto productInfo = cartDatas.get(i);

			List<Integer> optionIds = productInfo.getOptionIds();

			orderItemOptions.addAll(getOrderItemOptions(orderItem, optionIds));
		}

		// DB에 저장
		orderItemOptionRepository.saveAll(orderItemOptions);
	}

	// orderItem과 해당 orderItem에 포함된 optionId 리스트를 이용해서, orderItemOption 리스트를 만드는 메서드
	private List<OrderItemOption> getOrderItemOptions(OrderItem orderItem, List<Integer> optionIds) {
		List<Option> options = optionRepository.findAllById(optionIds);

		return options.stream()
			.map(option -> OrderItemOption.builder()
				.orderItem(orderItem)
				.option(option)
				.build())
			.toList();
	}

	/**
	 * 유저들이 담은 메뉴들의 정보를 바탕으로, order_items table에 데이터를 저장한다
	 *
	 * @param order : orders table에 저장된 주문 정보
	 * @param cartDatas : 유저들이 담은 메뉴들의 정보
	 * @return : 저장된 entity list
	 */
	private List<OrderItem> saveOrderItems(Order order, List<CartDto> cartDatas) {

		List<OrderItem> orderItems = cartDatas.stream()
			.map(cartData -> productInfoToOrderItem(order, cartData))
			.toList();

		return orderItemRepository.saveAll(orderItems);
	}

	private OrderItem productInfoToOrderItem(Order order, CartDto productInfo) {
		Integer dishId = productInfo.getDishId();
		Dish dish = dishRepository.findById(dishId)
			.orElseThrow(() -> new JDQRException(ErrorCode.DISH_NOT_FOUND));
		String userId = productInfo.getUserId();

		return OrderItem.builder()
			.order(order)
			.dish(dish)
			.userId(userId)
			.orderPrice(productInfo.getPrice())
			.quantity(productInfo.getQuantity())
			.orderedAt(productInfo.getOrderedAt())
			.build();
	}

	/**
	 * 유저들이 담은 메뉴들의 정보를 바탕으로, orders table에 데이터를 저장한다
	 *
	 * @param cartDatas : 유저들이 담은 메뉴들의 정보
	 * @param tableId
	 * @return : 저장된 entity
	 */
	private Order saveOrder(List<CartDto> cartDatas, String tableId) {
		Order order = Order.builder()
			.tableId(tableId)
			.orderStatus(OrderStatus.PENDING)
			.menuCnt(cartDatas.size())
			.build();

		return orderRepository.save(order);
	}

}
