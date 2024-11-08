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

import com.example.backend.TestDataGenerator;
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

	private final TestDataGenerator generator = new TestDataGenerator();

	@DisplayName("테이블 ID로 주문을 조회할 수 있다")
	@Test
	void findByTableId() {

		Owner owner = Owner.builder()
			.email("sujipark2009@gmail.com")
			.code("ABCDEFG")
			.name("김영표")
			.build();

		ownerRepository.save(owner);


		// 식당 생성
		List<Restaurant> restaurants = generator.generateTestRestaurantList(false);
		for(Restaurant restaurant : restaurants) {
			restaurant.setOwner(owner);
		}

		restaurantRepository.saveAll(restaurants);

		// order 생성
		List<Order> orders = generator.generateTestOrderList(false);
		orderRepository.saveAll(orders);


		//when
		List<Order> orderList = orderRepository.findByTableId("11111");

		//then
		assertThat(orderList.size()).isEqualTo(2);

		assertThat(orderList).extracting(Order::getMenuCnt)
			.containsExactly(5,9);

	}
}