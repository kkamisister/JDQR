package com.example.backend.owner.service;

import static com.example.backend.dish.dto.DishResponse.*;
import static com.example.backend.owner.dto.OwnerResponse.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.common.dto.CommonResponse;
import com.example.backend.common.exception.ErrorCode;
import com.example.backend.common.exception.JDQRException;
import com.example.backend.dish.dto.DishRequest;
import com.example.backend.dish.entity.Dish;
import com.example.backend.dish.entity.DishCategory;
import com.example.backend.dish.entity.DishOption;
import com.example.backend.dish.entity.DishTag;
import com.example.backend.dish.entity.Option;
import com.example.backend.dish.entity.Tag;
import com.example.backend.dish.repository.DishCategoryRepository;
import com.example.backend.dish.repository.DishOptionRepository;
import com.example.backend.dish.repository.DishRepository;
import com.example.backend.dish.repository.DishTagRepository;
import com.example.backend.dish.repository.OptionRepository;
import com.example.backend.dish.repository.TagRepository;
import com.example.backend.etc.entity.Restaurant;
import com.example.backend.etc.repository.RestaurantRepository;
import com.example.backend.owner.dto.CategoryDto;
import com.example.backend.owner.dto.OwnerResponse;
import com.example.backend.owner.entity.Owner;
import com.example.backend.owner.repository.OwnerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OwnerServiceImpl implements OwnerService{

	private final DishRepository dishRepository;
	private final DishCategoryRepository dishCategoryRepository;
	private final OwnerRepository ownerRepository;
	private final DishTagRepository dishTagRepository;
	private final TagRepository tagRepository;
	private final OptionRepository optionRepository;
	private final DishOptionRepository dishOptionRepository;
	private final RestaurantRepository restaurantRepository;

	@Override
	@Transactional
	public CommonResponse.ResponseWithMessage addDish(Integer userId, DishRequest.DishInfo dishInfo) {

		//해당하는 가게 주인이 존재하는지 찾는다.
		Owner owner = ownerRepository.findById(userId)
			.orElseThrow(() -> new JDQRException(ErrorCode.USER_NOT_FOUND));

		//이제 dish를 만들어야 한다..
		//우선 dishCategory를 가지고 와야한다
		DishCategory dishCategory = dishCategoryRepository.findById(dishInfo.dishCategoryId())
			.orElseThrow(() -> new JDQRException(ErrorCode.FUCKED_UP_QR));

		// s3에서 저장 한 후 반환된 imageUrl
		String imageUrl = "";

		Dish dish = Dish.of(dishInfo,dishCategory,imageUrl);

		Dish savedDish = dishRepository.save(dish);

		// dishTag 엔티티 생성
		for(int tagId : dishInfo.tagIds()){
			Tag tag = tagRepository.findById(tagId)
				.orElseThrow(() -> new JDQRException(ErrorCode.TAG_NOT_FOUND));
			DishTag dishTag = DishTag.of(tag, savedDish);
			dishTagRepository.save(dishTag);

		}

		// 메뉴에 대한 옵션그룹 엔티티를 생성
		for(int optionGroupId : dishInfo.optionIds()){
			Option option = optionRepository.findById(optionGroupId)
				.orElseThrow(() -> new JDQRException(ErrorCode.OPTIONGROUP_NOT_FOUND));
			DishOption dishOption = DishOption.of(savedDish, option);
			dishOptionRepository.save(dishOption);
		}


		return new CommonResponse.ResponseWithMessage(HttpStatus.OK.value(), "메뉴목록에 추가했습니다");
	}


	@Override
	@Transactional
	public CommonResponse.ResponseWithMessage removeDish(Integer userId, Integer dishId) {
		//해당하는 가게 주인이 존재하는지 찾는다.
		Owner owner = ownerRepository.findById(userId)
			.orElseThrow(() -> new JDQRException(ErrorCode.USER_NOT_FOUND));

		//가게 주인이 없애려는 메뉴가 db에 존재하는지 찾는다
		Dish dish = dishRepository.findById(dishId)
			.orElseThrow(() -> new JDQRException(ErrorCode.DISH_NOT_FOUND));

		//매뉴옵션그룹(dish_options) 테이블에서 dish_id컬럼이 dishId인 행들을 삭제
		List<DishOption> dishOptions = dishOptionRepository.findByDishId(dishId);
		for(DishOption dishOption : dishOptions){
			dishOptionRepository.delete(dishOption);
		}
		//메뉴태그(dish_tag) 테이블에서 dis_id컬럼이 dishId인 행들을 삭제
		List<DishTag> dishTags = dishTagRepository.findByDishId(dishId);
		dishTagRepository.deleteAll(dishTags);
		//메뉴(dish) 테이블에서 id컬럼이 dishId인 행 삭제
		dishRepository.delete(dish);

		return new CommonResponse.ResponseWithMessage(HttpStatus.OK.value(), "메뉴에서 삭제되었습니다.");
	}

	@Override
	@Transactional
	public CommonResponse.ResponseWithMessage updateDish(Integer userId, Integer dishId, DishRequest.DishInfo dishInfo) {

		//해당하는 가게 주인이 존재하는지 찾는다.
		Owner owner = ownerRepository.findById(userId)
			.orElseThrow(() -> new JDQRException(ErrorCode.USER_NOT_FOUND));

		//가게 주인이 수정하려는 메뉴가 db에 존재하는지 찾는다
		Dish dish = dishRepository.findById(dishId)
			.orElseThrow(() -> new JDQRException(ErrorCode.DISH_NOT_FOUND));

		//메뉴의 정보(이름, 가격, 설명)를 수정한다.
		dish.setName(dishInfo.dishName());
		dish.setPrice(dishInfo.price());
		dish.setDescription(dishInfo.description());

		//메뉴의 정보(이미지)를 수정한다.
		String imageUrl = "";
		dish.setImage(imageUrl);

		//메뉴의 정보(카테고리)를 수정한다.
		Integer dishCategoryId = dishInfo.dishCategoryId();
		if(dishCategoryId != null && dishCategoryId > 0){
			DishCategory dishCategory = dishCategoryRepository.findById(dishInfo.dishCategoryId())
				.orElseThrow(() -> new JDQRException(ErrorCode.FUCKED_UP_QR));
			dish.setDishCategory(dishCategory);
		}

		//메뉴의 정보(태그)를 수정한다.
		//메뉴id가 dishId인 기존의 dishTag 삭제
		List<DishTag> existingTags = dishTagRepository.findByDishId(dishId);
		for(DishTag dishTag : existingTags){
			dishTagRepository.delete(dishTag);
		}
		//새로운 dishTag 추가
		for(int tagId : dishInfo.tagIds()){
			Tag tag = tagRepository.findById(tagId)
				.orElseThrow(() -> new JDQRException(ErrorCode.TAG_NOT_FOUND));
			DishTag newDishTag = DishTag.of(tag, dish);
			dishTagRepository.save(newDishTag);
		}

		//메뉴의 정보(옵션그룹)를 수정한다.
		//메뉴id가 dishId인 기존의 optionGroup 삭제
		List<DishOption> existingOptionGroups = dishOptionRepository.findByDishId(dishId);
		for(DishOption dishOption : existingOptionGroups){
			dishOptionRepository.delete(dishOption);
		}
		//새로운 optionGroup 추가
		for(int optionGroupId : dishInfo.optionIds()){
			Option option = optionRepository.findById(optionGroupId)
				.orElseThrow(() -> new JDQRException(ErrorCode.OPTIONGROUP_NOT_FOUND));
			DishOption newDishOption = DishOption.of(dish, option);
			dishOptionRepository.save(newDishOption);
		}

		dishRepository.save(dish);

		return new CommonResponse.ResponseWithMessage(HttpStatus.OK.value(), "메뉴가 수정되었습니다.");
	}

	/**
	 * 업장의 전체 메뉴를 조회하는 메서드
	 * 점주 전용 메서드이며, 메뉴판 조회와 조금 다르다
	 * @param userId
	 */
	@Override
	public DishSummaryResultDto getAllMenus(Integer userId) {
		//1. 점주를 조회한다
		Owner owner = ownerRepository.findById(userId)
			.orElseThrow(() -> new JDQRException(ErrorCode.USER_NOT_FOUND));

		//2. 점주의 식당을 찾는다
		Restaurant restaurant = restaurantRepository.findByOwner(owner)
			.orElseThrow(() -> new JDQRException(ErrorCode.USER_NOT_FOUND));

		//3. 식당의 메뉴를 조회한다
		List<Dish> dishes = dishRepository.findDishesByRestaurant(restaurant);

		Map<Integer,String> idToNameMap = new HashMap<>();
		for(Dish dish : dishes) {
			DishCategory dishCategory = dish.getDishCategory();
			idToNameMap.put(dishCategory.getId(), dishCategory.getName());
		}

		// 카테고리별로 메뉴정보를 담은 맵을 생성한다
		Map<Integer,List<DishSimpleInfo>> idToSimpleInfoMap = new LinkedHashMap<>();
		for(Dish dish : dishes){

			DishCategory dishCategory = dish.getDishCategory();
			// dishCategory정보 추출
			int dishCategoryId = dishCategory.getId();

			DishSimpleInfo dishSimpleInfo = createDishSimpleInfo(dish);

			// 카테고리 별 SimpleInfo정보를 추가한다
			if(!idToSimpleInfoMap.containsKey(dishCategoryId)){
				idToSimpleInfoMap.put(dishCategoryId, new ArrayList<>());
			}
			idToSimpleInfoMap.get(dishCategoryId).add(dishSimpleInfo);
		}

		// 정보를 생성한다
		List<DishSummaryInfo> dishSummaryInfoList = new ArrayList<>();
		for(Map.Entry<Integer,List<DishSimpleInfo>> entry : idToSimpleInfoMap.entrySet()){

			DishSummaryInfo dishSummaryInfo = createDishSummaryInfo(entry, idToNameMap);
			dishSummaryInfoList.add(dishSummaryInfo);
		}
		List<String> dishCategories = idToNameMap.values().stream().map(String::toString).toList();
		// 반환 DTO
		DishSummaryResultDto dishSummaryResultDto = DishSummaryResultDto.builder()
			.dishCategories(dishCategories)
			.dishes(dishSummaryInfoList)
			.build();

		return dishSummaryResultDto;
	}

	/**
	 * 상세 메뉴를 조회하는 메서드.
	 * 점주전용 메서드이다
	 *
	 * @param userId
	 */
	@Override
	public DishSummaryResultDto getMenu(Integer userId,Integer dishId) {

		//1. 점주를 조회한다
		Owner owner = ownerRepository.findById(userId)
			.orElseThrow(() -> new JDQRException(ErrorCode.USER_NOT_FOUND));

		//2. 메뉴를 조회한다
		Dish dish = dishRepository.findDishWithCategoryById(dishId)
			.orElseThrow(() -> new JDQRException(ErrorCode.FUCKED_UP_QR));

		Map<Integer,String> idToNameMap = new HashMap<>();

		DishCategory dishCategory = dish.getDishCategory();
		idToNameMap.put(dishCategory.getId(), dishCategory.getName());

		// 카테고리별로 메뉴정보를 담은 맵을 생성한다
		Map<Integer,List<DishSimpleInfo>> idToSimpleInfoMap = new LinkedHashMap<>();
		// dishCategory정보 추출
		int dishCategoryId = dishCategory.getId();

		DishSimpleInfo dishSimpleInfo = createDishSimpleInfo(dish);

		// 카테고리 별 SimpleInfo정보를 추가한다
		if(!idToSimpleInfoMap.containsKey(dishCategoryId)){
			idToSimpleInfoMap.put(dishCategoryId, new ArrayList<>());
		}
		idToSimpleInfoMap.get(dishCategoryId).add(dishSimpleInfo);

		// 정보를 생성한다
		List<DishSummaryInfo> dishSummaryInfoList = new ArrayList<>();
		for(Map.Entry<Integer,List<DishSimpleInfo>> entry : idToSimpleInfoMap.entrySet()){

			DishSummaryInfo dishSummaryInfo = createDishSummaryInfo(entry, idToNameMap);
			dishSummaryInfoList.add(dishSummaryInfo);
		}

		List<String> dishCategories = idToNameMap.values().stream().map(String::toString).toList();
		// 반환 DTO
		DishSummaryResultDto dishSummaryResultDto = DishSummaryResultDto.builder()
			.dishCategories(dishCategories)
			.dishes(dishSummaryInfoList)
			.build();

		return dishSummaryResultDto;

	}

	/**
	 * 카테고리를 추가하는 메서드
	 * @param categoryDto
	 * @param userId
	 */
	@Override
	public void createCategory(CategoryDto categoryDto, Integer userId) {

		log.warn("categoryDto : {}",categoryDto);

		//1. 점주를 조회한다
		Owner owner = ownerRepository.findById(userId)
			.orElseThrow(() -> new JDQRException(ErrorCode.USER_NOT_FOUND));

		//2. 점주의 식당을 조회한다
		Restaurant restaurant = restaurantRepository.findByOwner(owner)
			.orElseThrow(() -> new JDQRException(ErrorCode.RESTAURANT_NOT_FOUND));

		//3. 카테고리를 추가한다
		DishCategory dishCategory = DishCategory.builder()
			.name(categoryDto.getDishCategoryName())
			.restaurant(restaurant)
			.build();


		//4. 카테고리를 저장한다
		dishCategoryRepository.save(dishCategory);
	}

	/**
	 * 메뉴 카테고리를 삭제하는 메서드
	 * @param dishCategoryId
	 * @param userId
	 */
	@Override
	public void removeCategory(Integer dishCategoryId, Integer userId) {
		//1. 점주를 조회한다
		Owner owner = ownerRepository.findById(userId)
			.orElseThrow(() -> new JDQRException(ErrorCode.USER_NOT_FOUND));

		//2. 식당을 조회한다
		Restaurant restaurant = restaurantRepository.findByOwner(owner)
			.orElseThrow(() -> new JDQRException(ErrorCode.RESTAURANT_NOT_FOUND));

		//2. 식당카테고리를 조회한다
		List<DishCategory> dishCategories = dishCategoryRepository.findByRestaurant(restaurant);

		for(DishCategory dishCategory : dishCategories){

			if(Objects.equals(dishCategory.getId(), dishCategoryId)){
				dishCategoryRepository.delete(dishCategory);
				return;
			}
		}
	}

	/**
	 * 모든 메뉴 카테고리를 조회하는 메서드
	 * @param userId
	 */
	@Override
	public CategoryResult getAllCategories(Integer userId) {

		//1. 점주를 조회한다
		Owner owner = ownerRepository.findById(userId)
			.orElseThrow(() -> new JDQRException(ErrorCode.USER_NOT_FOUND));

		//2. 식당을 조회한다
		Restaurant restaurant = restaurantRepository.findByOwner(owner)
				.orElseThrow(() -> new JDQRException(ErrorCode.RESTAURANT_NOT_FOUND));

		//3. 응답을 생성한다
		List<DishCategory> dishCategories = dishCategoryRepository.findByRestaurant(restaurant);

		List<CategoryDto> categoryDtos = new ArrayList<>();
		for(int idx=0;idx<dishCategories.size();idx++){

			DishCategory dishCategory = dishCategories.get(idx);

			int categoryId = dishCategory.getId();
			String categoryName = dishCategory.getName();

			CategoryDto categoryDto = new CategoryDto(categoryId, categoryName);
			categoryDtos.add(categoryDto);
		}

		CategoryResult categoryResult = new CategoryResult(categoryDtos);

		return categoryResult;
	}



	/**
	 * 메뉴 요약정보를 생성하는 메서드
	 * @param entry
	 * @param idToNameMap
	 */
	private static DishSummaryInfo createDishSummaryInfo(Map.Entry<Integer, List<DishSimpleInfo>> entry, Map<Integer, String> idToNameMap) {
		int dishCategoryId = entry.getKey();
		String dishCategoryName = idToNameMap.get(dishCategoryId);
		List<DishSimpleInfo> dishSimpleInfoList = entry.getValue();

		DishSummaryInfo dishSummaryInfo = DishSummaryInfo.builder()
			.dishCategoryId(dishCategoryId)
			.dishCategoryName(dishCategoryName)
			.items(dishSimpleInfoList)
			.build();

		return dishSummaryInfo;
	}

	/**
	 * 메뉴에 대한 간단정보를 담는 DTO
	 * @param dish
	 */
	private DishSimpleInfo createDishSimpleInfo(Dish dish) {
		// itmes 항목 채우기
		// 우선, 메뉴의 태그를 가져와야한다
		List<DishTag> dishTags = dishTagRepository.findTagsByDish(dish);
		List<String> tags = dishTags.stream().map(DishTag::getTag)
			.map(Tag::getName).toList();

		DishSimpleInfo dishSimpleInfo = DishSimpleInfo.of(dish,tags);
		return dishSimpleInfo;
	}

}
