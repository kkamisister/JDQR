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
import com.example.backend.config.ContainerSupport;
import com.example.backend.etc.entity.Restaurant;
import com.example.backend.owner.entity.Owner;
import com.example.backend.owner.repository.OwnerRepository;

import lombok.extern.slf4j.Slf4j;

@ActiveProfiles("test")
@DataJpaTest
@Import({QuerydslConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class RestaurantRepositoryCustomTest extends ContainerSupport {

	@Autowired
	private RestaurantRepository restaurantRepository;
	@Autowired
	private OwnerRepository ownerRepository;

	private final TestDataGenerator generator = new TestDataGenerator();

	@DisplayName("좌표 범위 안에 있는 음식점을 조회할 수 있다")
	@Test
	void findByCoordTest(){

		Owner owner = Owner.builder()
			.email("sujipark2009@gmail.com")
			.code("ABCDEFG")
			.name("김영표")
			.build();

		ownerRepository.save(owner);

		List<Restaurant> restaurants1 = generator.generateTestRestaurantList(false);
		for(Restaurant restaurant : restaurants1) {
			restaurant.setOwner(owner);
		}
		restaurantRepository.saveAll(restaurants1);

		//when
		List<Restaurant> restaurants = restaurantRepository.findByCoord(30, 50, 100, 150);

		//then
		assertThat(restaurants.size()).isEqualTo(2);

		assertThat(restaurants)
			.extracting(Restaurant::getName)
			.contains("용수집","영표의식당");

	}

}