package com.example.backend.etc.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.example.backend.common.config.QuerydslConfig;
import com.example.backend.common.enums.CategoryType;
import com.example.backend.etc.entity.RestaurantCategory;

import lombok.extern.slf4j.Slf4j;

@DataJpaTest
@ActiveProfiles("test")
@Import({QuerydslConfig.class})
@Slf4j
class RestaurantCategoryRepositoryTest {

	@Autowired
	private RestaurantCategoryRepository restaurantCategoryRepository;

	@DisplayName("식당의 Major카테고리를 조회할 수 있다")
	@Test
	void findMajorCategory(){

		RestaurantCategory restaurantCategory1 = RestaurantCategory.builder()
			.name("일식")
			.categoryType(CategoryType.MAJOR)
			.build();

		RestaurantCategory restaurantCategory2 = RestaurantCategory.builder()
			.name("일식")
			.categoryType(CategoryType.MAJOR)
			.build();

		RestaurantCategory restaurantCategory3 = RestaurantCategory.builder()
			.name("일식")
			.categoryType(CategoryType.MINOR)
			.build();

		RestaurantCategory restaurantCategory4 = RestaurantCategory.builder()
			.name("일식")
			.categoryType(CategoryType.MAJOR)
			.build();

		RestaurantCategory restaurantCategory5 = RestaurantCategory.builder()
			.name("일식")
			.categoryType(CategoryType.MINOR)
			.build();

		restaurantCategoryRepository.save(restaurantCategory1);
		restaurantCategoryRepository.save(restaurantCategory2);
		restaurantCategoryRepository.save(restaurantCategory3);
		restaurantCategoryRepository.save(restaurantCategory4);
		restaurantCategoryRepository.save(restaurantCategory5);

		//when
		List<RestaurantCategory> allMajor = restaurantCategoryRepository.findAllMajor();

		//then
		assertThat(allMajor.size()).isEqualTo(3);

	}

}