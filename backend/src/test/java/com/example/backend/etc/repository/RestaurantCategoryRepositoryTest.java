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
import com.example.backend.etc.entity.RestaurantCategory;

import lombok.extern.slf4j.Slf4j;

@DataJpaTest
@ActiveProfiles("test")
@Import({QuerydslConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class RestaurantCategoryRepositoryTest extends ContainerSupport {

	@Autowired
	private RestaurantCategoryRepository restaurantCategoryRepository;

	private final TestDataGenerator generator = new TestDataGenerator();

	@DisplayName("식당의 Major카테고리를 조회할 수 있다")
	@Test
	void findMajorCategory(){

		List<RestaurantCategory> restaurantCategories = generator.generateTestRestaurantCategoryList(false);

		restaurantCategoryRepository.saveAll(restaurantCategories);

		//when
		List<RestaurantCategory> allMajor = restaurantCategoryRepository.findAllMajor();

		//then
		assertThat(allMajor.size()).isEqualTo(4);
		assertThat(allMajor)
			.extracting(RestaurantCategory::getName)
			.containsExactly("한식","일식","양식","중식");


	}

}