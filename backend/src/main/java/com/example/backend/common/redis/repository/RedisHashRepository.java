package com.example.backend.common.redis.repository;

import java.util.List;
import java.util.Map;

import com.example.backend.order.dto.CartDto;

public interface RedisHashRepository {
	void saveHashData(String key,String subKey,Object value,long ttl);
	void removeHashData(String key,String subKey);

	Object getHashData(String key,String subKey);

	Map<String,Map<Integer,CartDto>> getAllCartDatas(String tableId);
	Map<Integer,CartDto> getCartDatas(String tableId,String userId);
	void saveCartDatas(String tableId,int hashCode,Map<String,List<CartDto>> cartDatas);
	void removeKey(String key);
}
