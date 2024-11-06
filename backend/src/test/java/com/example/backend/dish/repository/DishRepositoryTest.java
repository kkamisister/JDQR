package com.example.backend.dish.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.example.backend.common.config.QuerydslConfig;
import com.example.backend.config.ContainerSupport;
import com.example.backend.dish.entity.Dish;
import com.example.backend.dish.entity.DishCategory;
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
class DishRepositoryTest extends ContainerSupport {

	@Autowired
	private OwnerRepository ownerRepository;
	@Autowired
	private RestaurantRepository restaurantRepository;
	@Autowired
	private DishRepository dishRepository;
	@Autowired
	private DishCategoryRepository dishCategoryRepository;

	@DisplayName("식당이 가진 메뉴를 조회할 수 있다")
	@Test
	void findRestaurantDishes(){

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
			.restaurant(restaurant1)
			.name("면류")
			.build();
		DishCategory dishCategory2 = DishCategory.builder()
			.restaurant(restaurant1)
			.name("사이드")
			.build();
		DishCategory dishCategory3 = DishCategory.builder()
			.restaurant(restaurant3)
			.name("음료")
			.build();

		dishCategoryRepository.save(dishCategory1);
		dishCategoryRepository.save(dishCategory2);
		dishCategoryRepository.save(dishCategory3);


		Dish dish1 = Dish.builder()
			.dishCategory(dishCategory1)
			.name("짜장면")
			.price(8000)
			.description("흑인이 먹는 라면은?")
			.image("image.jpg")
			.build();

		Dish dish2 = Dish.builder()
			.dishCategory(dishCategory1)
			.name("간짜장")
			.price(9000)
			.description("흑인이 먹는 라면은?")
			.image("image.jpg")
			.build();

		Dish dish3 = Dish.builder()
			.dishCategory(dishCategory2)
			.name("치즈볼")
			.price(8000)
			.description("치즈볼")
			.image("image.jpg")
			.build();

		Dish dish4 = Dish.builder()
			.dishCategory(dishCategory2)
			.name("함박스테이크")
			.price(8000)
			.description("햄버그")
			.image("image.jpg")
			.build();

		Dish dish5 = Dish.builder()
			.dishCategory(dishCategory1)
			.name("콜라")
			.price(8000)
			.description("흑인이 먹는 물은?")
			.image("image.jpg")
			.build();

		Dish dish6 = Dish.builder()
			.dishCategory(dishCategory1)
			.name("사이다")
			.price(8000)
			.description("백인이 먹는 사이다는?")
			.image("image.jpg")
			.build();

		dishRepository.save(dish1);dishRepository.save(dish2);
		dishRepository.save(dish3);dishRepository.save(dish4);
		dishRepository.save(dish5);dishRepository.save(dish6);

		//when
		List<Dish> dishes = dishRepository.findDishesByRestaurant(restaurant1);

		//then
		assertThat(dishes.size()).isEqualTo(6);
	}
}