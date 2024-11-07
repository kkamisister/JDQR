package com.example.backend.etc.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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

@DataJpaTest
@ActiveProfiles("test")
@Import({QuerydslConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RestaurantRepositoryTest extends ContainerSupport {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private OwnerRepository ownerRepository;

	@DisplayName("사용자 화면 범위내의 레스토랑을 조회할 수 있다")
	@Test
	void getAllRestaurants() {

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

		//when
		List<Restaurant> byCoord = restaurantRepository.findByCoord(0, 100, 0, 150);

		//then
		assertThat(byCoord.size()).isEqualTo(3);
	}

}