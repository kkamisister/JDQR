package com.example.backend.order.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.example.backend.TestDataGenerator;
import com.example.backend.common.config.QuerydslConfig;
import com.example.backend.config.ContainerSupport;
import com.example.backend.dish.entity.Choice;
import com.example.backend.dish.entity.Dish;
import com.example.backend.dish.entity.Option;
import com.example.backend.dish.repository.ChoiceRepository;
import com.example.backend.dish.repository.DishRepository;
import com.example.backend.dish.repository.OptionRepository;
import com.example.backend.order.entity.Order;
import com.example.backend.order.entity.OrderItem;
import com.example.backend.order.entity.OrderItemChoice;
import com.example.backend.order.entity.ParentOrder;
import com.example.backend.owner.entity.Owner;
import com.example.backend.owner.repository.OwnerRepository;
import com.example.backend.table.dto.TableOrderResponseVo;

import lombok.extern.slf4j.Slf4j;

@ActiveProfiles("test")
@DataJpaTest
@Import({QuerydslConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class OrderItemRepositoryCustomImplTest {

	@Autowired
	private OwnerRepository ownerRepository;
	@Autowired
	private OrderItemRepository orderItemRepository;
	@Autowired
	private ParentOrderRepository parentOrderRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private DishRepository dishRepository;
	@Autowired
	private OptionRepository optionRepository;
	@Autowired
	private ChoiceRepository choiceRepository;
	@Autowired
	private OrderItemChoiceRepository orderItemChoiceRepository;

	private final TestDataGenerator generator = new TestDataGenerator();

	@DisplayName("상위 주문을 통해 하위 주문안에 속한 주문음식을 조회할 수 있다")
	@Test
	void findOrderItemByParentOrder() {

		//given
		Owner owner = Owner.builder()
			.email("sujipark2009@gmail.com")
			.code("ABCDEFG")
			.name("김영표")
			.build();

		ownerRepository.save(owner);

		//parentOrder 1개에 order 2개씩 할당

		//6개
		List<ParentOrder> parentOrderList = generator.generateTestParentOrderList(false);
		List<ParentOrder> parentOrderList1 = parentOrderRepository.saveAll(parentOrderList);

		//6개
		List<Order> orders = generator.generateTestOrderList(false);

		for(int i=0;i<orders.size();i++){
			Order order = orders.get(i);
			order.setParentOrder(parentOrderList1.get(i%6));
		}
		List<Order> orders1 = orderRepository.saveAll(orders);

		// orderItem에 order매핑

		List<Dish> dishes = generator.generateTestDishList(false);
		List<Dish> dishes1 = dishRepository.saveAll(dishes);

		List<OrderItem> orderItems = generator.generateTestOrderItemList(false);
		for(int i=0;i<orderItems.size();i++){
			OrderItem orderItem = orderItems.get(i);
			orderItem.setOrder(orders1.get(i%6));
			orderItem.setDish(dishes1.get(i%8));
		}
		orderItemRepository.saveAll(orderItems);

		//when
		List<OrderItem> res = orderItemRepository.findOrderItemByParentOrder(
			parentOrderList.get(0));

		//then
		assertThat(res).extracting(OrderItem::getDish)
			.extracting(
				Dish::getName
			)
			.containsExactly("더블QPC","사이다");



	}

	// @DisplayName("상위 주문에 속한 모든 음식의 옵션들을 조회할 수 있다")
	// @Test
	// void findAllDishOptionsAndChoicesByParentOrder() {
	//
	// 	//given
	//
	// 	//parentOrder 1개에 order 2개씩 할당
	//
	// 	//6개
	// 	List<ParentOrder> parentOrderList = generator.generateTestParentOrderList(false);
	// 	List<ParentOrder> parentOrderList1 = parentOrderRepository.saveAll(parentOrderList);
	//
	// 	//6개
	// 	List<Order> orders = generator.generateTestOrderList(false);
	//
	// 	for(int i=0;i<orders.size();i++){
	// 		Order order = orders.get(i);
	// 		order.setParentOrder(parentOrderList1.get(i%6));
	// 	}
	// 	List<Order> orders1 = orderRepository.saveAll(orders);
	//
	// 	// orderItem에 order매핑
	//
	// 	List<Dish> dishes = generator.generateTestDishList(false);
	// 	List<Dish> dishes1 = dishRepository.saveAll(dishes);
	//
	// 	List<Choice> choices = generator.generateTestChoiceList(false);
	// 	List<Option> options = generator.generateTestOptionList(false);
	//
	// 	List<Choice> choices1 = choiceRepository.saveAll(choices);
	// 	List<Option> options1 = optionRepository.saveAll(options);
	//
	// 	for(int i=0;i<choices1.size();i++){
	// 		Choice choice = choices1.get(i);
	// 		choice.setOption(options1.get(i));
	// 	}
	//
	//
	// 	List<OrderItem> orderItems = generator.generateTestOrderItemList(false);
	// 	for(int i=0;i<orderItems.size();i++){
	// 		OrderItem orderItem = orderItems.get(i);
	// 		orderItem.setOrder(orders1.get(i%6));
	// 		orderItem.setDish(dishes1.get(i%8));
	// 	}
	//
	// 	List<OrderItem> orderItems1 = orderItemRepository.saveAll(orderItems);
	//
	// 	List<OrderItemChoice> orderItemChoices = new ArrayList<>();
	// 	for(int i=0;i<12;i++){
	// 		OrderItem orderItem = orderItems1.get(i);
	// 		OrderItemChoice orderItemChoice = OrderItemChoice.of(orderItem,choices1.get(i%6));
	// 		orderItemChoices.add(orderItemChoice);
	// 		log.warn("여기있다 : {}",orderItem);
	// 	}
	// 	orderItemChoiceRepository.saveAll(orderItemChoices);
	//
	// 	//when
	// 	List<TableOrderResponseVo> res = orderItemRepository.findAllDishOptionsAndChoicesByParentOrder(
	// 		parentOrderList1.get(0)
	// 	);
	//
	// 	log.warn("res : {}",res);
	//
	// 	//then
	// 	assertThat(res)
	// 		.extracting(
	// 			TableOrderResponseVo::getDishId
	// 		)
	// 		.containsExactly(dishes1.get(0).getId(),dishes1.get(6).getId());
	//
	// 	assertThat(res)
	// 		.extracting(
	// 			TableOrderResponseVo::getDishName
	// 		)
	// 		.containsExactly(dishes1.get(0).getName(),dishes1.get(6).getName());
	//
	// 	// assertThat(res)
	// 	// 	.extracting(
	// 	// 		TableOrderResponseVo::getChoiceName
	// 	// 	)
	// 	// 	.containsExactly(choices1.get(0).getName(),choices1.get(0).getName());
	// }
}