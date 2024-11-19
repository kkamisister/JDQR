package com.example.backend.common.util;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.backend.common.enums.RedirectUrl;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GenerateLink {

	public static final String AUTH_PREFIX = "https://jdqr608.duckdns.org/api/v1/order/auth";
	//20분
	private final long TOKEN_VALIDITY_SECONDS = 1000 * 60 * 20L;

	private final TokenProvider tokenProvider;

	/**
	 * 각 테이블 별 고유 URL을 생성하는 메서드
	 * @param tableId
	 * @return
	 */
	public String create(String tableId){

		String tableSuffix = UUID.randomUUID().toString();

		return AUTH_PREFIX+"/"+tableId+"/"+tableSuffix;
	}

	/**
	 * 각 테이블 별 인증을 위한 리다이렉트 링크를 생성하는 메서드.
	 * 엑세스토큰의 유효기한은 20분으로 한다
	 * @param tableId
	 * @return
	 */
	public String createAuthLink(String tableId){
		Long now = (new Date()).getTime();
		Date tokenExpiredDate = new Date(now + TOKEN_VALIDITY_SECONDS);
		String accessToken = tokenProvider.generateQrAccessToken(tableId, tokenExpiredDate);
		tokenProvider.generateQrRefreshToken(tableId,tokenExpiredDate,accessToken);

		return RedirectUrl.FRONT.getExplain()+"?tableId="+tableId+"&token="+accessToken;
	}

}
