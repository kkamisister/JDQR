package com.example.backend.etc.service;

import static com.example.backend.etc.dto.RestaurantResponse.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.common.enums.UseStatus;
import com.example.backend.common.exception.ErrorCode;
import com.example.backend.common.exception.JDQRException;
import com.example.backend.common.service.ImageS3Service;
import com.example.backend.common.util.TagParser;
import com.example.backend.dish.dto.DishResponse.DishDataDto;
import com.example.backend.dish.dto.DishResponse.DishDetailInfo;
import com.example.backend.dish.dto.DishResponse.DishDetailSummaryInfo;
import com.example.backend.dish.dto.OptionDto;
import com.example.backend.dish.entity.Choice;
import com.example.backend.dish.entity.Dish;
import com.example.backend.dish.entity.DishOption;
import com.example.backend.dish.entity.Option;
import com.example.backend.dish.repository.DishOptionRepository;
import com.example.backend.dish.repository.DishRepository;
import com.example.backend.etc.dto.RestaurantCategoryDetail;
import com.example.backend.etc.dto.RestaurantCategoryDto;
import com.example.backend.etc.dto.RestaurantDto;
import com.example.backend.etc.dto.RestaurantProfileDto;
import com.example.backend.etc.dto.RestaurantResponse;
import com.example.backend.etc.dto.RestaurantResponse.RestaurantInfo;
import com.example.backend.etc.entity.Restaurant;
import com.example.backend.etc.entity.RestaurantCategory;
import com.example.backend.etc.entity.RestaurantCategoryMap;
import com.example.backend.etc.repository.RestaurantCategoryMapRepository;
import com.example.backend.etc.repository.RestaurantCategoryRepository;
import com.example.backend.etc.repository.RestaurantRepository;
import com.example.backend.owner.entity.Owner;
import com.example.backend.owner.repository.OwnerRepository;
import com.example.backend.table.entity.Table;
import com.example.backend.table.repository.TableRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RestaurantServiceImpl implements RestaurantService {

	private final RestaurantRepository restaurantRepository;
	private final RestaurantCategoryRepository restaurantCategoryRepository;
	private final RestaurantCategoryMapRepository restaurantCategoryMapRepository;
	private final TableRepository tableRepository;
	private final OwnerRepository ownerRepository;
	private final DishRepository dishRepository;
	private final DishOptionRepository dishOptionRepository;
	private final ImageS3Service imageS3Service;
	/**
	 * 유저의 화면범위에 존재하는 가맹점을 반환하는 메서드
	 * @param minLat
	 * @param maxLat
	 * @param minLng
	 * @param maxLng
	 * @param people
	 * @param together
	 * @return
	 */
	@Override
	public RestaurantInfo getNearRestaurant(double minLat, double maxLat, double minLng, double maxLng,
		int people, boolean together) {

		//1. 해당 범위내의 모든 레스토랑을 조회한다
		List<Restaurant> findRestaurants = restaurantRepository.findByCoord(minLat, maxLat, minLng, maxLng);

		//2. 식당의 MAJOR 카테고리를 가지고온다
		List<RestaurantCategory> allMajor = restaurantCategoryRepository.findAllMajor();

		//2-1. major 카테고리 리스트 생성
		List<String> majorList = allMajor.stream().map(RestaurantCategory::getName).toList();
		log.warn("majorList : {}",majorList);

		//3. 식당카테고리 매핑 엔티티를 가지고온다
		List<Integer> restaurantIds = findRestaurants.stream().map(Restaurant::getId).toList();

		//3-1. <식당ID,식당> 의 맵 생성
		Map<Integer, Restaurant> IdToRestaurantMap = findRestaurants.stream()
			.collect(Collectors.toMap(Restaurant::getId, restaurant -> restaurant));

		//3-2. 식당과 카테고리를 이어주는 연결엔티티를 가지고온다
		List<RestaurantCategoryMap> restaurantCategoryMaps = restaurantCategoryMapRepository.findByRestaurandIds(
			restaurantIds);

		// 4. <식당ID, 카테고리 리스트> 의 맵을 생성한다
		Map<Integer, List<RestaurantCategoryDto>> IdToRestaurantCategoryMap = new HashMap<>();

		for(RestaurantCategoryMap restaurantCategoryMap : restaurantCategoryMaps) {

			int restaurantId = restaurantCategoryMap.getRestaurant().getId();
			
			RestaurantCategory restaurantCategory = restaurantCategoryMap.getRestaurantCategory();
			log.warn("restaurantCategory : {}",restaurantCategory);

			// 식당이 속한 (카테고리ID,카테고리이름)을 가진 DTO를 생성한다
			RestaurantCategoryDto categoryDto = RestaurantCategoryDto.from(restaurantCategory);

			log.warn("categoryDto : {}",categoryDto);

			if(!IdToRestaurantCategoryMap.containsKey(restaurantId)) {
				List<RestaurantCategoryDto> categoryDtos = new ArrayList<>();
				categoryDtos.add(categoryDto);
				IdToRestaurantCategoryMap.put(restaurantId, categoryDtos);
			}
			else{
				List<RestaurantCategoryDto> categoryDtos = IdToRestaurantCategoryMap.get(restaurantId);
				categoryDtos.add(categoryDto);
				IdToRestaurantCategoryMap.put(restaurantId, categoryDtos);
			}
		}
		//5. 식당에 대한 정보를 채워간다
		List<RestaurantDto> restaurantDtos = getRestaurantDtos(people, together,
			restaurantIds, IdToRestaurantMap, IdToRestaurantCategoryMap);

		//응답 DTO 생성
		RestaurantInfo restaurantInfo = RestaurantInfo.builder()
			.majorCategories(majorList)
			.restaurants(restaurantDtos)
			.build();

		return restaurantInfo;
	}

	/**
	 * 사업장 정보를 조회하는 메서드
	 * @param userId
	 * @return
	 */
	@Override
	public RestaurantProfileDto getRestaurant(Integer userId) {

		//1. 점주를 조회한다
		Owner owner = ownerRepository.findById(userId)
			.orElseThrow(() -> new JDQRException(ErrorCode.USER_NOT_FOUND));

		//2. 식당을 조회한다
		Restaurant restaurant = restaurantRepository.findByOwner(owner)
			.orElseThrow(() -> new JDQRException(ErrorCode.RESTAURANT_NOT_FOUND));

		//3. 응답을 리턴한다
		RestaurantProfileDto from = RestaurantProfileDto.from(restaurant);

		return from;
	}

	/**
	 * 입력받은 정보로 사업장을 등록한다
	 * @param profile
	 * @param userId
	 */
	@Override
	public void createRestaurant(RestaurantProfileDto profile, MultipartFile imageFile,Integer userId) {

		//1. 점주를 찾는다
		Owner owner = ownerRepository.findById(userId)
			.orElseThrow(() -> new JDQRException(ErrorCode.USER_NOT_FOUND));

		//2. 입력받은 정보로 사업장을 등록한다
		String imageUrl = imageS3Service.uploadImageToS3(imageFile);
		Restaurant restaurant = Restaurant.of(profile,imageUrl,owner);


		restaurantRepository.save(restaurant);
	}

	/**
	 * 가맹점의 상세정보를 조회하는 메서드
	 * @param restaurantId
	 */
	@Override
	public RestaurantDetailInfo getRestaurantDetail(Integer restaurantId) {

		//1. 식당을 조회한다
		Restaurant restaurant = restaurantRepository.findById(restaurantId)
			.orElseThrow(() -> new JDQRException(ErrorCode.RESTAURANT_NOT_FOUND));

		//2.식당과 카테고리를 이어주는 연결엔티티를 가지고온다
		List<RestaurantCategoryMap> restaurantCategoryMapList = restaurantCategoryMapRepository.findByRestaurantId(restaurantId);
		List<RestaurantCategoryDto> restaurantCategories = new ArrayList<>();
		for(RestaurantCategoryMap restaurantCategoryMap : restaurantCategoryMapList) {

			RestaurantCategory restaurantCategory = restaurantCategoryMap.getRestaurantCategory();
			RestaurantCategoryDto from = RestaurantCategoryDto.from(restaurantCategory);
			restaurantCategories.add(from);
		}

		//3. 테이블 정보를 받아온다
		List<Table> tables = tableRepository.findByRestaurantId(restaurantId);

		int restTableNum = 0;
		int restSeatNum = 0;
		int maxPeopleNum = 0;
		for(Table table : tables){
			if(table.getUseStatus().equals(UseStatus.AVAILABLE)){
				restTableNum++;
				restSeatNum += table.getPeople();
				maxPeopleNum = Math.max(maxPeopleNum,table.getPeople());
			}
		}
		// major 카테고리와  minor 카테고리를 구분한다
		List<RestaurantCategoryDto> major = new ArrayList<>();
		List<RestaurantCategoryDto> minor = new ArrayList<>();

		for(RestaurantCategoryDto restaurantCategoryDto : restaurantCategories){
			if(restaurantCategoryDto.getIsMajor()){
				major.add(restaurantCategoryDto);
			}
			else{
				minor.add(restaurantCategoryDto);
			}
		}

		RestaurantCategoryDetail categoryDetail = RestaurantCategoryDetail.of(major,minor);

		// 식당정보 생성
		RestaurantDto restaurantDto = getRestaurantDto(restaurantId, restaurant,
			categoryDetail, restTableNum, restSeatNum, maxPeopleNum);

		// dishInfo를 생성해야한다

		// 우선 dishCategoires 필요

		List<Dish> dishes = dishRepository.findDishesByRestaurant(restaurant);

		// <카테고리ID,카테고리이름> 의 맵을 생성한다
		Map<Integer,String> idToCategoryNameMap = new HashMap<>();
		for(Dish dish : dishes){
			int categoryId = dish.getDishCategory().getId();
			String categoryName = dish.getDishCategory().getName();

			if(!idToCategoryNameMap.containsKey(categoryId)){
				idToCategoryNameMap.put(categoryId,categoryName);
			}
		}

		// dishCategories
		List<String> dishCategories = idToCategoryNameMap.values().stream()
			.map(String::toString).toList();

		// 카테고리에 속한 음식정보 맵을 생성한다
		Map<Integer, List<DishDetailInfo>> idToDishDetailInfoMap = createIdToDishDetailMap(
			dishes);

		// dishes를 생성한다
		List<DishDetailSummaryInfo> dishDetailSummaryInfos = new ArrayList<>();
		for(Map.Entry<Integer,List<DishDetailInfo>> entry : idToDishDetailInfoMap.entrySet()){

			int categoryId = entry.getKey();
			String categoryName = idToCategoryNameMap.get(categoryId);
			List<DishDetailInfo> dishDetailInfos = entry.getValue();

			DishDetailSummaryInfo dishSummaryInfo = DishDetailSummaryInfo.builder()
				.dishCategoryId(categoryId)
				.dishCategoryName(categoryName)
				.items(dishDetailInfos)
				.build();

			dishDetailSummaryInfos.add(dishSummaryInfo);
		}

		//dishInfo를 생성한다
		DishDataDto dishDataDto = DishDataDto.of(dishCategories,dishDetailSummaryInfos);

		//dishInfo와 restaurantDto 마지막 DTO에 담아 반환한다
		RestaurantDetailInfo restaurantDetailInfo = RestaurantDetailInfo.of(restaurantDto, dishDataDto);

		return restaurantDetailInfo;
	}

	private static RestaurantDto getRestaurantDto(Integer restaurantId, Restaurant restaurant,
		RestaurantCategoryDetail restaurantCategories, int restTableNum, int restSeatNum, int maxPeopleNum) {
		RestaurantDto restaurantDto = RestaurantDto.builder()
			.id(restaurantId)
			.restaurantName(restaurant.getName())
			.restaurantCategories(restaurantCategories)
			.restTableNum(restTableNum)
			.restSeatNum(restSeatNum)
			.maxPeopleNum(maxPeopleNum)
			.address(restaurant.getAddress())
			.image(restaurant.getImage())
			.lat(restaurant.getLatitude())
			.lng(restaurant.getLongitude())
			.phoneNumber(restaurant.getPhoneNumber())
			.open(restaurant.getOpen())
			.build();
		return restaurantDto;
	}

	private Map<Integer, List<DishDetailInfo>> createIdToDishDetailMap(List<Dish> dishes) {
		// <카테고리ID, 카테고리 속 메뉴정보> 의 맵을 생성한다
		Map<Integer,List<DishDetailInfo>> idToDishDetailInfoMap = new HashMap<>();
		for(Dish dish : dishes){

			int dishCategoryId = dish.getDishCategory().getId();

			// 메뉴의 옵션들을 가지고온다
			List<DishOption> dishOptions = dishOptionRepository.findByDishId(dish.getId());

			List<OptionDto> optionDtos = new ArrayList<>();
			for(DishOption dishOption : dishOptions){

				Option option = dishOption.getOption();
				List<Choice> choices = option.getChoices();
				OptionDto optionDto = OptionDto.of(option, choices);

				optionDtos.add(optionDto);
			}

			// 메뉴의 태그들을 가지고온다
			List<String> tags = TagParser.parseTags(dish.getTags());

			// 5. 반환 DTO
			DishDetailInfo dishDetailInfo = DishDetailInfo.of(dish,optionDtos,tags);

			if(!idToDishDetailInfoMap.containsKey(dishCategoryId)){
				List<DishDetailInfo> dishDetailInfos = new ArrayList<>();
				dishDetailInfos.add(dishDetailInfo);
				idToDishDetailInfoMap.put(dishCategoryId, dishDetailInfos);
			}
			else{
				idToDishDetailInfoMap.get(dishCategoryId).add(dishDetailInfo);
			}
		}
		return idToDishDetailInfoMap;
	}

	/**
	 * 조건에 맞는 식당을 선별하여 정보를 반환하는 메서드
	 * @param people
	 * @param together
	 * @param restaurantIds
	 * @param IdToRestaurantMap
	 * @param IdToRestaurantCategoryMap
	 * @return
	 */
	private List<RestaurantDto> getRestaurantDtos(int people, boolean together, List<Integer> restaurantIds,
		Map<Integer, Restaurant> IdToRestaurantMap,
		Map<Integer, List<RestaurantCategoryDto>> IdToRestaurantCategoryMap) {
		List<RestaurantDto> restaurantDtos = new ArrayList<>();
		for(int idx = 0; idx< restaurantIds.size(); idx++){

			int restaurantId = restaurantIds.get(idx);
			log.warn("restaurantId : {}",restaurantId);
			Restaurant restaurant = IdToRestaurantMap.get(restaurantId);

			List<Table> findTables = tableRepository.findByRestaurantId(restaurantId);
			// 남은 테이블 수, 남은 좌석 수, 최대 몇 인 테이블인지 반환
			int restTableNum = 0;
			int restSeatNum = 0;
			int maxPeopleNum = 0;
			boolean isAvailable = false;
			for(Table table : findTables){
				// 우선 모든 테이블을 순회하면서, (남은 좌석 수, 남은 테이블 수)를 카운팅한다
				if(table.getUseStatus().equals(UseStatus.AVAILABLE)){
					restTableNum++;
					restSeatNum += table.getPeople();
					//함께앉기를 고려하여, 그 인원을 수용할 수 있는 테이블의 수를 카운팅한다
					if(table.getPeople() >= people)isAvailable = true;
					maxPeopleNum = Math.max(maxPeopleNum, table.getPeople());
				}
			}

			// 인원수가 기본값이 아닌 경우, 남은 좌석 수가 인원수보다 적고 열지 않았으면 해당 식당을 건너뜀
			if((people > 0 && restSeatNum < people) || !restaurant.getOpen()){
				continue;
			}

			// 함께 앉기를 원하는 경우, 최대 테이블 인원수가 인원수보다 적거나 열지 않았으면 해당 식당을 건너뜀
			if(together && !isAvailable){
				continue;
			}

			// 식당정보 생성
			List<RestaurantCategoryDto> restaurantCategories = IdToRestaurantCategoryMap.get(restaurantId);

			log.warn("restaurantCategories : {}",restaurantCategories);

			// major 카테고리와  minor 카테고리를 구분한다
			List<RestaurantCategoryDto> major = new ArrayList<>();
			List<RestaurantCategoryDto> minor = new ArrayList<>();

			for(RestaurantCategoryDto restaurantCategoryDto : restaurantCategories){
				if(restaurantCategoryDto.getIsMajor()){
					major.add(restaurantCategoryDto);
				}
				else{
					minor.add(restaurantCategoryDto);
				}
			}

			RestaurantCategoryDetail categoryDetail = RestaurantCategoryDetail.of(major,minor);

			RestaurantDto restaurantDto = getRestaurantDto(restaurantId, restaurant,
				categoryDetail, restTableNum, restSeatNum, maxPeopleNum);

			restaurantDtos.add(restaurantDto);
		}
		return restaurantDtos;
	}

	/**
	 * 가맹점의 영업여부를 업데이트하는 메서드
	 * @param restaurantId
	 */
	@Override
	public void updateBusinessStatus(Integer restaurantId) {
		//1. 식당을 조회한다
		Restaurant restaurant = restaurantRepository.findById(restaurantId)
			.orElseThrow(() -> new JDQRException(ErrorCode.RESTAURANT_NOT_FOUND));

		//2. 식당 테이블(restaurants)의 open컬럼 값이 true면 false로, false면 true로 변환
		restaurant.setOpen(!restaurant.getOpen());

		//3. 변경 사항을 저장하여 db에 반영
		restaurantRepository.save(restaurant);
	}

	/**
	 * 가맹점의 영업여부를 조회하는 메서드
	 * @param userId
	 * @return
	 */
	@Override
	public RestaurantBusinessDto getBusinessStatus(Integer userId) {
		//0. 점주를 조회한다
		Owner owner = ownerRepository.findById(userId)
			.orElseThrow(() -> new JDQRException(ErrorCode.USER_NOT_FOUND));

		//1. 식당을 조회한다
		Restaurant restaurant = restaurantRepository.findByOwner(owner)
			.orElseThrow(() -> new JDQRException(ErrorCode.RESTAURANT_NOT_FOUND));

		//2. 응답을 리턴한다
		RestaurantBusinessDto resDto =
			new RestaurantBusinessDto(restaurant.getOpen());

		return resDto;
	}

	/**
	 * 키워드에 해당하는 가맹점을 검색하는 메서드
	 * @param keyword
	 */
	@Override
	public RestaurantInfo searchByKeyword(String keyword,double minLat,double maxLat,double minLng,double maxLng,int people,boolean together) {

		// 키워드에 해당하는 가맹점을 검색한다
		List<Restaurant> findRestaurants = restaurantRepository.findByKeyword(keyword);
		for(Restaurant restaurant : findRestaurants){
			log.warn("restaurant : {}",restaurant);
		}

		//2. 식당의 MAJOR 카테고리를 가지고온다
		List<RestaurantCategory> allMajor = restaurantCategoryRepository.findAllMajor();

		//2-1. major 카테고리 리스트 생성
		List<String> majorList = allMajor.stream().map(RestaurantCategory::getName).toList();
		log.warn("majorList : {}",majorList);

		//3. 식당카테고리 매핑 엔티티를 가지고온다
		List<Integer> restaurantIds = findRestaurants.stream().map(Restaurant::getId).toList();

		//3-1. <식당ID,식당> 의 맵 생성
		Map<Integer, Restaurant> IdToRestaurantMap = findRestaurants.stream()
			.collect(Collectors.toMap(Restaurant::getId, restaurant -> restaurant));

		//3-2. 식당과 카테고리를 이어주는 연결엔티티를 가지고온다
		List<RestaurantCategoryMap> restaurantCategoryMaps = restaurantCategoryMapRepository.findByRestaurandIds(
			restaurantIds);

		// 4. <식당ID, 카테고리 리스트> 의 맵을 생성한다
		Map<Integer, List<RestaurantCategoryDto>> IdToRestaurantCategoryMap = new HashMap<>();

		for(RestaurantCategoryMap restaurantCategoryMap : restaurantCategoryMaps) {

			int restaurantId = restaurantCategoryMap.getRestaurant().getId();

			RestaurantCategory restaurantCategory = restaurantCategoryMap.getRestaurantCategory();
			log.warn("restaurantCategory : {}",restaurantCategory);

			// 식당이 속한 (카테고리ID,카테고리이름)을 가진 DTO를 생성한다
			RestaurantCategoryDto categoryDto = RestaurantCategoryDto.from(restaurantCategory);

			log.warn("categoryDto : {}",categoryDto);

			if(!IdToRestaurantCategoryMap.containsKey(restaurantId)) {
				List<RestaurantCategoryDto> categoryDtos = new ArrayList<>();
				categoryDtos.add(categoryDto);
				IdToRestaurantCategoryMap.put(restaurantId, categoryDtos);
			}
			else{
				List<RestaurantCategoryDto> categoryDtos = IdToRestaurantCategoryMap.get(restaurantId);
				categoryDtos.add(categoryDto);
				IdToRestaurantCategoryMap.put(restaurantId, categoryDtos);
			}
		}

		//5. 식당에 대한 정보를 채워간다
		List<RestaurantDto> restaurantDtos = getRestaurantDtos(people, together,
			restaurantIds, IdToRestaurantMap, IdToRestaurantCategoryMap);

		//응답 DTO 생성
		RestaurantInfo restaurantInfo = RestaurantInfo.builder()
			.majorCategories(majorList)
			.restaurants(restaurantDtos)
			.build();

		return restaurantInfo;

	}
}
