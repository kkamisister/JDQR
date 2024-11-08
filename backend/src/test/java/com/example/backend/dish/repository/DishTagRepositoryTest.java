package com.example.backend.dish.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import com.example.backend.dish.entity.DishTag;
import com.example.backend.dish.entity.Tag;
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
class DishTagRepositoryTest {

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

	private final TestDataGenerator generator = new TestDataGenerator();


	@DisplayName("메뉴를 통해 메뉴태그를 조회할 수 있다")
	@Test
	void findTagsByDishTest(){

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
		List<Restaurant> restaurants1 = restaurantRepository.saveAll(restaurants);

		List<DishCategory> dishCategories = generator.generateTestDishCategoryList(false);
		for(int i=0;i<dishCategories.size();i++){
			DishCategory dishCategory = dishCategories.get(i);
			dishCategory.setRestaurant(restaurants.get(i));
		}
		List<DishCategory> dishCategories1 = dishCategoryRepository.saveAll(dishCategories);

		List<Dish> dishes = generator.generateTestDishList(false);
		for(int i=0;i<dishes.size();i++){
			Dish dish = dishes.get(i);
			dish.setDishCategory(dishCategories.get(i/2));
		}
		List<Dish> dishes1 = dishRepository.saveAll(dishes);

		// 태그생성
		List<Tag> tags = generator.generateTestTagList(false);
		List<Tag> tags1 = tagRepository.saveAll(tags);

		// 메뉴태그 생성
		List<DishTag> dishTags = generator.generateTestDishTagList(false);
		for(int i=0;i<dishTags.size();i++){
			DishTag dishTag = dishTags.get(i);
			dishTag.setTagAndDish(tags1.get(i),dishes1.get(i/4));
		}
		List<DishTag> dishTags1 = dishTagRepository.saveAll(dishTags);

		//when
		List<DishTag> tagsByDish = dishTagRepository.findTagsByDish(dishes1.get(0));

		//then
		assertThat(tagsByDish).extracting(DishTag::getTag)
			.extracting(Tag::getName)
			.contains("인기","화재","맛있음");

	}

}