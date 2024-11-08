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

	@DisplayName("좌표 범위 안에 있는 음식점을 조회할 수 있다")
	@Test
	void findByCoordTest(){

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
			.latitude(30.0)
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


		Restaurant restaurant4 = Restaurant.builder()
			.owner(owner)
			.name("용수집2")
			.address("멀티캠퍼스23층")
			.phoneNumber("000-111-222")
			.latitude(30.0)
			.longitude(110.0)
			.open(true)
			.build();

		restaurantRepository.save(restaurant1);
		restaurantRepository.save(restaurant2);
		restaurantRepository.save(restaurant3);
		restaurantRepository.save(restaurant4);

		//when
		List<Restaurant> restaurants = restaurantRepository.findByCoord(30, 50, 100, 150);

		//then
		assertThat(restaurants.size()).isEqualTo(2);

		assertThat(restaurants)
			.extracting(Restaurant::getName)
			.contains("영표집","용수집2");

	}

}