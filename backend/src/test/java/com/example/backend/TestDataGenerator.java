package com.example.backend;

import static com.example.backend.common.enums.CategoryType.*;
import static com.example.backend.order.enums.OrderStatus.*;
import static com.example.backend.order.enums.PaymentMethod.*;
import static java.lang.Boolean.*;

import java.util.ArrayList;
import java.util.List;

import com.example.backend.order.entity.Order;
import com.example.backend.order.entity.ParentOrder;
import org.springframework.stereotype.Component;

import com.example.backend.common.enums.CategoryType;
import com.example.backend.dish.entity.Choice;
import com.example.backend.dish.entity.Dish;
import com.example.backend.dish.entity.DishCategory;
import com.example.backend.dish.entity.DishOption;
import com.example.backend.dish.entity.Option;
import com.example.backend.etc.entity.Restaurant;
import com.example.backend.etc.entity.RestaurantCategory;
import com.example.backend.etc.entity.RestaurantCategoryMap;
import com.example.backend.order.entity.OrderItem;
import com.example.backend.order.enums.OrderStatus;
import com.example.backend.order.enums.PaymentMethod;

@Component
public final class TestDataGenerator {

	private final List<String> userIdList;

	private final List<String> tagNameList;
	private final String tagName;
	private final List<String> restaurantNameList;
	private final List<String> restaurantAddressList;
	private final List<Double> restaurantLatitudeList;
	private final List<Double> restaurantLongitudeList;

	private final List<String> dishCategoryNameList;

	private final List<String> dishNameList;
	private final List<Integer> dishPriceList;

	private final List<Integer> menuCntList;

	private final List<String> categoryNameList;
	private final List<CategoryType> categoryTypeList;

	private final List<OrderStatus> orderStatusList;
	private final List<PaymentMethod> paymentMethodList;

	private final List<Integer> quantityList;
	private final List<Integer> paidQuantityList;
	private final List<Integer> orderPriceList;

	private final List<String> optionNameList;
	private final List<Boolean> mandatoryList;
	private final List<Integer> maxChoiceList;

	private final List<String> choiceList;
	private final List<Integer> choicePriceList;

	private final List<Integer> peopleNumList;
	private final List<String> tableIdList;


	public TestDataGenerator(){

		tagNameList = List.of("인기","화재","맛있음","맛없음","영표픽","용수픽");
		tagName = "[\"인기\",\"화재\",\"맛있음\",\"맛없음\",\"영표픽\",\"용수픽\"]";
		restaurantNameList = List.of("용수의식당","영표집","용수집","영표의식당");
		restaurantAddressList = List.of("멀티캠퍼스1층","멀티캠퍼스2층","멀티캠퍼스3층","멀티캠퍼스4층");
		restaurantLatitudeList = List.of(5.0,10.0,30.0,50.0);
		restaurantLongitudeList = List.of(100.0,110.0,130.0,150.0);

		dishCategoryNameList = List.of("햄버거","치킨","사이드","피자");

		dishNameList = List.of("더블QPC","허니콤보","콜라","콤비네이션","베토디","고추바사삭","사이다","페퍼로니");
		dishPriceList = List.of(8000,9000,18000,23000,2500,2500,23000,19000);

		menuCntList = List.of(5,7,9,11);

		categoryNameList = List.of("한식","일식","양식","중식");
		categoryTypeList = List.of(MAJOR,MAJOR,MAJOR,MAJOR);

		orderStatusList = List.of(PAID,PAY_WAITING,PENDING,PAY_WAITING,PAID,PENDING);
		paymentMethodList = List.of(UNDEFINED,MONEY_DIVIDE,UNDEFINED,MENU_DIVIDE,MENU_DIVIDE,MONEY_DIVIDE);
		peopleNumList = List.of(4,4,4,4,6,6);
		tableIdList = List.of("table1","table2","table3","table4","table5","table6");


		userIdList = List.of("aaaaa","bbbbb","ccccc","ddddd");
		quantityList = List.of(10,8,6,4,2,1);
		paidQuantityList = List.of(5,4,3,2,1,0);
		orderPriceList = List.of(10000,8000,6000,4000,2000,1000);

		optionNameList = List.of("부위","맛","샷추가","휘핑크림","소스","양념");
		mandatoryList = List.of(TRUE,TRUE,FALSE,FALSE,TRUE,FALSE);
		maxChoiceList = List.of(2,2,1,1,3,3);

		choiceList = List.of(
			"간","내장","순대",
			"맵게","짜게","달게",
			"1번","2번","3번",
			"1번","2번","3번",
			"핫소스","스테이크","타바스코",
			"치즈","양파","기본"
		);
		choicePriceList = List.of(
			1000,1000,1000,
			100,200,300,
			200,300,400,
			200,300,400,
			500,600,700,
			300,300,300
		);
	}

	//6개
	public List<Order> generateTestOrderList(boolean  isIdNeed){

		int numOfElement = 6;
		List<Order> orderList = new ArrayList<>();
		for(int i=0;i<numOfElement;i++){
			orderList.add(Order.builder().build());
		}
		return orderList;
	}

	// 6개
	public List<ParentOrder> generateTestParentOrderList(boolean isIdNeed){

		int numOfElement = 6;
		List<ParentOrder> parentOrderList = new ArrayList<>();
		for(int i=0;i<numOfElement;i++){
			parentOrderList.add(generateParentOrder(
				tableIdList.get(i),orderStatusList.get(i),paymentMethodList.get(i), peopleNumList.get(i),
				isIdNeed ? i+1 : null)
			);
		}
		return parentOrderList;
	}

