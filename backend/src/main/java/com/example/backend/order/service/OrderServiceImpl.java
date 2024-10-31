package com.example.backend.order.service;

import static com.example.backend.order.dto.CartResponse.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.backend.common.exception.ErrorCode;
import com.example.backend.common.exception.JDQRException;
import com.example.backend.common.redis.repository.RedisHashRepository;
import com.example.backend.common.util.GenerateLink;
import com.example.backend.dish.repository.DishRepository;
import com.example.backend.order.dto.CartDto;
import com.example.backend.notification.service.NotificationService;
import com.example.backend.order.dto.CartResponse;
import com.example.backend.order.entity.Order;
import com.example.backend.table.entity.Table;
import com.example.backend.table.repository.TableRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

	private final TableRepository tableRepository;
	private final GenerateLink generateLink;
	private final NotificationService notificationService;
	private final RedisHashRepository redisHashRepository;
	private final DishRepository dishRepository;
	/**
	 * tableName으로 qrCode를 찾아서, 해당 코드에 token을 더한 주소를 반환
	 * @param tableId
	 * @return
	 */
	@Override
	public String redirectUrl(String tableId,String uuid) {
		//1. table을 찾는다
		Table table = tableRepository.findById(tableId)
			.orElseThrow(() -> new JDQRException(ErrorCode.FUCKED_UP_QR));

		log.warn("table : {}",table);

		// 현재 url이 유효하지 않다면 예외를 반환한다
		String targetUrl = GenerateLink.AUTH_PREFIX + "/"+tableId+"/"+uuid;
		if(!targetUrl.equals(table.getQrCode())){
			throw new JDQRException(ErrorCode.FUCKED_UP_QR);
		}

		//2. table의 링크에 token을 생성한다
		String authLink = generateLink.createAuthLink(table.getQrCode(),table.getId());

		return authLink;
	}

	/**
	 * 장바구니에 물품을 담는 메서드
	 * @param tableId
	 * @param productInfo
	 */
	@Override
	public void addItem(String tableId,String userId,CartDto productInfo) {

		// Redis 장바구니에 물품 담기

		// 1. productInfo에서 id를 꺼내서 그런 메뉴가 있는지부터 확인
		dishRepository.findById(productInfo.dishId())
			.orElseThrow(() -> new JDQRException(ErrorCode.DISH_NOT_FOUND));

		// 2. 테이블 장바구니에 물품을 담는다
		List<CartDto> cachedCartData = redisHashRepository.getCartDatas(tableId,userId);
		String key = "table::"+tableId;
		if(cachedCartData != null){
			cachedCartData.add(productInfo);
			redisHashRepository.saveHashData(key,userId,cachedCartData,20L);
		}
		else{
			cachedCartData = new ArrayList<>();
			cachedCartData.add(productInfo);
			redisHashRepository.saveHashData(key,userId,cachedCartData,20L);
		}

		// 3. 전송할 데이터를 생성한다

		// 3-1. 현재 테이블의 장바구니를 가지고온다.
		// 맵은 (userId,해당 유저가 담은 항목들) 의 형식
		Map<String, List<CartDto>> allCartDatas = redisHashRepository.getAllCartDatas(tableId);

		// 3-2. 현재 테이블과 연결된 사람수를 받아온다
		int subscriberSize = notificationService.getSubscriberSize(tableId);

		// 3-3. 현재 테이블의 이름을 가져오기위해 테이블을 조회한다
		Table table = tableRepository.findById(tableId).orElseThrow(() -> new JDQRException(ErrorCode.TABLE_NOT_FOUND));

		// 3-4. 최종적으로 전송할 데이터
		CartInfo sendData = CartInfo.of(allCartDatas,table.getName(),subscriberSize);

		notificationService.sentToClient(tableId,sendData);
	}
}
