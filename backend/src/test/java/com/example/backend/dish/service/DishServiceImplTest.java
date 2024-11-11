package com.example.backend.dish.service;

import static com.example.backend.dish.dto.DishResponse.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.backend.TestDataGenerator;
import com.example.backend.common.redis.repository.RedisHashRepository;
import com.example.backend.dish.dto.DishResponse.DishSummaryInfo;
import com.example.backend.dish.dto.DishResponse.DishSummaryResultDto;
import com.example.backend.dish.entity.Dish;
import com.example.backend.dish.entity.DishCategory;
import com.example.backend.dish.repository.DishRepository;
import com.example.backend.etc.entity.Restaurant;
import com.example.backend.etc.repository.RestaurantRepository;
import com.example.backend.owner.entity.Owner;
import com.example.backend.table.entity.Table;
import com.example.backend.table.repository.TableRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(MockitoExtension.class)
class DishServiceImplTest {

	@InjectMocks
	private DishServiceImpl dishService;

	@Mock
	private TableRepository tableRepository;
	@Mock
	private RestaurantRepository restaurantRepository;
	@Mock
	private DishRepository dishRepository;
	@Mock
	private RedisHashRepository redisHashRepository;


	private final TestDataGenerator generator = new TestDataGenerator();

	@DisplayName("음식점의 메뉴판을 조회할 수 있다")
	@Test
	void getAllDishes() {

		Owner owner = Owner.builder()
			.id(1)
			.email("sujipark2009@gmail.com")
			.code("ABCDEFG")
			.name("김영표")
			.build();


		Table table = Table.builder()
			.id("11111")
			.name("영표의식탁")
			.color("#ffffff")
			.people(6)
			.build();

		List<Restaurant> restaurants = generator.generateTestRestaurantList(true);
		for(Restaurant restaurant : restaurants) {
			restaurant.setOwner(owner);
		}

		List<DishCategory> dishCategories = generator.generateTestDishCategoryList(true);
		for(int i=0;i<4;i++){
			DishCategory dishCategory = dishCategories.get(i);
			dishCategory.setRestaurant(restaurants.get(i));
		}

		List<Dish> dishes1 = generator.generateTestDishList(true);
		for(int i=0;i<8;i++){
			Dish dish = dishes1.get(i);
			dish.setDishCategory(dishCategories.get(i%4));
		}

		when(restaurantRepository.findById(anyInt()))
			.thenReturn(Optional.ofNullable(restaurants.get(0)));

		when(tableRepository.findById(anyString()))
			.thenReturn(Optional.ofNullable(table));

		when(dishRepository.findDishesByRestaurant(any(Restaurant.class)))
			.thenReturn(dishes1);

		when(redisHashRepository.getCurrentUserCnt(anyString()))
			.thenReturn(5);

		//when
		DishSummaryResultDto resultDto = dishService.getAllDishes(table.getId());

		//then
		assertThat(resultDto.tableId())
			.isEqualTo("11111");

		assertThat(resultDto.tableName())
			.isEqualTo("영표의식탁");

		assertThat(resultDto.peopleCount()).isEqualTo(5);

		assertThat(resultDto.dishCategories())
			.contains("햄버거");

		assertThat(resultDto.dishes())
			.flatExtracting(DishSummaryInfo::items)
			.extracting(DishSimpleInfo::dishName)
			.contains("더블QPC","베토디");

	}

	@Test
	void getDish() {

	}

	@Test
	void getSearchedDishes() {

	}
}