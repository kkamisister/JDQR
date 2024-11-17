package com.example.backend.common.interceptor;

import static com.example.backend.common.enums.OnlineUser.*;
import static com.example.backend.common.enums.Operator.*;

import java.util.concurrent.TimeUnit;

import com.example.backend.common.enums.Operator;
import com.example.backend.common.event.CartEvent;
import com.example.backend.common.exception.ErrorCode;
import com.example.backend.common.exception.JDQRException;
import com.example.backend.common.util.TokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.*;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
@RequiredArgsConstructor
@Slf4j
public class JDQRChannelInterceptor implements ChannelInterceptor {

	private final TokenProvider tokenProvider;
	private static final String TOKEN_PREFIX = "Bearer ";
	private final RedisTemplate<String,Object> redisTemplate;
	private final ApplicationEventPublisher publisher;


	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {

		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
		// log.warn("preSend : {}",accessor);

		// CONNECT 프레임에 대해서만 처리
		if (StompCommand.CONNECT.equals(accessor.getCommand())) {
			String accessToken = accessor.getFirstNativeHeader("Authorization");
			log.warn("accessToken : {}",accessToken);
			if (!ObjectUtils.isEmpty(accessToken) && accessToken.startsWith(TOKEN_PREFIX)) {
				accessToken = accessToken.substring(TOKEN_PREFIX.length());

				if(accessToken.equals("dummyTableToken")){
					accessor.getSessionAttributes().put("tableId","6721aa9b0d22a923091eef73");
					return message;
				}

			} else {
				log.info("Authorization 헤더가 없습니다.");
				throw new IllegalArgumentException("Authorization 헤더가 없습니다.");
			}

			// 토큰 검증 로직 적용
			try {
				if (tokenProvider.validateToken(accessToken)) {
					String tableId = tokenProvider.extractSubject(accessToken);
					// 세션 속성에 사용자 ID 저장
					accessor.getSessionAttributes().put("tableId", tableId);
				} else {
					throw new JDQRException(ErrorCode.TOKEN_IS_NOT_VALID);
				}
			} catch (Exception e) {
				log.error("토큰 검증 실패: {}", e.getMessage());
				throw new IllegalArgumentException("토큰 검증 실패");
			}
		}
		return message;
	}
	@Override
	public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

		// log.warn("postSend : {}",accessor);
		// DISCONNECT 프레임에 대해서만 처리
		if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {

			String sessionId = accessor.getSessionId();

			// 동일한 세션으로부터 중복된 DISCONNECT오는것을 대비하여 거는 필터링
			if(Boolean.TRUE.equals(redisTemplate.opsForSet().isMember("processed-disconnects", sessionId))){
				log.warn("Duplicate DISCONNECT event for sessionId : {}",sessionId);
				return;
			}

			// 목적지를 찾는다
			String dest = (String)accessor.getSessionAttributes().get("destination");

			// log.warn("dest : {}",dest);

			// /sub/cart의 경우에만 이벤트를 호출한다
			if(dest != null && dest.equals("/sub/cart")){
				String tableId = (String)accessor.getSessionAttributes().get("tableId");

				// 만료된 세션을 표기한다
				redisTemplate.opsForSet().add("processed-disconnects",sessionId);
				redisTemplate.expire("processed-disconnects",5, TimeUnit.MINUTES);
				// 여기에다가 구독시 tableId에 속한 장바구니 데이터를 보내는 이벤트 설정
				publisher.publishEvent(new CartEvent(tableId, MINUS));
			}

		}
		if(StompCommand.SUBSCRIBE.equals(accessor.getCommand())){
			String destination = accessor.getDestination(); // 구독 요청의 URL 확인

			if (destination != null && destination.contains("/sub/cart")) {

				String dest = destination.substring(0,destination.lastIndexOf("/"));
				// log.warn("dest : {}",dest);
				String tableId = destination.substring(destination.lastIndexOf("/") + 1);

				// 추후 경로 구분을 위한 destination 설정. 여기서 해놔야 연결끊어질때 구분가능함
				accessor.getSessionAttributes().put("destination", dest);

				// 여기에다가 구독시 tableId에 속한 장바구니 데이터를 보내는 이벤트 설정
				publisher.publishEvent(new CartEvent(tableId,PLUS));
			} else {
				log.warn("구독 요청에 예상치 않은 destination: {}", destination);
			}
		}
	}
}
