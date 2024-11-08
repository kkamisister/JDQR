package com.example.backend.dish.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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

	@DisplayName("메뉴 ID로 메뉴 옵션을 조회할 수 있다")
	@Test
	void findOptionsByDishId(){

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

		Dish saveDish1 = dishRepository.save(dish1);
		Dish saveDish2 = dishRepository.save(dish2);
		dishRepository.save(dish3);dishRepository.save(dish4);
		dishRepository.save(dish5);dishRepository.save(dish6);


		Option option1 = Option.builder()
			.restaurant(restaurant1)
			.name("치즈추가")
			.maxChoiceCount(2)
			.mandatory(true)
			.build();
		Option option2 = Option.builder()
			.restaurant(restaurant1)
			.name("패티추가")
			.maxChoiceCount(2)
			.mandatory(true)
			.build();
		Option option3 = Option.builder()
			.restaurant(restaurant2)
			.name("토마토제거")
			.maxChoiceCount(2)
			.mandatory(true)
			.build();
		Option option4 = Option.builder()
			.restaurant(restaurant2)
			.name("양파추가")
			.maxChoiceCount(2)
			.mandatory(true)
			.build();
		Option option5 = Option.builder()
			.restaurant(restaurant3)
			.name("피클제거")
			.maxChoiceCount(2)
			.mandatory(true)
			.build();
		Option option6 = Option.builder()
			.restaurant(restaurant3)
			.name("상추추가")
			.maxChoiceCount(2)
			.mandatory(true)
			.build();

		optionRepository.save(option1);optionRepository.save(option2);
		optionRepository.save(option3);optionRepository.save(option4);
		optionRepository.save(option5);optionRepository.save(option6);

		DishOption dishOption1 = DishOption.builder()
			.dish(dish1)
			.option(option1)
			.build();
		DishOption dishOption2 = DishOption.builder()
			.dish(dish1)
			.option(option2)
			.build();
		DishOption dishOption3 = DishOption.builder()
			.dish(dish1)
			.option(option3)
			.build();
		DishOption dishOption4 = DishOption.builder()
			.dish(dish2)
			.option(option1)
			.build();
		DishOption dishOption5 = DishOption.builder()
			.dish(dish2)
			.option(option2)
			.build();
		DishOption dishOption6 = DishOption.builder()
			.dish(dish2)
			.option(option3)
			.build();

		dishOptionRepository.save(dishOption1);dishOptionRepository.save(dishOption2);
		dishOptionRepository.save(dishOption3);dishOptionRepository.save(dishOption4);
		dishOptionRepository.save(dishOption5);dishOptionRepository.save(dishOption6);

		//when
		List<DishOption> dishOptions = dishOptionRepository.findByDishId(saveDish1.getId());

		//then
		assertThat(dishOptions)
			.extracting(DishOption::getOption)
			.extracting(Option::getName)
			.contains("치즈추가","패티추가","토마토제거");

	}
}