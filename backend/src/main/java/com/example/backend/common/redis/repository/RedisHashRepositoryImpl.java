package com.example.backend.common.redis.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.example.backend.order.dto.CartRequest.ProductInfo;

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

	@Override
	public List<ProductInfo> getCartDatas(String tableId) {

		String key = "table::"+tableId;

		Object cachedData = redisTemplate.opsForValue().get(key);
		if(cachedData != null){
			return (List<ProductInfo>)cachedData;
		}
		return null;
	}

	@Override
	public void saveCartDatas(String tableId, List<ProductInfo> cartDatas) {
		String key = "table::"+tableId;

		redisTemplate.opsForValue().set(key,cartDatas);
		redisTemplate.expire(key,20, TimeUnit.MINUTES);
	}

	/**
	 * redis에 key에 해당하는 데이터를 모두 삭제한다.
	 * @param key : 삭제를 희망하는 redis의 key
	 */
	@Override
	public void removeKey(String key) {
		redisTemplate.opsForValue().getAndDelete(key);
	}
}
