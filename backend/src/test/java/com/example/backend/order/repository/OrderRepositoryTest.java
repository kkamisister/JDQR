package com.example.backend.order.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;

import com.example.backend.common.config.QuerydslConfig;
import com.example.backend.config.ContainerSupport;
import com.example.backend.etc.entity.Restaurant;
import com.example.backend.etc.repository.RestaurantRepository;
import com.example.backend.order.entity.Order;
import com.example.backend.order.enums.OrderStatus;
import com.example.backend.order.enums.PaymentMethod;
import com.example.backend.owner.entity.Owner;
import com.example.backend.owner.repository.OwnerRepository;
import com.example.backend.table.entity.Table;
import com.example.backend.table.repository.TableRepository;

import lombok.extern.slf4j.Slf4j;

@ActiveProfiles("test")
@DataJpaTest
@Import({QuerydslConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class OrderRepositoryTest extends ContainerSupport {

	@Autowired
	private OwnerRepository ownerRepository;
	@Autowired
	private RestaurantRepository restaurantRepository;
	@Autowired
	private OrderRepository orderRepository;

	@DisplayName("테이블 ID로 주문을 조회할 수 있다")
	@Test
	void findByTableId() {

		Owner owner = Owner.builder()
			.email("sujipark2009@gmail.com")
			.code("ABCDEFG")
			.name("김영표")
			.build();

		ownerRepository.save(owner);

		Restaurant restaurant1 = Restaurant.builder()
			.owner(owner)
			.name("용수의식당")
			.address("멀티캠퍼스10층")
			.phoneNumber("000-111-222")
			.latitude(10.0)
			.longitude(130.0)
			.open(true)
			.build();

		Restaurant restaurant2 = Restaurant.builder()
			.owner(owner)
			.name("영표집")
			.address("멀티캠퍼스11층")
			.phoneNumber("000-111-222")
			.latitude(5.0)
			.longitude(130.0)
			.open(true)
			.build();

		Restaurant restaurant3 = Restaurant.builder()
			.owner(owner)
			.name("용수집")
			.address("멀티캠퍼스13층")
			.phoneNumber("000-111-222")
			.latitude(10.0)
			.longitude(150.0)
			.open(true)
			.build();

		Restaurant save = restaurantRepository.save(restaurant1);
		Restaurant save1 = restaurantRepository.save(restaurant2);
		Restaurant save2 = restaurantRepository.save(restaurant3);


		Order order = Order.builder()
			.tableId("11111")
			.menuCnt(5)
			.orderStatus(OrderStatus.PENDING)
			.paymentMethod(PaymentMethod.UNDEFINED)
			.build();
		Order order2 = Order.builder()
			.tableId("11111")
			.menuCnt(9)
			.orderStatus(OrderStatus.PENDING)
			.paymentMethod(PaymentMethod.UNDEFINED)
			.build();
		Order order3 = Order.builder()
			.tableId("11111")
			.menuCnt(7)
			.orderStatus(OrderStatus.PENDING)
			.paymentMethod(PaymentMethod.UNDEFINED)
			.build();

		orderRepository.save(order);
		orderRepository.save(order2);
		orderRepository.save(order3);

		//when
		List<Order> orderList = orderRepository.findByTableId("11111");

		//then
		assertThat(orderList.size()).isEqualTo(3);

		assertThat(orderList).extracting(Order::getMenuCnt)
			.containsExactly(5,9,7);

	}
}