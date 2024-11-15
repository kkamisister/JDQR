package com.example.backend.common.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.example.backend.common.handler.CustomHandshakeHandler;
import com.example.backend.common.interceptor.JDQRChannelInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

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
		config.enableSimpleBroker("/sub","/queue");

		// 클라이언트 -> 서버로 메시지를 보낼 때 사용하는 경로 설정
		// 클라이언트가 발행한 메세지 전달
		config.setApplicationDestinationPrefixes("/pub");

	}
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {

		//클라이언트에서 연결할 엔드포인트 설정
		registry.addEndpoint("/ws")
			.setAllowedOrigins("http://localhost:3000","https://jdqr608.duckdns.org","https://jdqr608.duckdns.org:8081")
			.setHandshakeHandler(new CustomHandshakeHandler());
		// .withSockJS();
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(jdqrChannelInterceptor);
	}

	@Override
	public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		ObjectMapper objectMapper = new ObjectMapper();

		objectMapper.registerModule(new JavaTimeModule()); // Java 8 날짜/시간 모듈 등록
		converter.setObjectMapper(objectMapper);
		messageConverters.add(converter);
		return false;
	}

}
