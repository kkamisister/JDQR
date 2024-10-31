package com.example.backend.common.service;

import java.util.Optional;

public interface TokenService {

	void saveOrUpdate(String accessToken,String refreshToken,Long ttl);

	void saveOrUpdate(String accessToken,String refreshToken);

	Optional<String> findByAccessToken(String accessToken);

	// void updateToken(String accessToken,String refreshToken);

	void deleteRefreshToken(String accessToken);

}