	private ParentOrder generateParentOrder(
		String tableId,OrderStatus orderStatus,PaymentMethod paymentMethod,
		int peopleNum,Integer id){

		return ParentOrder.builder()
			.id(id)
			.tableId(tableId)
			.orderStatus(orderStatus)
			.paymentMethod(paymentMethod)
			.peopleNum(peopleNum)
			.build();

	}

	// 6개
	public List<DishOption> generateTestDishOptionList(boolean isIdNeed){

		int numOfElement = 6;
		List<DishOption> dishOptionList = new ArrayList<>();
		for(int i=0; i<numOfElement; i++){
			dishOptionList.add(generateDishOption(isIdNeed ? i+1 : null));
		}
		return dishOptionList;

	}
	private DishOption generateDishOption(Integer id){
		return DishOption.builder()
			.id(id)
			.build();
	}

	// 6개
	public List<Choice> generateTestChoiceList(boolean isIdNeed){

		int numOfElement = 6;

		List<Choice> choiceList1 = new ArrayList<>();
		for(int i = 0; i < numOfElement; i++){
			choiceList1.add(generateChoice(choiceList.get(i),choicePriceList.get(i),isIdNeed ? i+1 : null));
		}
		return choiceList1;
	}

	private Choice generateChoice(String name,Integer price,Integer id){

		return Choice.builder()
			.id(id)
			.name(name)
			.price(price)
			.build();

	}

	// 6개
	public List<Option> generateTestOptionList(boolean isIdNeed){

		int numOfElement = 6;

		List<Option> optionList = new ArrayList<>();
		for(int i=0;i<numOfElement;i++){
			optionList.add(generateOption(optionNameList.get(i),maxChoiceList.get(i),mandatoryList.get(i),isIdNeed ? i+1 : null));
		}
		return optionList;
	}

	private Option generateOption(String optionName,int maxChoice,Boolean mandatory,Integer id){
		return Option.builder()
			.id(id)
			.name(optionName)
			.mandatory(mandatory)
			.maxChoiceCount(maxChoice)
			.build();
	}

	// 12개
	public List<OrderItem> generateTestOrderItemList(boolean isIdNeed){

		int numOfElement = 12;

		List<OrderItem> orderItemList = new ArrayList<>();
		for(int i = 0; i < numOfElement; i++){
			orderItemList.add(generateOrderItem(userIdList.get(i%4),quantityList.get(i%6),paidQuantityList.get(i%6),orderPriceList.get(i%6),orderStatusList.get(i%6),isIdNeed ? i+1 : null));

		}
		return orderItemList;
	}

	private OrderItem generateOrderItem(String userId,Integer quantity,Integer paidQuantity,Integer orderPrice,OrderStatus orderStatus,Integer id){
		return OrderItem.builder()
			.userId(userId)
			.quantity(quantity)
			.paidQuantity(paidQuantity)
			.orderPrice(orderPrice)
			.build();

	}

	// 4개
	public List<RestaurantCategoryMap> generateTestRestaurantCategoryMap(boolean isIdNeed){

		int numOfElement = 4;

		List<RestaurantCategoryMap> restaurantCategoryMapList = new ArrayList<>();
		for(int i=0; i<numOfElement; i++){
			restaurantCategoryMapList.add(generateRestaurantCategoryMap(isIdNeed ? i+1 : null));
		}

		return restaurantCategoryMapList;
	}

	private RestaurantCategoryMap generateRestaurantCategoryMap(Integer id){
		return RestaurantCategoryMap.builder()
			.id(id)
			.build();
	}

	// 4개
	public List<RestaurantCategory> generateTestRestaurantCategoryList(boolean isIdNeed){

		int numOfElement = 4;

		List<RestaurantCategory> restaurantCategoryList = new ArrayList<>();
		for(int i=0;i<numOfElement;i++){
			restaurantCategoryList.add(generateRestaurantCategory(categoryNameList.get(i),categoryTypeList.get(i),isIdNeed ? i+1 : null));
		}

		return restaurantCategoryList;
	}

	private RestaurantCategory generateRestaurantCategory(String categoryName,CategoryType categoryType,Integer id){
		return RestaurantCategory.builder()
			.id(id)
			.name(categoryName)
			.categoryType(categoryType)
			.build();
	}

	// // 4개
	// public List<ParentOrder> generateTestOrderList(boolean isIdNeed){
	//
	// 	int numOfElement = 4;
	//
	// 	List<ParentOrder> parentOrderList = new ArrayList<>();
	// 	for(int i=0;i<numOfElement;i++){
	// 		parentOrderList.add(generateOrder(tableIdList.get(i%2),menuCntList.get(i),orderStatusList.get(i),paymentMethodList.get(i),isIdNeed ? i+1 : null));
	// 	}
	// 	return parentOrderList;
	// }
	//
	// private ParentOrder generateOrder(String tableId, int cnt, OrderStatus orderStatus, PaymentMethod paymentMethod, Integer id){
	// 	return ParentOrder.builder()
	// 		.tableId(tableId)
	// 		.orderStatus(orderStatus)
	// 		.paymentMethod(paymentMethod)
	// 		.build();
	// }


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
			.tags(tagName)
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

	// // 6개
	// public List<Tag> generateTestTagList(boolean isIdNeed){
	// 	int numOfElement = 6;
	//
	// 	List<Tag> tagList = new ArrayList<>();
	// 	for(int i=0;i<numOfElement;i++){
	// 		tagList.add(generateTag(tagNameList.get(i),isIdNeed ? i + 1 : null));
	// 	}
	//
	// 	return tagList;
	// }

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

	// private Tag generateTag(String name,Integer id){
	// 	return Tag.builder()
	// 		.id(id)
	// 		.name(name)
	// 		.build();
	// }

}
