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

import com.example.backend.TestDataGenerator;
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

	private final TestDataGenerator generator = new TestDataGenerator();

	@DisplayName("식당이 가진 메뉴를 조회할 수 있다")
	@Test
	void findRestaurantDishes(){

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

		List<Dish> dishes1 = generator.generateTestDishList(false);
		for(int i=0;i<4;i++){
			Dish dish = dishes1.get(i);
			dish.setDishCategory(dishCategories.get(i%2));
		}
		dishRepository.saveAll(dishes1);

		//when
		List<Dish> dishes = dishRepository.findDishesByRestaurant(restaurants.get(0));

		//then
		assertThat(dishes.size()).isEqualTo(2);
		assertThat(dishes).extracting(Dish::getName)
			.containsExactly("더블QPC","콜라");
	}

	@DisplayName("키워드를 포함한 메뉴들을 조회할 수 있다")
	@Test
	void findDishesByKeyword(){

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

		List<Dish> dishes1 = generator.generateTestDishList(false);
		for(int i=0;i<6;i++){
			Dish dish = dishes1.get(i);
			dish.setDishCategory(dishCategories.get(i%2));
		}
		dishRepository.saveAll(dishes1);

		//when
		List<Dish> dishes = dishRepository.findDishesByKeyword(restaurants.get(0), "콜");

		//then
		assertThat(dishes.size()).isEqualTo(1);
		
		assertThat(dishes)
			.extracting(Dish::getName)
			.contains("콜라");

	}

}