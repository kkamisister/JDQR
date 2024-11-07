package com.example.backend.owner.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.backend.dish.dto.ChoiceDto;
import com.example.backend.etc.entity.Restaurant;
import com.example.backend.etc.repository.RestaurantRepository;
import com.example.backend.owner.dto.OptionVo;
import com.example.backend.owner.dto.OwnerResponse.*;
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

	@Override
	public WholeOptionResponseDto getWholeOptionInfo(Integer userId) {
		Restaurant restaurant = restaurantRepository.findByOwnerId(userId)
			.orElseThrow(() -> new JDQRException(ErrorCode.RESTAURANT_NOT_FOUND));

		List<OptionVo> optionVos = optionRepository.findAllOptionByRestaurant(restaurant);
		Map<Integer, List<OptionVo>> optionGroupByOptionId = optionVos.stream()
			.collect(Collectors.groupingBy(OptionVo::getOptionId));
		List<OptionResponseDto> optionResponseDtos = optionGroupByOptionId.entrySet().stream()
			.map(optionEntry -> {
				Integer optionId = optionEntry.getKey();
				List<OptionVo> values = optionEntry.getValue();
				OptionVo baseOptionVo = values.get(0);

				List<ChoiceDto> choiceDtos = values.stream()
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
			})
			.toList();

		return WholeOptionResponseDto.builder()
			.options(optionResponseDtos)
			.build();
	}

}
