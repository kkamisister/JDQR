package com.example.backend.dish.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.backend.common.redis.repository.RedisHashRepository;
import com.example.backend.dish.dto.DishResponse;
import com.example.backend.dish.dto.DishResponse.DishSummaryResultDto;
import com.example.backend.dish.entity.Dish;
import com.example.backend.dish.entity.DishCategory;
import com.example.backend.dish.entity.DishTag;
import com.example.backend.dish.entity.Tag;
import com.example.backend.dish.repository.DishRepository;
import com.example.backend.dish.repository.DishTagRepository;
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
	@Mock
	private DishTagRepository dishTagRepository;


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

		Restaurant restaurant1 = Restaurant.builder()
			.id(1)
			.owner(owner)
			.name("용수의식당")
			.address("멀티캠퍼스10층")
			.phoneNumber("000-111-222")
			.latitude(10.0)
			.longitude(130.0)
			.open(true)
			.build();

		DishCategory dishCategory1 = DishCategory.builder()
			.id(1)
			.restaurant(restaurant1)
			.name("면류")
			.build();

		DishCategory dishCategory2 = DishCategory.builder()
			.id(2)
			.restaurant(restaurant1)
			.name("사이드")
			.build();

		DishCategory dishCategory3 = DishCategory.builder()
			.id(3)
			.restaurant(restaurant1)
			.name("햄버그")
			.build();


		Dish dish1 = Dish.builder()
			.id(1)
			.dishCategory(dishCategory1)
			.name("짜장면")
			.price(8000)
			.description("흑인이 먹는 라면은?")
			.image("image.jpg")
			.build();

		Dish dish2 = Dish.builder()
			.id(2)
			.dishCategory(dishCategory1)
			.name("간짜장")
			.price(9000)
			.description("흑인이 먹는 라면은?")
			.image("image.jpg")
			.build();

		Dish dish3 = Dish.builder()
			.id(3)
			.dishCategory(dishCategory2)
			.name("치즈볼")
			.price(8000)
			.description("치즈볼")
			.image("image.jpg")
			.build();

		Dish dish4 = Dish.builder()
			.id(4)
			.dishCategory(dishCategory3)
			.name("치즈함박스테이크")
			.price(8000)
			.description("햄버그")
			.image("image.jpg")
			.build();

		Dish dish5 = Dish.builder()
			.id(5)
			.dishCategory(dishCategory2)
			.name("콜라")
			.price(8000)
			.description("흑인이 먹는 물은?")
			.image("image.jpg")
			.build();

		Dish dish6 = Dish.builder()
			.id(6)
			.dishCategory(dishCategory2)
			.name("사이다")
			.price(8000)
			.description("백인이 먹는 사이다는?")
			.image("image.jpg")
			.build();

		Tag tag1 = Tag.builder()
			.id(1)
			.name("인기")
			.build();
		Tag tag2 = Tag.builder()
			.id(2)
			.name("화재")
			.build();
		Tag tag3 = Tag.builder()
			.id(3)
			.name("맛있음")
			.build();
		Tag tag4 = Tag.builder()
			.id(4)
			.name("맛없음")
			.build();
		Tag tag5 = Tag.builder()
			.id(5)
			.name("영표픽")
			.build();
		Tag tag6 = Tag.builder()
			.id(6)
			.name("용수픽")
			.build();

		DishTag dishTag1 = DishTag.builder()
			.id(1)
			.dish(dish1)
			.tag(tag1)
			.build();
		DishTag dishTag2 = DishTag.builder()
			.id(2)
			.dish(dish1)
			.tag(tag2)
			.build();
		DishTag dishTag3 = DishTag.builder()
			.id(3)
			.dish(dish1)
			.tag(tag3)
			.build();
		DishTag dishTag4 = DishTag.builder()
			.id(4)
			.dish(dish2)
			.tag(tag4)
			.build();
		DishTag dishTag5 = DishTag.builder()
			.id(5)
			.dish(dish2)
			.tag(tag5)
			.build();
		DishTag dishTag6 = DishTag.builder()
			.id(6)
			.dish(dish2)
			.tag(tag6)
			.build();

		when(restaurantRepository.findById(anyInt()))
			.thenReturn(Optional.ofNullable(restaurant1));

		when(tableRepository.findById(anyString()))
			.thenReturn(Optional.ofNullable(table));

		List<Dish> dishes = List.of(dish1,dish2,dish3,dish4,dish5,dish6);

		when(dishRepository.findDishesByRestaurant(any(Restaurant.class)))
			.thenReturn(dishes);

		when(redisHashRepository.getCurrentUserCnt(anyString()))
			.thenReturn(5);

		List<DishTag> dishTags = List.of(dishTag1,dishTag2,dishTag3,dishTag4,dishTag5,dishTag6);

		when(dishTagRepository.findTagsByDish(any(Dish.class)))
			.thenReturn(dishTags);

		//when
		DishSummaryResultDto resultDto = dishService.getAllDishes(table.getId());

		//then
		assertThat(resultDto.tableId())
			.isEqualTo("11111");

		assertThat(resultDto.tableName())
			.isEqualTo("영표의식탁");

		assertThat(resultDto.peopleCount()).isEqualTo(5);

		assertThat(resultDto.dishCategories())
			.contains("면류","햄버그","사이드");
	}

	@Test
	void getDish() {

	}

	@Test
	void getSearchedDishes() {

	}
}