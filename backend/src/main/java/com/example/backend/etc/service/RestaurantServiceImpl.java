package com.example.backend.etc.service;

import static com.example.backend.etc.dto.RestaurantRequest.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.common.enums.UseStatus;
import com.example.backend.common.exception.ErrorCode;
import com.example.backend.common.exception.JDQRException;
import com.example.backend.etc.dto.RestaurantCategoryDto;
import com.example.backend.etc.dto.RestaurantDto;
import com.example.backend.etc.dto.RestaurantProfileDto;
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
			
			// 식당이 속한 (카테고리ID,카테고리이름)을 가진 DTO를 생성한다
			int restaurantCategoryId = restaurantCategoryMap.getRestaurantCategory().getId();
			String restaurantCategoryName = restaurantCategoryMap.getRestaurantCategory().getName();

			RestaurantCategoryDto categoryDto = new RestaurantCategoryDto(restaurantCategoryId,restaurantCategoryName);

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
	 * @param restaurantId
	 * @param userId
	 * @return
	 */
	@Override
	public RestaurantProfileDto getRestaurant(Integer restaurantId, Integer userId) {

		//1. 점주를 조회한다
		Owner owner = ownerRepository.findById(userId)
			.orElseThrow(() -> new JDQRException(ErrorCode.USER_NOT_FOUND));

		//2. 식당을 조회한다
		Restaurant restaurant = restaurantRepository.findById(restaurantId)
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
	public void createRestaurant(RestaurantProfileDto profile,Integer userId) {

		//1. 점주를 찾는다
		Owner owner = ownerRepository.findById(userId)
			.orElseThrow(() -> new JDQRException(ErrorCode.USER_NOT_FOUND));

		//2. 입력받은 정보로 사업장을 등록한다
		Restaurant restaurant = Restaurant.of(profile,owner);

		restaurantRepository.save(restaurant);
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
			Restaurant restaurant = IdToRestaurantMap.get(restaurantId);

			List<Table> findTables = tableRepository.findByRestaurantId(restaurantId);
			// 남은 테이블 수, 남은 좌석 수, 최대 몇 인 테이블인지 반환
			int restTable = 0;
			int restSeat = 0;
			int maxPeopleNum = 0;
			boolean isAvailable = false;
			for(Table table : findTables){
				// 우선 모든 테이블을 순회하면서, (남은 좌석 수, 남은 테이블 수)를 카운팅한다
				if(table.getStatus().equals(UseStatus.AVAILABLE)){
					restTable++;
					restSeat += table.getPeople();
					//함께앉기를 고려하여, 그 인원을 수용할 수 있는 테이블의 수를 카운팅한다
					if(table.getPeople() >= people)isAvailable = true;
					maxPeopleNum = Math.max(maxPeopleNum, table.getPeople());
				}
			}

			// 인원수가 기본값이 아닌 경우, 남은 좌석 수가 인원수보다 적고 열지 않았으면 해당 식당을 건너뜀
			if((people > 0 && restSeat < people) && !restaurant.getOpen()){
				continue;
			}

			// 함께 앉기를 원하는 경우, 최대 테이블 인원수가 인원수보다 적거나 열지 않았으면 해당 식당을 건너뜀
			if(together && !isAvailable){
				continue;
			}

			RestaurantDto restaurantDto = RestaurantDto.builder()
				.id(restaurantId)
				.restaurantName(restaurant.getName())
				.restaurantCategories(IdToRestaurantCategoryMap.get(restaurantId))
				.restSeatNum(restSeat)
				.restTableNum(restTable)
				.address(restaurant.getAddress())
				.image(restaurant.getImage())
				.lat(restaurant.getLatitude())
				.lng(restaurant.getLongitude())
				.open(restaurant.getOpen())
				.build();

			restaurantDtos.add(restaurantDto);
		}
		return restaurantDtos;
	}
}
