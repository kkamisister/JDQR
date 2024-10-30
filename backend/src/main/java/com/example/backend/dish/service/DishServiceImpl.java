package com.example.backend.dish.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.backend.common.dto.CommonResponse;
import com.example.backend.common.dto.CommonResponse.ResponseWithData;
import com.example.backend.common.exception.ErrorCode;
import com.example.backend.common.exception.JDQRException;
import com.example.backend.dish.dto.DishResponse;
import com.example.backend.dish.dto.DishResponse.DishSummaryInfo;
import com.example.backend.dish.dto.DishResponse.DishSummaryResultDto;
import com.example.backend.dish.entity.Dish;
import com.example.backend.dish.repository.DishRepository;
import com.example.backend.owner.entity.Owner;
import com.example.backend.owner.repository.OwnerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

	private final DishRepository dishRepository;
	private final OwnerRepository ownerRepository;

	@Override
	public void addDish(Dish dish,Integer userId) {

		Owner owner = ownerRepository.findById(userId)
			.orElseThrow(() -> new JDQRException(ErrorCode.USER_NOT_FOUND));

		dishRepository.save(dish);
	}

	/**
	 * 전체 메뉴 목록을 조회하는 메서드
	 * @return
	 */
	@Override
	public ResponseWithData<DishSummaryResultDto> getAllDishes(Integer userId){
		//가게 주인을 가지고 온다
		Owner owner = ownerRepository.findById(userId)
			.orElseThrow(() -> new JDQRException(ErrorCode.USER_NOT_FOUND));

		//가게 주인의 메뉴 목록을 가지고 온다
		List<Dish> alldishes = dishRepository.getAllDishes(); //db에서 모든 dish 엔터티를 조회

		//메뉴 정보를 dto로 변환한 리스트를 만든다
		List<DishSummaryInfo> dishSummarys = alldishes.stream()
			.map(DishSummaryInfo::from)
			.toList();

		//결과를 resultDTO에 담아서 반환한다.
		DishSummaryResultDto dishResultDto = DishSummaryResultDto.builder()
			.dishNum(0)
			.dishes(dishSummarys)
			.build();

		return new ResponseWithData<>(HttpStatus.OK.value(), "메뉴 조회에 성공하였습니다.", dishResultDto);
	}

}
