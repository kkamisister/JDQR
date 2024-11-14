package com.example.backend.common.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.example.backend.common.interceptor.JDQRChannelInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	private final JDQRChannelInterceptor jdqrChannelInterceptor;

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config){

		// 서버 -> 클라이언트로 메시지를 전달할 때 사용하는 경로 설정
		// 클라이언트가 구독한 채널로 전달
		config.enableSimpleBroker("/sub");

		// 클라이언트 -> 서버로 메시지를 보낼 때 사용하는 경로 설정
		// 클라이언트가 발행한 메세지 전달
		config.setApplicationDestinationPrefixes("/pub");

	}
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {

		//클라이언트에서 연결할 엔드포인트 설정
		registry.addEndpoint("/ws")
			.setAllowedOrigins("http://localhost:3000","https://jdqr608.duckdns.org");
			// .setHandshakeHandler(new CustomHandshakeHandler());
		// .withSockJS();
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(jdqrChannelInterceptor);
	}

	// @Override
	// public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
	// 	// JSON 형식의 메시지를 처리하기 위해 Jackson 메시지 컨버터 추가
	// 	messageConverters.add(new MappingJackson2MessageConverter());
	// 	return true; // 추가 컨버터 사용을 기본으로 설정
	// }

}
