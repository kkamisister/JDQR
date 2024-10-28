package com.example.backend.common.redis.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RedisHashRepositoryImpl implements RedisHashRepository{

	private final RedisTemplate<String,Object> redisTemplate;

	@Override
	public void saveHashData(String key, String subKey, Object value, long ttl) {
		if(Boolean.FALSE.equals(redisTemplate.hasKey(key))){
			redisTemplate.opsForHash().put(key,subKey,value);
			redisTemplate.expire(key,ttl, TimeUnit.MINUTES);
		}

		redisTemplate.opsForHash().put(key,subKey,value);
	}

	@Override
	public void removeHashData(String key, String subKey) {

		if(Boolean.FALSE.equals(redisTemplate.hasKey(key)))return;

		redisTemplate.opsForHash().delete(key,subKey);
	}

	@Override
	public Object getHashData(String key, String subKey) {
		if(Boolean.FALSE.equals(redisTemplate.hasKey(key))){
			return null;
		}

		return redisTemplate.opsForHash().get(key,subKey);
	}
}
