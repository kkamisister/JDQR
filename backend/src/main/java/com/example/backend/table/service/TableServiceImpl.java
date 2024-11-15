package com.example.backend.table.service;

import static com.example.backend.table.dto.TableRequest.*;
import static com.example.backend.table.dto.TableResponse.*;

import java.util.*;

import com.example.backend.common.enums.EntityStatus;
import com.example.backend.dish.dto.DishResponse.DishDetailInfo;
import com.example.backend.dish.dto.OptionDto;
import com.example.backend.dish.entity.Dish;
import com.example.backend.dish.entity.DishOption;
import com.example.backend.dish.entity.Option;
import com.example.backend.dish.repository.DishOptionRepository;
import com.example.backend.dish.repository.DishRepository;
import com.example.backend.etc.entity.Restaurant;
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
	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;
	private final DishRepository dishRepository;
	private final DishOptionRepository dishOptionRepository;
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

		log.warn("업데이트 시작");
		log.warn("tableInfo : {}",tableInfo);
		//1. 점주를 찾는다
		ownerRepository.findById(userId)
			.orElseThrow(() -> new JDQRException(ErrorCode.USER_NOT_FOUND));

		//2. 테이블을 찾는다
		Table table = tableRepository.findById(tableInfo.tableId())
			.orElseThrow(() -> new JDQRException(ErrorCode.TABLE_NOT_FOUND));

		log.warn("tableId : {}",table.getId());
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

		return new QRInfo(link);
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

			// 테이블에 한 번도 주문이 들어가지 않은 상태일 경우
			if (optionalParentOrder.isEmpty()) {
				tableDetailInfos.add(getBaseTableInfo(table));
				continue;
			}

			ParentOrder parentOrder = optionalParentOrder.get();

			Map<DishDetailInfo, Integer> dishCountMap = new LinkedHashMap<>();
			if(table.getUseStatus().equals(UseStatus.AVAILABLE)){ // 남은 좌석의 수를 카운팅
				leftSeatNum += table.getPeople();
			}

			// 아직 결제되지 않은 항목만 가지고온다
			if(parentOrder.getOrderStatus().equals(OrderStatus.PENDING) || parentOrder.getOrderStatus().equals(OrderStatus.PAY_WAITING)){
				// 해당 주문에 있는 모든 Dish를 가지고온다

				List<Dish> dishes = dishRepository.findAllByOrder(parentOrder);
				for(Dish dish : dishes){
					List<DishOption> dishOptions = dishOptionRepository.findByDish(dish);
					List<Option> options = dishOptions.stream().map(DishOption::getOption).toList();
					List<OptionDto> optionDtos = options.stream().map(OptionDto::of).toList();
					// log.warn("optionDtos : {}",optionDtos);
					DishDetailInfo dishDetailInfo = DishDetailInfo.of(dish,optionDtos);

					// log.warn("dishDetailInfo : {}",dishDetailInfo);
					dishCountMap.put(dishDetailInfo, dishCountMap.getOrDefault(dishDetailInfo, 0) + 1);
					// log.warn("개수 : {}",dishCountMap.get(dishDetailInfo));
				}
			}

			// 수량이 누적된 dishCountMap에서 최종 DishDetailInfo 생성
			List<DishDetailInfo> result = dishCountMap.entrySet().stream()
				.map(entry -> entry.getKey().withQuantity(entry.getValue()))
				.toList();


			TableDetailInfo tableDetailInfo = TableDetailInfo.of(table,result);
			tableDetailInfos.add(tableDetailInfo);
		}

		TableResultDto tableResultDto = new TableResultDto(tableDetailInfos,leftSeatNum);

		return tableResultDto;
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
			.people(0)
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

		int totalPrice = 0;
		// 아직 결제되지 않은 항목만 가지고온다
		if(parentOrder.getOrderStatus().equals(OrderStatus.PENDING)){
			// 해당 주문에 있는 모든 Dish를 가지고온다
			List<Dish> dishes = dishRepository.findAllByOrder(parentOrder);
			for(Dish dish : dishes){
				List<DishOption> dishOptions = dishOptionRepository.findByDish(dish);
				List<Option> options = dishOptions.stream().map(DishOption::getOption).toList();
				List<OptionDto> optionDtos = options.stream().map(OptionDto::of).toList();
				DishDetailInfo dishDetailInfo = DishDetailInfo.of(dish,optionDtos);

				dishCountMap.put(dishDetailInfo, dishCountMap.getOrDefault(dishDetailInfo, 0) + 1);

				totalPrice += dish.getPrice();
			}
		}

		// 수량이 누적된 dishCountMap에서 최종 DishDetailInfo 생성
		List<DishDetailInfo> result = dishCountMap.entrySet().stream()
			.map(entry -> entry.getKey().withQuantity(entry.getValue()))
			.toList();


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
