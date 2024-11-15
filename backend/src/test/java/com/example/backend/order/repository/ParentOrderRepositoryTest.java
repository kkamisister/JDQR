package com.example.backend.order.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import com.example.backend.order.entity.ParentOrder;
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
import com.example.backend.etc.entity.Restaurant;
import com.example.backend.etc.repository.RestaurantRepository;
import com.example.backend.owner.entity.Owner;
import com.example.backend.owner.repository.OwnerRepository;

import lombok.extern.slf4j.Slf4j;

@ActiveProfiles("test")
@DataJpaTest
@Import({QuerydslConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class ParentOrderRepositoryTest extends ContainerSupport {

	@Autowired
	private OwnerRepository ownerRepository;
	@Autowired
	private RestaurantRepository restaurantRepository;
	@Autowired
	private OrderRepository orderRepository;

	private final TestDataGenerator generator = new TestDataGenerator();

//	@DisplayName("테이블 ID로 주문을 조회할 수 있다")
//	@Test
//	void findByTableId() {
//
//		Owner owner = Owner.builder()
//			.email("sujipark2009@gmail.com")
//			.code("ABCDEFG")
//			.name("김영표")
//			.build();
//
//		ownerRepository.save(owner);
//
//
//		// 식당 생성
//		List<Restaurant> restaurants = generator.generateTestRestaurantList(false);
//		for(Restaurant restaurant : restaurants) {
//			restaurant.setOwner(owner);
//		}
//
//		restaurantRepository.saveAll(restaurants);
//
//		// order 생성
//		List<ParentOrder> parentOrders = generator.generateTestOrderList(false);
//		orderRepository.saveAll(parentOrders);
//
//
//		//when
//		List<ParentOrder> parentOrderList = orderRepository.findByTableId("11111");
//
//		//then
//		assertThat(parentOrderList.size()).isEqualTo(2);
//
//		assertThat(parentOrderList).extracting(ParentOrder::getMenuCnt)
//			.containsExactly(5,9);
//
//	}
}