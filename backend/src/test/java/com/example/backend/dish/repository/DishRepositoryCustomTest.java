package com.example.backend.dish.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;

import com.example.backend.common.config.QuerydslConfig;
import com.example.backend.common.exception.ErrorCode;
import com.example.backend.common.exception.JDQRException;
import com.example.backend.config.ContainerSupport;
import com.example.backend.dish.entity.Dish;
import com.example.backend.dish.entity.DishCategory;
import com.example.backend.etc.entity.Restaurant;
import com.example.backend.etc.repository.RestaurantRepository;
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

	@DisplayName("메뉴 ID로, (메뉴,메뉴와 연관된 카테고리)를 조회할 수 있다")
	@Test
	void findDishWithCategoryById() {

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

		restaurantRepository.save(restaurant1);
		restaurantRepository.save(restaurant2);
		restaurantRepository.save(restaurant3);

		DishCategory dishCategory1 = DishCategory.builder()
			.name("햄버거")
			.restaurant(restaurant1)
			.build();

		DishCategory dishCategory2 = DishCategory.builder()
			.name("사이드")
			.restaurant(restaurant1)
			.build();

		DishCategory dishCategory3 = DishCategory.builder()
			.name("치킨")
			.restaurant(restaurant1)
			.build();

		DishCategory dishCategory4 = DishCategory.builder()
			.name("음료")
			.restaurant(restaurant1)
			.build();

		dishCategoryRepository.save(dishCategory1);
		dishCategoryRepository.save(dishCategory2);
		dishCategoryRepository.save(dishCategory3);
		dishCategoryRepository.save(dishCategory4);

		Dish dish1 = Dish.builder()
			.name("더블QPC")
			.price(5000)
			.dishCategory(dishCategory1)
			.build();

		Dish dish2 = Dish.builder()
			.name("치즈볼")
			.price(5000)
			.dishCategory(dishCategory1)
			.build();

		Dish dish3 = Dish.builder()
			.name("허니콤보")
			.price(5000)
			.dishCategory(dishCategory1)
			.build();

		Dish dish4 = Dish.builder()
			.name("사이다")
			.price(5000)
			.dishCategory(dishCategory1)
			.build();

		Dish saved = dishRepository.save(dish1);
		dishRepository.save(dish2);
		dishRepository.save(dish3);
		dishRepository.save(dish4);


		//when
		Dish findDish = dishRepository.findDishWithCategoryById(saved.getId())
			.orElseThrow(() -> new JDQRException(ErrorCode.DISH_NOT_FOUND));

		//then
		assertThat(findDish.getName())
			.isEqualTo("더블QPC");

		assertThat(findDish.getDishCategory())
			.extracting(DishCategory::getName)
			.isEqualTo(dishCategory1.getName());


		Restaurant restaurant = findDish.getDishCategory().getRestaurant();
		assertThat(restaurant.getName()).isEqualTo("용수의식당");

	}
}