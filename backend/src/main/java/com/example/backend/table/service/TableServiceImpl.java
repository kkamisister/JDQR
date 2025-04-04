package com.example.backend.table.service;

import static com.example.backend.table.dto.TableRequest.*;
import static com.example.backend.table.dto.TableResponse.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.example.backend.common.enums.EntityStatus;
import com.example.backend.dish.dto.ChoiceDto;
import com.example.backend.dish.dto.DishResponse.DishDetailInfo;
import com.example.backend.dish.dto.OptionDto;
import com.example.backend.dish.entity.Dish;
import com.example.backend.dish.entity.DishOption;
import com.example.backend.dish.entity.Option;
import com.example.backend.dish.repository.DishOptionRepository;
import com.example.backend.dish.repository.DishRepository;
import com.example.backend.etc.entity.Restaurant;
import com.example.backend.order.entity.OrderItem;
import com.example.backend.order.entity.OrderItemChoice;
import com.example.backend.order.entity.ParentOrder;
import com.example.backend.order.enums.OrderStatus;
import com.example.backend.order.repository.OrderItemRepository;
import com.example.backend.order.repository.OrderRepository;
import com.example.backend.order.repository.ParentOrderRepository;
import com.example.backend.owner.entity.Owner;
import com.example.backend.owner.repository.OwnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.common.enums.UseStatus;
import com.example.backend.common.exception.ErrorCode;
import com.example.backend.common.exception.JDQRException;
import com.example.backend.common.util.GenerateLink;
import com.example.backend.etc.repository.RestaurantRepository;
import com.example.backend.table.dto.TableOrderResponseVo;
import com.example.backend.table.entity.Table;
import com.example.backend.table.repository.TableRepository;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TableServiceImpl implements TableService{

	private final OwnerRepository ownerRepository;
	private final RestaurantRepository restaurantRepository;
	private final TableRepository tableRepository;
	private final GenerateLink generateLink;
	private final OrderItemRepository orderItemRepository;
	private final DishRepository dishRepository;
	private final ParentOrderRepository parentOrderRepository;

	/**
	 * 요청받은 테이블의 정보를, userId를 가진 점주를 찾아 등록한다
	 * @param tableInfo
	 * @param userId
	 * @return
	 */
	@Override
	public void createTable(TableInfo tableInfo, Integer userId) {

		//1. 점주를 찾는다
		Owner owner = ownerRepository.findById(userId)
			.orElseThrow(() -> new JDQRException(ErrorCode.USER_NOT_FOUND));

		//2. 점주가 가진 식당을 찾는다
		Restaurant restaurant = restaurantRepository.findByOwner(owner)
			.orElseThrow(() -> new JDQRException(ErrorCode.RESTAURANT_NOT_FOUND));

		//3. 테이블 정보 + 식당ID를 합쳐서 mongoDB에 저장한다
		Table table = Table.of(tableInfo,restaurant.getId(),UseStatus.AVAILABLE);
		Table savedTable = tableRepository.save(table);

		//4. QRCode를 생성하기위한 링크를 반환한다.
		String link = generateLink.create(savedTable.getId());

		//5. 테이블의 qrlink를 업데이트한다
		savedTable.updateQrCode(link);
		tableRepository.save(savedTable);
	}

	/**
	 * 테이블을 삭제하는 메서드
	 * @param tableId
	 * @param userId
	 */
	@Override
	public void deleteTable(String tableId, Integer userId) {

		//1. 점주를 찾는다
		Owner owner = ownerRepository.findById(userId)
			.orElseThrow(() -> new JDQRException(ErrorCode.USER_NOT_FOUND));

		//2. 테이블을 찾는다
		Table table = tableRepository.findById(tableId)
			.orElseThrow(() -> new JDQRException(ErrorCode.TABLE_NOT_FOUND));

		//3. 삭제한다
		table.setStatus(EntityStatus.DELETE);
		tableRepository.save(table);
	}

	/**
	 * 테이블 정보를 수정하는 메서드
	 * @param tableInfo
	 * @param userId
	 */
	@Override
	public void updateTable(TableInfo tableInfo, Integer userId) {

		// log.warn("업데이트 시작");
		// log.warn("tableInfo : {}",tableInfo);
		//1. 점주를 찾는다
		ownerRepository.findById(userId)
			.orElseThrow(() -> new JDQRException(ErrorCode.USER_NOT_FOUND));

		//2. 테이블을 찾는다
		Table table = tableRepository.findById(tableInfo.tableId())
			.orElseThrow(() -> new JDQRException(ErrorCode.TABLE_NOT_FOUND));

		// log.warn("tableId : {}",table.getId());
		//3. 업데이트한다
		table.updateTable(tableInfo);

		tableRepository.save(table);
	}

	/**
	 * QR코드를 재생성하는 메서드
	 * @param tableId
	 * @param userId
	 * @return
	 */
	@Override
	public QRInfo createQR(String tableId, Integer userId) {

		//1. 점주를 찾는다
		ownerRepository.findById(userId)
			.orElseThrow(() -> new JDQRException(ErrorCode.USER_NOT_FOUND));

		//2. 테이블을 찾는다
		Table table =  tableRepository.findById(tableId)
			.orElseThrow(() -> new JDQRException(ErrorCode.TABLE_NOT_FOUND));

		//3. 테이블의 QR을 재생성한다
		String link = generateLink.create(table.getId());

		//4. 테이블의 qrlink를 업데이트한다
		table.updateQrCode(link);
		tableRepository.save(table);

		return new QRInfo(link, LocalDateTime.now());
	}

	/**
	 * 모든 테이블을 조회한다
	 * @param userId
	 */
	@Override
	public TableResultDto getAllTables(Integer userId) {
		//1. 점주를 조회한다
		Owner owner = ownerRepository.findById(userId)
			.orElseThrow(() -> new JDQRException(ErrorCode.USER_NOT_FOUND));

		//2. 식당을 찾는다
		Restaurant restaurant = restaurantRepository.findByOwner(owner)
			.orElseThrow(() -> new JDQRException(ErrorCode.RESTAURANT_NOT_FOUND));

		//3. 식당의 모든 테이블을 조회한다
		List<Table> tables = tableRepository.findByRestaurantId(restaurant.getId());

		List<TableDetailInfo> tableDetailInfos = new ArrayList<>();
		int leftSeatNum = 0;
		for(Table table : tables){
			Optional<ParentOrder> optionalParentOrder = parentOrderRepository.findFirstByTableIdOrderByIdDesc(table.getId());

			if(table.getUseStatus().equals(UseStatus.AVAILABLE)){ // 남은 좌석의 수를 카운팅
				leftSeatNum += table.getPeople();
			}

			// 테이블에 한 번도 주문이 들어가지 않은 상태일 경우
			if (optionalParentOrder.isEmpty()) {
				tableDetailInfos.add(getBaseTableInfo(table));
				continue;
			}

			ParentOrder parentOrder = optionalParentOrder.get();
			Map<DishDetailInfo, Integer> dishCountMap = new LinkedHashMap<>();
			// 아직 결제되지 않은 항목만 가지고온다
			if(parentOrder.getOrderStatus().equals(OrderStatus.PENDING) || parentOrder.getOrderStatus().equals(OrderStatus.PAY_WAITING)){

				List<TableOrderResponseVo> voList = orderItemRepository.findAllDishOptionsAndChoicesByParentOrder(parentOrder);

				// OrderItem ID를 기준으로 그룹핑하여 각 orderItem별로 처리
				Map<Integer, List<TableOrderResponseVo>> orderItemGroups = voList.stream()
					.collect(Collectors.groupingBy(TableOrderResponseVo::getOrderItemId));

				// orderItemVoList는 하나의 OrderItem에 대한 모든 옵션과 선택지를 포함하는 리스트
				for (List<TableOrderResponseVo> orderItemVoList : orderItemGroups.values()) {
					TableOrderResponseVo firstVo = orderItemVoList.get(0);

					// Option ID를 기준으로 그룹핑합니다.
					Map<Integer, List<TableOrderResponseVo>> optionsMap = orderItemVoList.stream()
						.collect(Collectors.groupingBy(vo -> vo.getOptionId() == null ? -1 : vo.getOptionId()));

					// OptionDto 리스트를 생성합니다.
					List<OptionDto> optionDtos = getOptionDtos(optionsMap);

					// Dish 정보를 생성합니다.
					Dish dish = Dish.builder()
						.id(firstVo.getDishId())
						.name(firstVo.getDishName())
						.description(firstVo.getDescription())
						.image(firstVo.getImage())
						.price(firstVo.getPrice())
						.build();

					// DishDetailInfo를 생성합니다.
					DishDetailInfo dishDetailInfo = DishDetailInfo.of(dish, optionDtos);

					// 동일한 메뉴와 옵션 조합의 수량을 누적합니다.
					dishCountMap.put(dishDetailInfo, dishCountMap.getOrDefault(dishDetailInfo, 0) + 1);
				}
			}

			// 수량이 누적된 dishCountMap에서 최종 DishDetailInfo 생성
			List<DishDetailInfo> result = dishCountMap.entrySet().stream()
				.map(entry -> entry.getKey().withQuantity(entry.getValue()))
				.toList();

			// 각 메뉴의 세부옵션을 다 더한 가격을 산출하여 총 가격을 구하는데 사용한다
			int totalPrice = 0;
			for(DishDetailInfo dishDetailInfo : result) {
				List<OptionDto> options = dishDetailInfo.options();
				int choicePrice = 0;
				for(OptionDto optionDto : options) {
					choicePrice += optionDto.getChoices().stream()
						.mapToInt(ChoiceDto::getPrice)
						.sum();
				}
				// 현재 음식의 진짜 가격(본 가격 + 옵션반영가격)에 수량을 곱한다
				totalPrice += (dishDetailInfo.price() + choicePrice) * dishDetailInfo.quantity();
			}
			TableDetailInfo tableDetailInfo = TableDetailInfo.of(table,result,totalPrice);
			tableDetailInfos.add(tableDetailInfo);
		}

		TableResultDto tableResultDto = new TableResultDto(tableDetailInfos,leftSeatNum);

		return tableResultDto;
	}

	/**
	 * (한옵션 - 세부옵션들)로 만든 OptionDto의 리스트를 반환하는 메서드.
	 * @param optionsMap
	 * @return
	 */
	private static List<OptionDto> getOptionDtos(Map<Integer, List<TableOrderResponseVo>> optionsMap) {
		List<OptionDto> optionDtos = optionsMap.entrySet().stream()
			.filter(entry -> entry.getKey() != -1)
			.map(entry -> {
				Integer optionId = entry.getKey();
				List<TableOrderResponseVo> optionVoList = entry.getValue();

				String optionName = optionVoList.get(0).getOptionName();

				List<ChoiceDto> choiceDtos = optionVoList.stream()
					.map(vo -> new ChoiceDto(vo.getChoiceId(), vo.getChoiceName(),vo.getChoicePrice()))
					.collect(Collectors.toList());

				return OptionDto.builder()
					.optionId(optionId)
					.optionName(optionName)
					.choices(choiceDtos)
					.build();
			})
			.toList();
		return optionDtos;
	}

	/**
	 * 테이블에 아직 한 번도 주문한 적이 없는 경우, 해당 테이블의 정보만을 반환
	 */
	private TableDetailInfo getBaseTableInfo(Table table) {
		return TableDetailInfo.builder()
			.tableId(table.getId())
			.name(table.getName())
			.color(table.getColor())
			.qrLink(table.getQrCode())
			.qrlastUpdatedAt(table.getQrUpdatedAt())
			.people(table.getPeople())
			.dishes(new ArrayList<>())
			.build();
	}

	/**
	 * 테이블의 상세정보를 조회하는 메서드
	 * @param tableId
	 * @param userId
	 */
	@Override
	public TableDetailInfo getTable(String tableId, Integer userId) {

		//1. 점주를 조회한다
		ownerRepository.findById(userId)
			.orElseThrow(() -> new JDQRException(ErrorCode.USER_NOT_FOUND));

		//2. 테이블을 조회한다
		Table table = tableRepository.findById(tableId)
			.orElseThrow(() -> new JDQRException(ErrorCode.TABLE_NOT_FOUND));

		//3. 테이블의 상세정보를 조회한다
		Optional<ParentOrder> optionalParentOrder = parentOrderRepository.findFirstByTableIdOrderByIdDesc(table.getId());
		if (optionalParentOrder.isEmpty()) {
			return getBaseTableInfo(table);
		}

		ParentOrder parentOrder = optionalParentOrder.get();

		Map<DishDetailInfo, Integer> dishCountMap = new LinkedHashMap<>();

		// 아직 결제되지 않은 항목만 가지고온다
		if(parentOrder.getOrderStatus().equals(OrderStatus.PENDING) || parentOrder.getOrderStatus().equals(OrderStatus.PAY_WAITING)){

			List<TableOrderResponseVo> voList = orderItemRepository.findAllDishOptionsAndChoicesByParentOrder(parentOrder);

			// OrderItem ID를 기준으로 그룹핑하여 각 orderItem별로 처리
			Map<Integer, List<TableOrderResponseVo>> orderItemGroups = voList.stream()
				.collect(Collectors.groupingBy(TableOrderResponseVo::getOrderItemId));

			// orderItemVoList는 하나의 OrderItem에 대한 모든 옵션과 선택지를 포함하는 리스트
			for (List<TableOrderResponseVo> orderItemVoList : orderItemGroups.values()) {
				TableOrderResponseVo firstVo = orderItemVoList.get(0);

				// Option ID를 기준으로 그룹핑합니다.
				Map<Integer, List<TableOrderResponseVo>> optionsMap = orderItemVoList.stream()
					.collect(Collectors.groupingBy(vo -> vo.getOptionId() == null ? -1 : vo.getOptionId()));

				// OptionDto 리스트를 생성합니다.
				List<OptionDto> optionDtos = getOptionDtos(optionsMap);

				// Dish 정보를 생성합니다.
				Dish dish = Dish.builder()
					.id(firstVo.getDishId())
					.name(firstVo.getDishName())
					.description(firstVo.getDescription())
					.image(firstVo.getImage())
					.price(firstVo.getPrice())
					.build();

				// DishDetailInfo를 생성합니다.
				DishDetailInfo dishDetailInfo = DishDetailInfo.of(dish, optionDtos);

				// 동일한 메뉴와 옵션 조합의 수량을 누적합니다.
				dishCountMap.put(dishDetailInfo, dishCountMap.getOrDefault(dishDetailInfo, 0) + 1);
			}
		}

		// 수량이 누적된 dishCountMap에서 최종 DishDetailInfo 생성
		List<DishDetailInfo> result = dishCountMap.entrySet().stream()
			.map(entry -> entry.getKey().withQuantity(entry.getValue()))
			.toList();

		// 각 메뉴의 세부옵션을 다 더한 가격을 산출하여 총 가격을 구하는데 사용한다
		int totalPrice = 0;
		for(DishDetailInfo dishDetailInfo : result) {
			List<OptionDto> options = dishDetailInfo.options();
			int choicePrice = 0;
			for(OptionDto optionDto : options) {
				choicePrice += optionDto.getChoices().stream()
					.mapToInt(ChoiceDto::getPrice)
					.sum();
			}
			// 현재 음식의 진짜 가격(본 가격 + 옵션반영가격)에 수량을 곱한다
			totalPrice += (dishDetailInfo.price() + choicePrice) * dishDetailInfo.quantity();
		}

		TableDetailInfo tableDetailInfo = TableDetailInfo.of(table,result,totalPrice);

		return tableDetailInfo;
	}

	/**
	 * 테이블 ID로 테이블 이름을 반환하는 API
	 * @param tableId
	 */
	@Override
	public TableNameDto getTableName(String tableId) {
		//1. 테이블 찾기
		Table table = tableRepository.findById(tableId)
			.orElseThrow(() -> new JDQRException(ErrorCode.TABLE_NOT_FOUND));

		TableNameDto tableNameDto = new TableNameDto(table.getName());

		return tableNameDto;
	}
}
