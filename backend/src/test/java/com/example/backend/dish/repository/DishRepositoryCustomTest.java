package com.example.backend.dish.repository;

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
import com.example.backend.common.exception.ErrorCode;
import com.example.backend.common.exception.JDQRException;
import com.example.backend.config.ContainerSupport;
import com.example.backend.dish.entity.Dish;
import com.example.backend.dish.entity.DishCategory;
import com.example.backend.etc.entity.Restaurant;
import com.example.backend.etc.repository.RestaurantRepository;
import com.example.backend.order.entity.OrderItem;
import com.example.backend.order.repository.OrderItemRepository;
import com.example.backend.order.repository.OrderRepository;
import com.example.backend.owner.entity.Owner;
import com.example.backend.owner.repository.OwnerRepository;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

@ActiveProfiles("test")
@DataJpaTest
@Import({QuerydslConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class DishRepositoryCustomTest extends ContainerSupport {

	@Autowired
	private DishRepository dishRepository;
	@Autowired
	private DishCategoryRepository dishCategoryRepository;
	@Autowired
	private OwnerRepository ownerRepository;
	@Autowired
	private RestaurantRepository restaurantRepository;
	@Autowired
	private EntityManager em;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderItemRepository orderItemRepository;

	private final TestDataGenerator generator = new TestDataGenerator();

	@DisplayName("메뉴 ID로, (메뉴,메뉴와 연관된 카테고리)를 조회할 수 있다")
	@Test
	void findDishWithCategoryById() {

		Owner owner = Owner.builder()
			.email("sujipark2009@gmail.com")
			.code("ABCDEFG")
			.name("김영표")
			.build();

		ownerRepository.save(owner);

		List<Restaurant> restaurants = generator.generateTestRestaurantList(false);
		for(Restaurant restaurant : restaurants) {
			restaurant.setOwner(owner);
		}
		restaurantRepository.saveAll(restaurants);

		List<DishCategory> dishCategories = generator.generateTestDishCategoryList(false);
		for(int i=0;i<4;i++){
			DishCategory dishCategory = dishCategories.get(i);
			dishCategory.setRestaurant(restaurants.get(i%2));
		}
		dishCategoryRepository.saveAll(dishCategories);

		List<Dish> dishes = generator.generateTestDishList(false);
		for(int i=0;i<8;i++){
			Dish dish = dishes.get(i);
			dish.setDishCategory(dishCategories.get(i%4));
		}
		dishRepository.saveAll(dishes);

		//when
		Dish findDish = dishRepository.findDishWithCategoryById(dishes.get(0).getId())
			.orElseThrow(() -> new JDQRException(ErrorCode.DISH_NOT_FOUND));

		//then
		assertThat(findDish).extracting(Dish::getName).isEqualTo("더블QPC");

		assertThat(findDish.getDishCategory())
			.extracting(DishCategory::getName)
			.isEqualTo("햄버거");

	}

//	@DisplayName("주문에 속한 메뉴들을 조회할 수 있다")
//	@Test
//	void findAllByOrderTest(){
//
//		Owner owner = Owner.builder()
//			.email("sujipark2009@gmail.com")
//			.code("ABCDEFG")
//			.name("김영표")
//			.build();
//
//		ownerRepository.save(owner);
//
//		List<Restaurant> restaurants = generator.generateTestRestaurantList(false);
//		for(Restaurant restaurant : restaurants) {
//			restaurant.setOwner(owner);
//		}
//		restaurantRepository.saveAll(restaurants);
//
//		List<DishCategory> dishCategories = generator.generateTestDishCategoryList(false);
//		for(int i=0;i<4;i++){
//			DishCategory dishCategory = dishCategories.get(i%4);
//			dishCategory.setRestaurant(restaurants.get(i%2));
//		}
//		dishCategoryRepository.saveAll(dishCategories);
//
//		List<Dish> dishes1 = generator.generateTestDishList(false);
//		for(int i=0;i<8;i++){
//			Dish dish = dishes1.get(i);
//			dish.setDishCategory(dishCategories.get(i%4));
//		}
//		dishRepository.saveAll(dishes1);
//
//		List<ParentOrder> parentOrders = generator.generateTestOrderList(false);
//		orderRepository.saveAll(parentOrders);
//
//		List<OrderItem> orderItems = generator.generateTestOrderItemList(false);
//		for(int i=0;i<6;i++){
//			OrderItem orderItem = orderItems.get(i);
//			orderItem.setDish(dishes1.get(i));
//			orderItem.setParentOrder(parentOrders.get(i%4));
//		}
//		orderItemRepository.saveAll(orderItems);
//
//		//when
//		List<Dish> dishes = dishRepository.findAllByOrder(parentOrders.get(0));
//
//		//then
//		assertThat(dishes.size()).isEqualTo(2);
//
//		assertThat(dishes)
//			.extracting(Dish::getName)
//			.contains("더블QPC","베토디");
//
//
//	}

}