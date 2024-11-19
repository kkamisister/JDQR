package com.example.backend.common.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class RedisConfig {

	private static final String REDISSON_HOST_PREFIX = "redis://";

	@Value("${spring.data.redis.host}")
	private String host;
	@Value("${spring.data.redis.port}")
	private int port;

	@Primary
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedissonConnectionFactory redissonConnectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redissonConnectionFactory);
		// redisTemplate.setEnableTransactionSupport(true);

		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());

		// ObjectMapper 커스터마이징
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());  // Java 8 날짜 타입 지원
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);  // 타임스탬프 대신 ISO 형식 사용

		// 객체 타입 정보를 포함하여 직렬화할 수 있도록 설정
		objectMapper.activateDefaultTyping(
			objectMapper.getPolymorphicTypeValidator(),
			ObjectMapper.DefaultTyping.NON_FINAL
		);

		// Custom GenericJackson2JsonRedisSerializer 설정
		GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer(objectMapper);
		redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer); // Hash Value에 적용
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);

		return redisTemplate;
	}

	@Bean
	public RedissonClient redissonClient() {
		Config config = new Config();
		config.useSingleServer()
			.setAddress(REDISSON_HOST_PREFIX + host + ":" + port);
		return Redisson.create(config);
	}

	@Bean
	public RedissonConnectionFactory redissonConnectionFactory(RedissonClient redissonClient) {
		return new RedissonConnectionFactory(redissonClient);
	}
}
