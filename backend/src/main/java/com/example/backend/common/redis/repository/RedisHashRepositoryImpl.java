package com.example.backend.common.redis.repository;

import static com.example.backend.common.enums.OnlineUser.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.example.backend.common.enums.OnlineUser;
import com.example.backend.common.util.JsonUtil;
import com.example.backend.order.dto.CartDto;
import com.fasterxml.jackson.core.type.TypeReference;

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
	public Map<String, Map<Integer, CartDto>> getAllCartDatas(String tableId) {
		String key = "table::" + tableId;
		Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);

		return entries.entrySet().stream()
			.collect(Collectors.toMap(
				entry -> (String) entry.getKey(),
				entry -> JsonUtil.readMap(JsonUtil.objectToString(entry.getValue()),
					new TypeReference<Map<Integer, CartDto>>() {})));
	}

	@Override
	public Map<Integer,CartDto> getCartDatas(String tableId,String userId) {

		Object cachedData = redisTemplate.opsForHash().get(tableId,userId);
		if(cachedData != null) {
			log.warn("cachedData : {}",cachedData);
			return JsonUtil.readMap(JsonUtil.objectToString(cachedData),
				new TypeReference<Map<Integer, CartDto>>() {
				});
		}
		return null;
	}

	@Override
	public void saveCartDatas(String tableId,int hashCode, Map<String,List<CartDto>> cartDatas) {
		String key = "table::"+tableId;

		// redisTemplate.opsForValue().set(key,cartDatas);
		redisTemplate.opsForHash().put(tableId,hashCode,cartDatas);
		redisTemplate.expire(key,20, TimeUnit.MINUTES);
	}

	/**
	 * redis에 key에 해당하는 데이터를 모두 삭제한다.
	 * @param key : 삭제를 희망하는 redis의 key
	 */
	@Override
	public void removeKey(String key) {
		redisTemplate.delete(key);
	}

	@Override
	public Integer getCurrentUserCnt(String key) {

		// 현재 온라인 사용자 수 가져오기
		Integer currentCount = (Integer)redisTemplate.opsForHash().get(ONLINE_USER.getExplain(), key);

		return currentCount;
	}
}
