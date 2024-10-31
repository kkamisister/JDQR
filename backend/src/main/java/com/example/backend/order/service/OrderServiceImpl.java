package com.example.backend.order.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.backend.common.exception.ErrorCode;
import com.example.backend.common.exception.JDQRException;
import com.example.backend.common.redis.repository.RedisHashRepository;
import com.example.backend.common.util.GenerateLink;
import com.example.backend.order.dto.CartRequest.ProductInfo;
import com.example.backend.order.dto.ProductOption;
import com.example.backend.notification.service.NotificationService;
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
	public void addItem(String tableId, ProductInfo productInfo) {

		// Redis 장바구니에 물품 담기

		// //우선 productInfo에서 id를 꺼내서 그런 메뉴가 있는지부터 확인
		// int dishId = productInfo.id();
		//
		// dishRepository.findById(dishId); 이런식으로 확인해서 없으면 Exception

		log.warn("productInfo : {}",productInfo);
		for(ProductOption o: productInfo.options()){
			log.warn("option : {}",o);
		}

		// 테이블 장바구니에 물품을 담는다
		List<ProductInfo> cachedCartData = redisHashRepository.getCartDatas(tableId);
		if(cachedCartData != null){
			cachedCartData.add(productInfo);
			redisHashRepository.saveCartDatas(tableId, cachedCartData);
		}
		else{
			cachedCartData = new ArrayList<>();
			cachedCartData.add(productInfo);
			redisHashRepository.saveCartDatas(tableId, cachedCartData);
		}

		// 전송할 데이터를 가지고온다
		List<ProductInfo> sendDatas = redisHashRepository.getCartDatas(tableId);
		log.warn("sendDatas  :{}",sendDatas);

		notificationService.sentToClient(tableId,sendDatas);
	}
}
