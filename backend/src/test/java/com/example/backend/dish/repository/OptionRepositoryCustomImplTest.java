package com.example.backend.dish.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.example.backend.TestDataGenerator;
import com.example.backend.common.config.QuerydslConfig;
import com.example.backend.etc.entity.Restaurant;
import com.example.backend.etc.repository.RestaurantRepository;

import lombok.extern.slf4j.Slf4j;

@ActiveProfiles("test")
@DataJpaTest
@Import({QuerydslConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class OptionRepositoryCustomImplTest {

	@Autowired
	private RestaurantRepository restaurantRepository;
	@Autowired
	private OptionRepository optionRepository;
	@Autowired
	private ChoiceRepository choiceRepository;

	private final TestDataGenerator generator = new TestDataGenerator();

	@DisplayName("식당에 있는 모든 옵션을 조회할 수 있다")
	@Test
	void findAllOptionByRestaurant() {

		// List<Restaurant> restaurants = generator.generateTestRestaurantList(false);

	}
}