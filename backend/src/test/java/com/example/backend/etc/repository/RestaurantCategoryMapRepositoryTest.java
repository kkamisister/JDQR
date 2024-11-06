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

	@DisplayName("식당 ID로 ,식당 - 카테고리 사이의 연결테이블을 조회할 수 있다")
	@Test
	void restaurantCategoryMapRepositoryTest() {
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


		RestaurantCategoryMap restaurantCategoryMap = RestaurantCategoryMap.builder()
			.restaurant(restaurant1)
			.restaurantCategory(restaurantCategory1)
			.build();

		RestaurantCategoryMap restaurantCategoryMap2 = RestaurantCategoryMap.builder()
			.restaurant(restaurant1)
			.restaurantCategory(restaurantCategory2)
			.build();

		RestaurantCategoryMap restaurantCategoryMap3 = RestaurantCategoryMap.builder()
			.restaurant(restaurant2)
			.restaurantCategory(restaurantCategory2)
			.build();

		RestaurantCategoryMap restaurantCategoryMap4 = RestaurantCategoryMap.builder()
			.restaurant(restaurant3)
			.restaurantCategory(restaurantCategory3)
			.build();

		RestaurantCategoryMap restaurantCategoryMap5 = RestaurantCategoryMap.builder()
			.restaurant(restaurant3)
			.restaurantCategory(restaurantCategory4)
			.build();

		restaurantCategoryMapRepository.save(restaurantCategoryMap);
		restaurantCategoryMapRepository.save(restaurantCategoryMap2);
		restaurantCategoryMapRepository.save(restaurantCategoryMap3);
		restaurantCategoryMapRepository.save(restaurantCategoryMap4);
		restaurantCategoryMapRepository.save(restaurantCategoryMap5);

		List<Integer> restaurantIds = List.of(restaurant1.getId(), restaurant2.getId(), restaurant3.getId());

		//when
		List<RestaurantCategoryMap> restaurantCategoryMaps = restaurantCategoryMapRepository.findByRestaurandIds(
			restaurantIds);

		//then
		assertThat(restaurantCategoryMaps.size()).isEqualTo(5);
	}
}