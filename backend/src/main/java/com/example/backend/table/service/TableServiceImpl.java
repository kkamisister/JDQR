package com.example.backend.table.service;

import static com.example.backend.table.dto.TableRequest.*;
import static com.example.backend.table.dto.TableResponse.*;

import com.example.backend.common.enums.EntityStatus;
import com.example.backend.etc.entity.Restaurant;
import com.example.backend.owner.entity.Owner;
import com.example.backend.owner.repository.OwnerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.common.dto.CommonResponse.ResponseWithData;
import com.example.backend.common.enums.UseStatus;
import com.example.backend.common.exception.ErrorCode;
import com.example.backend.common.exception.JDQRException;
import com.example.backend.common.util.GenerateLink;
import com.example.backend.etc.repository.RestaurantRepository;
import com.example.backend.table.dto.TableResponse;
import com.example.backend.table.entity.Table;
import com.example.backend.table.repository.TableRepository;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TableServiceImpl implements TableService{

	private final OwnerRepository ownerRepository;
	private final RestaurantRepository restaurantRepository;
	private final TableRepository tableRepository;
	private final GenerateLink generateLink;
	/**
	 * 요청받은 테이블의 정보를, userId를 가진 점주를 찾아 등록한다
	 * @param tableInfo
	 * @param userId
	 * @return
	 */
	@Override
	public void createTable(TableInfo tableInfo, Integer userId) {

		//1. 점주를 찾는다
		Owner owner = ownerRepository.findById(userId)
			.orElseThrow(() -> new JDQRException(ErrorCode.USER_NOT_FOUND));

		//2. 점주가 가진 식당을 찾는다
		Restaurant restaurant = restaurantRepository.findByOwner(owner)
			.orElseThrow(() -> new JDQRException(ErrorCode.RESTAURANT_NOT_FOUND));

		//3. 테이블 정보 + 식당ID를 합쳐서 mongoDB에 저장한다
		Table table = Table.of(tableInfo,restaurant.getId(),UseStatus.AVAILABLE);
		Table savedTable = tableRepository.save(table);

		//4. QRCode를 생성하기위한 링크를 반환한다.
		String link = generateLink.create(savedTable.getId());

		//5. 테이블의 qrlink를 업데이트한다
		savedTable.updateQrCode(link);
		tableRepository.save(savedTable);
	}

	/**
	 * 테이블을 삭제하는 메서드
	 * @param tableId
	 * @param userId
	 */
	@Override
	public void deleteTable(String tableId, Integer userId) {

		//1. 점주를 찾는다
		Owner owner = ownerRepository.findById(userId)
			.orElseThrow(() -> new JDQRException(ErrorCode.USER_NOT_FOUND));

		//2. 테이블을 찾는다
		Table table = tableRepository.findById(tableId)
			.orElseThrow(() -> new JDQRException(ErrorCode.TABLE_NOT_FOUND));

		//3. 삭제한다
		table.setStatus(EntityStatus.DELETE);

		tableRepository.save(table);
	}

	/**
	 * 테이블 정보를 수정하는 메서드
	 * @param tableInfo
	 * @param userId
	 */
	@Override
	public void updateTable(TableInfo tableInfo, Integer userId) {

		log.warn("업데이트 시작");
		log.warn("tableInfo : {}",tableInfo);
		//1. 점주를 찾는다
		ownerRepository.findById(userId)
			.orElseThrow(() -> new JDQRException(ErrorCode.USER_NOT_FOUND));

		//2. 테이블을 찾는다
		Table table = tableRepository.findById(tableInfo.tableId())
			.orElseThrow(() -> new JDQRException(ErrorCode.TABLE_NOT_FOUND));

		log.warn("tableId : {}",table.getId());
		//3. 업데이트한다
		table.updateTable(tableInfo);

		tableRepository.save(table);
	}

	/**
	 * QR코드를 재생성하는 메서드
	 * @param tableId
	 * @param userId
	 * @return
	 */
	@Override
	public QRInfo createQR(String tableId, Integer userId) {

		//1. 점주를 찾는다
		ownerRepository.findById(userId)
			.orElseThrow(() -> new JDQRException(ErrorCode.USER_NOT_FOUND));

		//2. 테이블을 찾는다
		Table table =  tableRepository.findById(tableId)
			.orElseThrow(() -> new JDQRException(ErrorCode.TABLE_NOT_FOUND));

		//3. 테이블의 QR을 재생성한다
		String link = generateLink.create(table.getId());

		//4. 테이블의 qrlink를 업데이트한다
		table.updateQrCode(link);
		tableRepository.save(table);

		return new QRInfo(link);
	}
}
