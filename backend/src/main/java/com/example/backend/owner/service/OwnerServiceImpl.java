package com.example.backend.owner.service;

import static com.example.backend.common.enums.EntityStatus.*;
import static com.example.backend.dish.dto.DishResponse.*;
import static com.example.backend.owner.dto.OwnerResponse.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Objects;

import com.example.backend.common.enums.EntityStatus;
import com.example.backend.common.service.ImageS3Service;
import com.example.backend.common.util.TagParser;
import com.example.backend.dish.dto.ChoiceDto;
import com.example.backend.dish.dto.OptionDto;
import com.example.backend.dish.entity.Choice;
import com.example.backend.dish.repository.ChoiceRepository;
import com.example.backend.etc.entity.Restaurant;
import com.example.backend.etc.repository.RestaurantRepository;
import com.example.backend.owner.dto.OptionVo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.common.dto.CommonResponse;
import com.example.backend.common.exception.ErrorCode;
import com.example.backend.common.exception.JDQRException;
import com.example.backend.dish.dto.DishRequest;
import com.example.backend.dish.entity.Dish;
import com.example.backend.dish.entity.DishCategory;
import com.example.backend.dish.entity.DishOption;
import com.example.backend.dish.entity.Option;
import com.example.backend.dish.repository.DishCategoryRepository;
import com.example.backend.dish.repository.DishOptionRepository;
import com.example.backend.dish.repository.DishRepository;
import com.example.backend.dish.repository.OptionRepository;
import com.example.backend.owner.dto.CategoryDto;
import com.example.backend.owner.dto.OwnerRequest.OptionRequestDto;
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
	private final OptionRepository optionRepository;
	private final DishOptionRepository dishOptionRepository;
	private final RestaurantRepository restaurantRepository;
	private final ChoiceRepository choiceRepository;
	private final ImageS3Service imageS3Service;

	@Override
	@Transactional
	public CommonResponse.ResponseWithMessage addDish(Integer userId, DishRequest.DishInfo dishInfo, MultipartFile multipartfile) {

		//해당하는 가게 주인이 존재하는지 찾는다.
		Owner owner = ownerRepository.findById(userId)
			.orElseThrow(() -> new JDQRException(ErrorCode.USER_NOT_FOUND));

		//이제 dish를 만들어야 한다..
		//우선 dishCategory를 가지고 와야한다
		DishCategory dishCategory = dishCategoryRepository.findById(dishInfo.dishCategoryId())
			.orElseThrow(() -> new JDQRException(ErrorCode.FUCKED_UP_QR));

		// s3에서 저장 한 후 반환된 imageUrl
		String imageUrl = imageS3Service.uploadImageToS3(multipartfile);

		Dish dish = Dish.of(dishInfo,dishCategory,imageUrl);

		Dish savedDish = dishRepository.save(dish);

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

		//메뉴(dish) 테이블에서 id컬럼이 dishId인 행 삭제
		dishRepository.delete(dish);

		return new CommonResponse.ResponseWithMessage(HttpStatus.OK.value(), "메뉴에서 삭제되었습니다.");
	}

	@Override
	@Transactional
	public CommonResponse.ResponseWithMessage updateDish(Integer userId, Integer dishId, DishRequest.DishInfo dishInfo, MultipartFile multipartFile) {

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
		dish.setTags(dishInfo.tags());

		//메뉴의 정보(이미지)를 수정한다.
		if(!ObjectUtils.isEmpty(multipartFile)){
			String imageUrl = imageS3Service.uploadImageToS3(multipartFile);
			dish.setImage(imageUrl);
		}

		//메뉴의 정보(카테고리)를 수정한다.
		Integer dishCategoryId = dishInfo.dishCategoryId();
		if(dishCategoryId != null && dishCategoryId > 0){
			DishCategory dishCategory = dishCategoryRepository.findById(dishInfo.dishCategoryId())
				.orElseThrow(() -> new JDQRException(ErrorCode.FUCKED_UP_QR));
			dish.setDishCategory(dishCategory);
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
	 * 점주가 설정한 옵션의 전체 정보를 담아 반환
	 * @param userId : 점주의 id
	 * @return : 전체 옵션 정보를 담아 반환
	 */
	@Override
	public WholeOptionResponseDto getWholeOptionInfo(Integer userId) {

		// 1. userId에 해당하는 restaurant를 찾는다.
		Restaurant restaurant = restaurantRepository.findByOwnerId(userId)
			.orElseThrow(() -> new JDQRException(ErrorCode.RESTAURANT_NOT_FOUND));

		// 2. option table과 choice table을 join하여 현재 restaurant에 해당하는 옵션 정보를 들고 온다.
		List<OptionVo> optionVos = optionRepository.findAllOptionByRestaurant(restaurant);

		// log.warn("optionVos : {}",optionVos);

		// 3. optionVos를 가지고 알맞은 api 반환 형식으로 가공한다.
		// 3-1. stream의 groupingBy를 이용해 optionId를 key로 분류한다.
		Map<Integer, List<OptionVo>> optionGroupByOptionId = optionVos.stream()
			.collect(Collectors.groupingBy(
				OptionVo::getOptionId,
				LinkedHashMap::new, // LinkedHashMap으로 결과를 저장
				Collectors.toList()
			));


		// 3-2. 각 option에 대해 세부 옵션 정보를 저장한다.
		List<OptionResponseDto> optionResponseDtos = optionGroupByOptionId.entrySet().stream()
			.map(optionEntry -> {
				Integer optionId = optionEntry.getKey();
				// log.warn("optionId : {}",optionId);
				List<OptionVo> values = optionEntry.getValue();

				return getOptionResponseDto(optionId, values);
			})
			.toList();

		// 옵션 리스트를 바탕으로 record build 후 반환
		return WholeOptionResponseDto.builder()
			.options(optionResponseDtos)
			.build();
	}

	/**
	 * optionId와, 그 optionId에 해당하는 List<OptionVo>를 받아서, api가 요구하는 형식으로 변환 후 반환
	 * @param optionId : 검색 예정인 option의 id
	 * @param values : OptionVo의 list들
	 * @return : OptionResponseDto 형식
	 */
	private static OptionResponseDto getOptionResponseDto(Integer optionId, List<OptionVo> values) {
		// 옵션 그룹에 대한 정보를 담고 있는 class
		OptionVo baseOptionVo = values.get(0);

		// 세부 옵션 정보 구하기
		List<ChoiceDto> choiceDtos = values.stream()
			.filter(optionVo -> optionVo.getChoiceId() != null)
			.map(optionVo -> ChoiceDto.builder()
				.choiceId(optionVo.getChoiceId())
				.choiceName(optionVo.getChoiceName())
				.price(optionVo.getPrice())
				.build()
			)
			.toList();

		return OptionResponseDto.builder()
			.optionId(optionId)
			.optionName(baseOptionVo.getOptionName())
			.choices(choiceDtos)
			.maxChoiceCount(baseOptionVo.getMaxChoiceCount())
			.isMandatory(baseOptionVo.getMandatory())
			.build();
	}

	/**
	 * @param optionId : 정보를 얻고자 하는 option의 id
	 */
	@Override
	public OptionResponseDto getIndividualOptionInfo(Integer userId, Integer optionId) {

		List<OptionVo> optionVos = optionRepository.findOptionByOptionId(optionId);

		return getOptionResponseDto(optionId, optionVos);
	}

	/**
	 * 옵션을 생성하는 메서드
	 * @param optionDto
	 * @param userId
	 */
	@Override
	public void createOption(Integer userId,OptionRequestDto optionDto) {

		//1. 점주를 찾는다
		Owner owner = ownerRepository.findById(userId)
			.orElseThrow(() -> new JDQRException(ErrorCode.USER_NOT_FOUND));

		//2. 식당을 찾는다
		Restaurant restaurant = restaurantRepository.findByOwner(owner)
			.orElseThrow(() -> new JDQRException(ErrorCode.RESTAURANT_NOT_FOUND));

		//3. 옵션을 추가한다
		Option option = Option.of(restaurant,optionDto);

		//4. 옵션을 저장한다
		Option savedOption = optionRepository.save(option);

		//5. 세부옵션(choice)을 설정한다
		List<Choice> choices = optionDto.choices().stream()
			.map(choiceDto -> Choice.of(choiceDto, savedOption))
			.toList();

		choiceRepository.saveAll(choices);
	}

	/**
	 * 옵션을 삭제하는 메서드
	 * @param userId
	 * @param optionId
	 */
	@Override
	public void deleteOption(Integer userId, Integer optionId) {

		//1. 점주를 조회한다
		Owner owner = ownerRepository.findById(userId)
			.orElseThrow(() -> new JDQRException(ErrorCode.USER_NOT_FOUND));

		//2. 옵션을 조회한다
		Option option = optionRepository.findById(optionId)
			.orElseThrow(() -> new JDQRException(ErrorCode.OPTIONGROUP_NOT_FOUND));

		//3. option 삭제시도
		// 우선 dishOption에 연관된 dish가 있다면 실패해야한다
		List<DishOption> dishOptions = dishOptionRepository.findByOption(option);

		if(!ObjectUtils.isEmpty(dishOptions)){
			throw new JDQRException(ErrorCode.OCCUPIED_OPTION);
		}
		else{
			// option을 삭제해도 괜찮은 경우
			List<Choice> choices = choiceRepository.findByOption(option);
			for(Choice choice : choices){
				choice.changeStatus(DELETE);
			}
			option.changeStatus(DELETE);
		}
	}

	/**
	 * 옵션을 수정하는 메서드
	 * @param userId
	 * @param optionId
	 * @param optionDto
	 */
	@Override
	public void updateOption(Integer userId, Integer optionId, OptionRequestDto optionDto) {

		//1. 점주를 찾는다
		Owner owner = ownerRepository.findById(userId)
			.orElseThrow(() -> new JDQRException(ErrorCode.USER_NOT_FOUND));

		//2. 옵션을 찾는다
		Option option = optionRepository.findById(optionId)
			.orElseThrow(() -> new JDQRException(ErrorCode.OPTIONGROUP_NOT_FOUND));

		// 기존 옵션이 가진 choice들을 가지고온다
		List<Choice> originalChoices = choiceRepository.findByOption(option);

		//3. 옵션의 이름을 수정한다
		option.changeOptionName(optionDto);

		// 우선 옵션에 딸린 choice를 먼저 수정해야한다
		List<ChoiceDto> choices = optionDto.choices();

		Map<Integer,Boolean> choiceIdMap = new HashMap<>();
		for(ChoiceDto choiceDto : choices){
			Integer choiceId = choiceDto.getChoiceId();
			if(ObjectUtils.isEmpty(choiceId)){
				// 새롭게 생성된 Choice -> 저장
				Choice choice = Choice.of(choiceDto, option);
				choiceRepository.save(choice);
			}
			else{
				// 기존에 존재하던 choice인 경우 -> 내용을 수정한다
				Choice choice = choiceRepository.findById(choiceId)
					.orElseThrow(() -> new JDQRException(ErrorCode.CHOICE_NOT_FOUND));

				choice.changeChoice(choiceDto);
				choiceIdMap.put(choiceId,true);
			}
		}

		// 기존 originalChoices를 돌며 필요없게 된 choice를 삭제한다
		for(Choice choice : originalChoices){
			Integer choiceId = choice.getId();
			if(!choiceIdMap.containsKey(choiceId)){
				choice.changeStatus(DELETE);
			}
		}
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

		//1. 점주를 조회한다
		Owner owner = ownerRepository.findById(userId)
			.orElseThrow(() -> new JDQRException(ErrorCode.USER_NOT_FOUND));

		//2. 점주의 식당을 조회한다
		Restaurant restaurant = restaurantRepository.findByOwner(owner)
			.orElseThrow(() -> new JDQRException(ErrorCode.RESTAURANT_NOT_FOUND));

		List<DishCategory> dishCategories = dishCategoryRepository.findByRestaurant(restaurant);

		List<String> categoryNameList = dishCategories.stream().map(DishCategory::getName).toList();

		for(String categoryName : categoryNameList){
			if(categoryName.equals(categoryDto.getDishCategoryName())){
				throw new JDQRException(ErrorCode.DUPLICATED_CATEGORY);
			}
		}

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
				// 카테고리 삭제 시, 카테고리에 속한 메뉴가 있는 지 확인한다
				// log.warn("dishes : {}",dishCategory.getDishes());
				if(ObjectUtils.isEmpty(dishCategory.getDishes())){
					dishCategoryRepository.delete(dishCategory);
					return;
				}
				else{
					throw new JDQRException(ErrorCode.OCCUPIED_CATEGORY);
				}
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
		List<String> tags = TagParser.parseTags(dish.getTags());

		// Dish의 옵션을 가지고온다
		List<DishOption> dishOptions = dishOptionRepository.findByDishId(dish.getId());

		List<OptionDto> options = new ArrayList<>();
		for(DishOption dishOption : dishOptions){
			Option option = dishOption.getOption();
			OptionDto optionDto = OptionDto.of(option);
			options.add(optionDto);
		}

		DishSimpleInfo dishSimpleInfo = DishSimpleInfo.of(dish,tags,options);
		return dishSimpleInfo;
	}

}
