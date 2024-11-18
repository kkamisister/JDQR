package com.example.backend.common.event;

import static com.example.backend.common.enums.OnlineUser.*;
import static com.example.backend.common.enums.Operator.*;
import static com.example.backend.order.dto.CartResponse.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.example.backend.common.enums.Operator;
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
	private final RedisTemplate<String,Object> redisTemplate;

	// @Async
	@EventListener
	public void sendData(CartEvent event) throws InterruptedException {
		// 3. 전송할 데이터를 생성한다
		String tableId = event.getTableId();
		String sessionId = event.getSessionId();
		Operator operator = event.getOperator();

		// 3-1. 현재 테이블의 장바구니를 가지고온다.
		// 맵은 (userId,해당 유저가 담은 항목들) 의 형식
		Map<String, Map<Integer, CartDto>> allCartDatas = redisHashRepository.getAllCartDatas(tableId);

		// 처음 구독하는 세션인 경우만, 인원수 변경을 해야한다
		Integer subscriberSize = null;
		if(!Boolean.TRUE.equals(redisTemplate.opsForSet().isMember("subscribed_sessions:" + sessionId, sessionId))){
			redisTemplate.opsForSet().add("subscribed_sessions", sessionId);
			redisTemplate.expire("subscribed_sessions", 10, TimeUnit.MINUTES);

			log.warn("인원수 변화 !!!!");
			subscriberSize = redisTemplate.opsForHash().increment(ONLINE_USER.getExplain(), tableId, 1).intValue();
		}
		else{ // 여기에 걸리는 경우는, 이미 구독한적이 있던 세션에서 이벤트를 날리는 경우이다. 그런 이벤트는 (SUBSCRIBE,DISCONNECT) 2가지가 있다

			// SUBSCRIBE인 경우는 무시하고 DISCONNECT인 경우만 인원수를 줄여주면 된다
			if(operator.equals(MINUS)){ // DISCONNECT 이벤트인 경우
				log.warn("인원수 변화 !!!!");
				subscriberSize = redisTemplate.opsForHash().increment(ONLINE_USER.getExplain(), tableId, -1).intValue();
			}
		}
		if(ObjectUtils.isEmpty(subscriberSize)){
			subscriberSize = redisHashRepository.getCurrentUserCnt(tableId);
		}
		if(subscriberSize < 0)subscriberSize = 0;

		log.warn("현재 연결된 사람 수 :{}",subscriberSize);

		// 3-3. 현재 테이블의 이름을 가져오기위해 테이블을 조회한다
		Table table = tableRepository.findById(tableId).orElseThrow(() -> new JDQRException(ErrorCode.TABLE_NOT_FOUND));

		// log.warn("table : {}",table.getId());

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
