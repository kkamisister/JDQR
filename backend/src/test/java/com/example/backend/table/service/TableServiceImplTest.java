package com.example.backend.table.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import com.example.backend.dish.dto.ChoiceDto;
import com.example.backend.dish.dto.DishResponse;
import com.example.backend.dish.dto.OptionDto;
import com.example.backend.order.entity.ParentOrder;
import com.example.backend.order.enums.OrderStatus;
import com.example.backend.order.enums.PaymentMethod;
import com.example.backend.order.repository.OrderItemRepository;
import com.example.backend.order.repository.ParentOrderRepository;
import com.example.backend.owner.entity.Owner;
import com.example.backend.owner.repository.OwnerRepository;
import com.example.backend.table.dto.TableOrderResponseVo;
import com.example.backend.table.dto.TableResponse;
import com.example.backend.table.dto.TableResponse.TableDetailInfo;
import com.example.backend.table.entity.Table;
import com.example.backend.table.repository.TableRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(MockitoExtension.class)
class TableServiceImplTest {

	@InjectMocks
	private TableServiceImpl tableService;

	@Mock
	private OwnerRepository ownerRepository;
	@Mock
	private TableRepository tableRepository;
	@Mock
	private ParentOrderRepository parentOrderRepository;
	@Mock
	private OrderItemRepository orderItemRepository;


	@Test
	void getAllTables() {
	}

	@DisplayName("점주는 테이블의 상세정보(주문 현황 .. )를 조회할 수 있다")
	@Test
	void getTable() {
		//given

		Owner owner = Owner.builder()
			.id(1)
			.email("sujipark2009@gmail.com")
			.code("ABCDEFG")
			.name("김영표")
			.build();


		Table table = Table.builder()
			.id("11111")
			.name("영표의식탁")
			.color("#ffffff")
			.people(6)
			.build();

		ParentOrder parentOrder = ParentOrder.builder()
			.id(1)
			.tableId(table.getId())
			.orderStatus(OrderStatus.PENDING)
			.peopleNum(4)
			.paymentMethod(PaymentMethod.MENU_DIVIDE)
			.build();

		TableOrderResponseVo vo1 = new TableOrderResponseVo(
			1,1,"음식1","군침싹1","image1.png",5000,1,"당도",1,"달달",100
		);
		TableOrderResponseVo vo2 = new TableOrderResponseVo(
			2,2,"음식2","군침싹2","image2.png",4000,2,"맵기",2,"활활",0
		);
		TableOrderResponseVo vo3 = new TableOrderResponseVo(
			3,3,"음식3","군침싹3","image3.png",3000,3,"후식",3,"아이스크림",500
		);
		List<TableOrderResponseVo> voList = List.of(vo1,vo2,vo3);

		when(ownerRepository.findById(any()))
			.thenReturn(Optional.of(owner));

		when(tableRepository.findById(any()))
			.thenReturn(Optional.of(table));

		when(parentOrderRepository.findFirstByTableIdOrderByIdDesc(any()))
			.thenReturn(Optional.of(parentOrder));

		when(orderItemRepository.findAllDishOptionsAndChoicesByParentOrder(any(ParentOrder.class)))
			.thenReturn(voList);

		//when
		TableDetailInfo res = tableService.getTable(table.getId(), owner.getId());

		//then
		assertThat(res.tableId()).isEqualTo(table.getId());
		assertThat(res.dishes())
			.extracting(DishResponse.DishDetailInfo::dishName)
			.contains("음식1","음식2","음식3");
		assertThat(res.totalPrice()).isEqualTo(
			vo1.getPrice()+vo1.getChoicePrice()+
				vo2.getPrice()+vo2.getChoicePrice()+
				vo3.getPrice()+vo3.getChoicePrice()
		);
		assertThat(res.dishes())
			.flatExtracting(DishResponse.DishDetailInfo::options)
			.extracting(OptionDto::getOptionName)
			.contains("당도","맵기","후식");

		assertThat(res.dishes())
			.flatExtracting(DishResponse.DishDetailInfo::options)
			.flatExtracting(OptionDto::getChoices)
			.extracting(ChoiceDto::getChoiceName)
			.contains(
				"달달","활활","아이스크림"
			);
	}
}