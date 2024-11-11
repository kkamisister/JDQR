package com.example.backend.common.login.service;

import org.springframework.stereotype.Service;

import com.example.backend.common.dto.AuthToken;
import com.example.backend.common.exception.ErrorCode;
import com.example.backend.common.exception.JDQRException;
import com.example.backend.common.login.dto.LoginInfo;
import com.example.backend.common.login.dto.LoginRequestDto;
import com.example.backend.common.redis.repository.RedisHashRepository;
import com.example.backend.common.util.TokenProvider;
import com.example.backend.owner.entity.Owner;
import com.example.backend.owner.repository.OwnerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

	private final OwnerRepository ownerRepository;
	private final TokenProvider tokenProvider;

	public LoginInfo login(LoginRequestDto loginRequestDto){
		log.warn("code : {}",loginRequestDto);
		Owner owner = ownerRepository.findByCode(loginRequestDto.getCode())
			.orElseThrow(() -> new JDQRException(ErrorCode.USER_NOT_FOUND));

		AuthToken authToken = tokenProvider.generate(owner.getId());
		String nickName = owner.getName();

		LoginInfo loginInfo = LoginInfo.of(authToken, nickName);
		return loginInfo;
	}
}
