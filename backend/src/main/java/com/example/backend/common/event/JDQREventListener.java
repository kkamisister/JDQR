package com.example.backend.common.event;

import static com.example.backend.order.dto.CartResponse.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.example.backend.common.exception.ErrorCode;
import com.example.backend.common.exception.JDQRException;
import com.example.backend.common.redis.repository.RedisHashRepository;
import com.example.backend.order.dto.CartDto;
import com.example.backend.order.dto.CartResponse;
import com.example.backend.table.entity.Table;
import com.example.backend.table.repository.TableRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JDQREventListener {

	private final RedisHashRepository redisHashRepository;
	private final TableRepository tableRepository;
	private final SimpMessagingTemplate messagingTemplate;

	// @Async
	@EventListener
	public void sendData(CartEvent event) throws InterruptedException {
		// 3. 전송할 데이터를 생성한다
		String tableId = event.getTableId();
		// 3-1. 현재 테이블의 장바구니를 가지고온다.
		// 맵은 (userId,해당 유저가 담은 항목들) 의 형식
		Map<String, Map<Integer, CartDto>> allCartDatas = redisHashRepository.getAllCartDatas(tableId);

		// 3-2. 현재 테이블과 연결된 사람수를 받아온다
		Integer subscriberSize = redisHashRepository.getCurrentUserCnt(tableId);

		log.warn("현재 연결된 사람 수 :{}",subscriberSize);

		// 3-3. 현재 테이블의 이름을 가져오기위해 테이블을 조회한다
		Table table = tableRepository.findById(tableId).orElseThrow(() -> new JDQRException(ErrorCode.TABLE_NOT_FOUND));

		log.warn("table : {}",table.getId());

		// 3-4. 최종적으로 전송할 데이터
		List<CartDto> cartList = allCartDatas.values().stream()
			.flatMap(map -> map.values().stream())
			.sorted(Comparator.comparing(CartDto::getOrderedAt).reversed())
			.collect(Collectors.toList());

		int totalPrice = 0;
		int totalQuantity = 0;
		for(CartDto cartData : cartList){
			totalPrice += cartData.getPrice() * cartData.getQuantity();
			totalQuantity += cartData.getQuantity();
		}

		CartInfo sendData = CartInfo.of(cartList,table.getName(),subscriberSize,totalPrice,totalQuantity,null);

		// notificationService.sentToClient(tableId,sendData);
		// log.warn("sendData : {}",sendData);
		messagingTemplate.convertAndSend("/sub/cart/"+tableId,sendData);
	}

}
