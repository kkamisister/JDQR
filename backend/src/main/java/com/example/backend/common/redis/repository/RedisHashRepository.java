package com.example.backend.common.redis.repository;

import java.time.LocalDateTime;
import java.util.List;

public interface RedisHashRepository {
	void saveHashData(String key,String subKey,Object value,long ttl);
	void removeHashData(String key,String subKey);

	Object getHashData(String key,String subKey);
}
