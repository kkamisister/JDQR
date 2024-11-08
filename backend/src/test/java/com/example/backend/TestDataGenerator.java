package com.example.backend;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.backend.dish.entity.Dish;
import com.example.backend.dish.entity.DishCategory;
import com.example.backend.dish.entity.DishTag;
import com.example.backend.dish.entity.Tag;
import com.example.backend.etc.entity.Restaurant;
import com.example.backend.order.entity.Order;
import com.example.backend.order.enums.OrderStatus;
import com.example.backend.order.enums.PaymentMethod;

@Component
public final class TestDataGenerator {

	private final List<String> tagNameList;
	private final List<String> restaurantNameList;
	private final List<String> restaurantAddressList;
	private final List<Double> restaurantLatitudeList;
	private final List<Double> restaurantLongitudeList;

	private final List<String> dishCategoryNameList;

	private final List<String> dishNameList;
	private final List<Integer> dishPriceList;

	private final List<String> tableIdList;
	private final List<Integer> menuCntList;

	public TestDataGenerator(){

		tagNameList = List.of("인기","화재","맛있음","맛없음","영표픽","용수픽");
		restaurantNameList = List.of("용수의식당","영표집","용수집","영표의식당");
		restaurantAddressList = List.of("멀티캠퍼스1층","멀티캠퍼스2층","멀티캠퍼스3층","멀티캠퍼스4층");
		restaurantLatitudeList = List.of(5.0,10.0,30.0,50.0);
		restaurantLongitudeList = List.of(100.0,110.0,130.0,150.0);

		dishCategoryNameList = List.of("햄버거","치킨","사이드","피자");

		dishNameList = List.of("더블QPC","허니콤보","콜라","콤비네이션","베토디","고추바사삭","사이다","페퍼로니");
		dishPriceList = List.of(8000,9000,18000,23000,2500,2500,23000,19000);

		tableIdList = List.of("11111","22222");
		menuCntList = List.of(5,7,9,11);
	}

	// 4개
	public List<Order> generateTestOrderList(boolean isIdNeed){

		int numOfElement = 4;

		List<Order> orderList = new ArrayList<>();
		for(int i=0;i<numOfElement;i++){
			orderList.add(generateOrder(tableIdList.get(i%2),menuCntList.get(i),isIdNeed ? i+1 : null));
		}
		return orderList;
	}

	private Order generateOrder(String tableId,int cnt,Integer id){
		return Order.builder()
			.tableId(tableId)
			.menuCnt(cnt)
			.orderStatus(OrderStatus.PENDING)
			.paymentMethod(PaymentMethod.UNDEFINED)
			.build();
	}


	// 6개
	public List<DishTag> generateTestDishTagList(boolean isIdNeed){

		int numOfElement = 6;

		List<DishTag> dishTagList = new ArrayList<>();
		for(int i=0;i<numOfElement;i++){
			dishTagList.add(generateDishTag(isIdNeed ? i + 1 : null));
		}

		return dishTagList;
	}

	private DishTag generateDishTag(Integer id){
		return DishTag.builder()
			.id(id)
			.build();
	}

	// 8개
	public List<Dish> generateTestDishList(boolean isIdNeed){

		int numOfElement = 8;

		List<Dish> dishList = new ArrayList<>();
		for(int i=0;i<numOfElement;i++){
			dishList.add(generateDish(dishNameList.get(i),dishPriceList.get(i),isIdNeed ? i + 1 : null));
		}

		return dishList;
	}

	private Dish generateDish(String dishName,int price,Integer id){
		return Dish.builder()
			.id(id)
			.name(dishName)
			.price(price)
			.build();
	}


	// 4개
	public List<DishCategory> generateTestDishCategoryList(boolean isIdNeed){

		int numOfElement = 4;

		List<DishCategory> dishCategoryList = new ArrayList<>();
		for(int i=0;i<numOfElement;i++){
			dishCategoryList.add(generateDishCategory(dishCategoryNameList.get(i),isIdNeed ? i + 1 : null));
		}
		return dishCategoryList;
	}

	private DishCategory generateDishCategory(String name,Integer id){

		return DishCategory.builder()
			.id(id)
			.name(name)
			.build();

	}

	// 6개
	public List<Tag> generateTestTagList(boolean isIdNeed){
		int numOfElement = 6;

		List<Tag> tagList = new ArrayList<>();
		for(int i=0;i<numOfElement;i++){
			tagList.add(generateTag(tagNameList.get(i),isIdNeed ? i + 1 : null));
		}

		return tagList;
	}

	// 4개
	public List<Restaurant> generateTestRestaurantList(boolean isIdNeed){
		int numOfElement = 4;

		List<Restaurant> restaurantList = new ArrayList<>();
		for(int i=0;i<numOfElement;i++){
			restaurantList.add(generateRestaurant(restaurantNameList.get(i),restaurantAddressList.get(i),restaurantLatitudeList.get(i),restaurantLongitudeList.get(i),isIdNeed ? i + 1 : null));
		}

		return restaurantList;
	}

	private Restaurant generateRestaurant(String name,String address,Double latitude,Double longitude,Integer id){
		return Restaurant.builder()
			.id(id)
			.name(name)
			.address(address)
			.latitude(latitude)
			.longitude(longitude)
			.build();

	}

	private Tag generateTag(String name,Integer id){
		return Tag.builder()
			.id(id)
			.name(name)
			.build();
	}

}
