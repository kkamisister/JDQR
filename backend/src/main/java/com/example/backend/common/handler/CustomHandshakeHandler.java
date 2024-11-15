package com.example.backend.common.handler;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.example.backend.common.util.StompPrincipal;

public class CustomHandshakeHandler extends DefaultHandshakeHandler {
	protected StompPrincipal determineUser(ServerHttpRequest request,
		WebSocketHandler wsHandler,
		Map<String, Object> attributes) {
		return new StompPrincipal(UUID.randomUUID().toString());
	}

}
