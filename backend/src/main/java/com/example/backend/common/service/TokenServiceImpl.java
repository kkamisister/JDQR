package com.example.backend.common.service;

import com.example.backend.common.redis.repository.RedisHashRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService{

	private final RedisHashRepository redisHashRepository;

	@Override
	public void saveOrUpdate(String accessToken, String refreshToken, Long ttl) {
		log.warn("accessToken : {} refreshToken : {} ",accessToken, refreshToken);

		// redis의 만료시간은 refreshToken의 만료기한으로 설정
		redisHashRepository.saveHashData("jwt",accessToken,refreshToken,ttl);
	}

	@Override
	public void saveOrUpdate(String accessToken, String refreshToken) {
		log.warn("accessToken : {} refreshToken : {} ",accessToken, refreshToken);

		// redis의 만료시간은 refreshToken의 만료기한으로 설정
		redisHashRepository.saveHashData("jwt",accessToken,refreshToken,60*24*7);
	}

	@Override
	public Optional<String> findByAccessToken(String accessToken) {

		Object hashData = redisHashRepository.getHashData("jwt",accessToken);

		return Optional.of(String.valueOf(hashData));
	}

	// @Override
	// public void updateToken(String accessToken, String reissueAccessToken) {
	// 	redisHashRepository.saveHashData("jwt",accessToken,refreshToken,-1);
	// }

	@Override
	public void deleteRefreshToken(String accessToken) {
		redisHashRepository.removeHashData("jwt",accessToken);
	}
}
