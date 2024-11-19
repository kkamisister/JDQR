package com.example.backend.etc.repository;

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

import com.example.backend.TestDataGenerator;
import com.example.backend.common.config.QuerydslConfig;
import com.example.backend.common.enums.CategoryType;
import com.example.backend.config.ContainerSupport;
import com.example.backend.etc.entity.Restaurant;
import com.example.backend.etc.entity.RestaurantCategory;
import com.example.backend.etc.entity.RestaurantCategoryMap;
import com.example.backend.owner.entity.Owner;
import com.example.backend.owner.repository.OwnerRepository;

import lombok.extern.slf4j.Slf4j;

@DataJpaTest
@ActiveProfiles("test")
@Import({QuerydslConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class RestaurantCategoryMapRepositoryTest extends ContainerSupport {

	@Autowired
	private RestaurantCategoryMapRepository restaurantCategoryMapRepository;

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private OwnerRepository ownerRepository;

	@Autowired
	private RestaurantCategoryRepository restaurantCategoryRepository;

	private final TestDataGenerator generator = new TestDataGenerator();

	@DisplayName("식당 ID로 ,식당 - 카테고리 사이의 연결테이블을 조회할 수 있다")
	@Test
	void restaurantCategoryMapRepositoryTest() {
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

		List<RestaurantCategory> restaurantCategories = generator.generateTestRestaurantCategoryList(false);
		restaurantCategoryRepository.saveAll(restaurantCategories);

		List<RestaurantCategoryMap> restaurantCategoryMaps1 = generator.generateTestRestaurantCategoryMap(false);
		for(int i=0;i<4;i++){
			RestaurantCategoryMap restaurantCategoryMap = restaurantCategoryMaps1.get(i);
			restaurantCategoryMap.setRestaurant(restaurants.get(i));
			restaurantCategoryMap.setRestaurantCategory(restaurantCategories.get(i));
		}
		restaurantCategoryMapRepository.saveAll(restaurantCategoryMaps1);

		List<Integer> restaurantIds = restaurants.stream().map(Restaurant::getId).toList();

		//when
		List<RestaurantCategoryMap> restaurantCategoryMaps = restaurantCategoryMapRepository.findByRestaurandIds(
			restaurantIds);

		//then
		assertThat(restaurantCategoryMaps.size()).isEqualTo(4);
	}
}