package com.example.backend.dish.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
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
import com.example.backend.dish.entity.Choice;
import com.example.backend.dish.entity.Dish;
import com.example.backend.dish.entity.DishCategory;
import com.example.backend.dish.entity.DishOption;
import com.example.backend.dish.entity.Option;
import com.example.backend.etc.entity.Restaurant;
import com.example.backend.etc.repository.RestaurantRepository;
import com.example.backend.owner.entity.Owner;
import com.example.backend.owner.repository.OwnerRepository;

@DataJpaTest
@ActiveProfiles("test")
@Import({QuerydslConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DishOptionRepositoryTest {

	@Autowired
	private OwnerRepository ownerRepository;
	@Autowired
	private DishRepository dishRepository;
	@Autowired
	private DishCategoryRepository dishCategoryRepository;
	@Autowired
	private RestaurantRepository restaurantRepository;
	@Autowired
	private OptionRepository optionRepository;
	@Autowired
	private DishOptionRepository dishOptionRepository;

	private final TestDataGenerator generator  = new TestDataGenerator();

	@DisplayName("메뉴 ID로 메뉴 옵션을 조회할 수 있다")
	@Test
	void findOptionsByDishId(){

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
			dishCategory.setRestaurant(restaurants.get(i));
		}
		dishCategoryRepository.saveAll(dishCategories);

		List<Dish> dishes = generator.generateTestDishList(false);
		for(int i=0;i<8;i++){
			Dish dish = dishes.get(i);
			dish.setDishCategory(dishCategories.get(i%2));
		}
		dishRepository.saveAll(dishes);

		List<Option> options = generator.generateTestOptionList(false);
		for(int i=0;i<6;i++){
			Option option = options.get(i);
			option.setRestaurant(restaurants.get(i%4));
		}
		optionRepository.saveAll(options);

		List<DishOption> dishOptionList = generator.generateTestDishOptionList(false);
		for(int i=0;i<6;i++){
			DishOption dishOption = dishOptionList.get(i);
			dishOption.setDish(dishes.get(i%4));
			dishOption.setOption(options.get(i%4));
		}
		dishOptionRepository.saveAll(dishOptionList);

		//when
		List<DishOption> dishOptions = dishOptionRepository.findByDishId(dishes.get(0).getId());

		//then
		assertThat(dishOptions)
			.extracting(DishOption::getOption)
			.extracting(Option::getName)
			.contains("부위");

	}
}