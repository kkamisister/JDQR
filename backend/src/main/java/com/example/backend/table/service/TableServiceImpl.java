package com.example.backend.table.service;

import static com.example.backend.table.dto.TableRequest.*;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.common.dto.CommonResponse.ResponseWithData;
import com.example.backend.common.enums.UseStatus;
import com.example.backend.common.exception.ErrorCode;
import com.example.backend.common.exception.JDQRException;
import com.example.backend.common.util.GenerateLink;
import com.example.backend.restaurants.entity.Restaurants;
import com.example.backend.restaurants.repository.RestaurantsRepository;
import com.example.backend.table.entity.Table;
import com.example.backend.table.repository.TableRepository;
import com.example.backend.user.entity.User;
import com.example.backend.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TableServiceImpl implements TableService{

	private final UserRepository userRepository;
	private final RestaurantsRepository restaurantsRepository;
	private final TableRepository tableRepository;
	private final GenerateLink generateLink;
	/**
	 * 요청받은 테이블의 정보를, userId를 가진 점주를 찾아 등록한다
	 * @param tableInfo
	 * @param userId
	 * @return
	 */
	@Override
	public ResponseWithData<String> createTable(TableInfo tableInfo, Integer userId) {

		//1. 점주를 찾는다
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new JDQRException(ErrorCode.USER_NOT_FOUND));

		//2. 점주가 가진 식당을 찾는다
		Restaurants restaurants = restaurantsRepository.findByUser(user)
			.orElseThrow(() -> new JDQRException(ErrorCode.FUCKED_UP_QR));

		//3. 테이블 정보 + 식당ID를 합쳐서 mongoDB에 저장한다
		Table table = Table.of(tableInfo,restaurants.getId(),UseStatus.AVAILABLE);
		Table savedTable = tableRepository.save(table);

		//4. QRCode를 생성하기위한 링크를 반환한다.
		String link = generateLink.create(savedTable.getId());

		return new ResponseWithData<>(HttpStatus.OK.value(),"URL 생성에 성공하였습니다",link);
	}
}
