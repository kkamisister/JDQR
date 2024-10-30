package com.example.backend.common.redis.repository;

import java.util.List;

import com.example.backend.order.dto.CartRequest.ProductInfo;

public interface RedisHashRepository {
	void saveHashData(String key,String subKey,Object value,long ttl);
	void removeHashData(String key,String subKey);

	Object getHashData(String key,String subKey);

	List<ProductInfo> getCartDatas(String tableId);
	void saveCartDatas(String tableId,List<ProductInfo> cartDatas);
}
