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
import com.example.backend.config.ContainerSupport;
import com.example.backend.dish.entity.Dish;
import com.example.backend.dish.entity.DishCategory;
import com.example.backend.dish.entity.DishTag;
import com.example.backend.dish.entity.Tag;
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
class DishTagRepositoryTest extends ContainerSupport {

	@Autowired
	private DishTagRepository dishTagRepository;
	@Autowired
	private RestaurantRepository restaurantRepository;
	@Autowired
	private OwnerRepository ownerRepository;
	@Autowired
	private DishRepository dishRepository;
	@Autowired
	private TagRepository tagRepository;
	@Autowired
	private DishCategoryRepository dishCategoryRepository;

	@DisplayName("메뉴를 통해 메뉴태그를 조회할 수 있다")
	@Test
	void findTagsByDishTest(){

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
			.name("치킨")
			.restaurant(restaurant1)
			.build();

		DishCategory dishCategory3 = DishCategory.builder()
			.name("사이드")
			.restaurant(restaurant1)
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


		Tag tag1 = Tag.builder()
			.name("인기")
			.build();
		Tag tag2 = Tag.builder()
			.name("화재")
			.build();
		Tag tag3 = Tag.builder()
			.name("맛있음")
			.build();
		Tag tag4 = Tag.builder()
			.name("맛없음")
			.build();
		Tag tag5 = Tag.builder()
			.name("영표픽")
			.build();
		Tag tag6 = Tag.builder()
			.name("용수픽")
			.build();

		tagRepository.save(tag1);tagRepository.save(tag2);
		tagRepository.save(tag3);tagRepository.save(tag4);
		tagRepository.save(tag5);tagRepository.save(tag6);

		DishTag dishTag1 = DishTag.builder()
			.dish(dish1)
			.tag(tag1)
			.build();
		DishTag dishTag2 = DishTag.builder()
			.dish(dish1)
			.tag(tag2)
			.build();
		DishTag dishTag3 = DishTag.builder()
			.dish(dish1)
			.tag(tag3)
			.build();
		DishTag dishTag4 = DishTag.builder()
			.dish(dish2)
			.tag(tag4)
			.build();
		DishTag dishTag5 = DishTag.builder()
			.dish(dish2)
			.tag(tag5)
			.build();
		DishTag dishTag6 = DishTag.builder()
			.dish(dish2)
			.tag(tag6)
			.build();

		dishTagRepository.save(dishTag1);dishTagRepository.save(dishTag2);
		dishTagRepository.save(dishTag3);dishTagRepository.save(dishTag4);
		dishTagRepository.save(dishTag5);dishTagRepository.save(dishTag6);

		//when
		List<DishTag> tagsByDish = dishTagRepository.findTagsByDish(dish1);

		//then
		assertThat(tagsByDish).extracting(DishTag::getTag)
			.extracting(Tag::getName)
			.contains("인기","화재","맛있음");

	}

}