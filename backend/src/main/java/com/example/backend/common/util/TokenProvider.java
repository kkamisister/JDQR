package com.example.backend.common.util;

import com.example.backend.common.dto.AuthToken;
import com.example.backend.common.exception.ErrorCode;
import com.example.backend.common.exception.JDQRException;
import com.example.backend.common.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class TokenProvider {

    // 1시간
    private static final long ACCESS_TOKEN_VALIDITY_SECONDS = 1000 * 60 * 60L;
    // private static final long ACCESS_TOKEN_VALIDITY_SECONDS = 1000 * 60;
    // 일주일
    private static final long REFRESH_TOKEN_VALIDITY_SECONDS = 1000 * 60 * 60 * 24L * 7;
    // private static final long REFRESH_TOKEN_VALIDITY_SECONDS = 1000 * 60;

    private final TokenService tokenService;

    @Value("${spring.jwt.secret-key}")
    private String key;
    private SecretKey secretKey;

    @PostConstruct
    public void setSecretKey(){
        log.warn("key : {}",this.key);
        secretKey = Keys.hmacShaKeyFor(key.getBytes());
    }

    /**
     * Qr 인증을 위한 accessToken을 발급하는 메서드
     * @param subject
     * @param expiredDate
     * @return
     */
    public String generateQrAccessToken(String subject,Date expiredDate){
        return Jwts.builder()
            .claim("type","QR")
            .setSubject(subject)
            .issuedAt(new Date())
            .setExpiration(expiredDate)
            .signWith(secretKey,SignatureAlgorithm.HS256)
            .compact();
    }

    /**
     * QR 인증을 위한 refreshToken을 발급하는 메서드
     * @param subject
     * @param expiredDate
     * @param accessToken
     */
    public void generateQrRefreshToken(String subject,Date expiredDate,String accessToken){
        String refreshToken = generateQrAccessToken(subject,expiredDate);
        tokenService.saveOrUpdate(accessToken,refreshToken,20L);
    }

    public String generateAccessToken(String subject, Date expiredDate){
        return generateToken(subject,expiredDate);
    }

    public void generateRefreshToken(String subject,Date expiredDate,String accessToken){
        String refreshToken = generateToken(subject,expiredDate);
        tokenService.saveOrUpdate(accessToken,refreshToken);
    }

    private String generateToken(String subject, Date expiredDate){
        return Jwts.builder()
            .setSubject(subject)
            .issuedAt(new Date())
            .setExpiration(expiredDate)
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();
    }

    public boolean validateToken(String token){

        Claims claims = parseClaims(token);
//		log.warn("현재 토큰 : {}",token);
//		log.warn("토큰 만료시간 : {}",claims.getExpiration());
        return claims.getExpiration().after(new Date());
    }

    public Object extractTypeClaim(String token){
        Claims claims = parseClaims(token);
        return claims.get("type");
    }

    public String extractSubject(String accessToken){
        Claims claims = parseClaims(accessToken);
        return claims.getSubject();
    }

    private Claims parseClaims(String accessToken){

        return Jwts.parser().verifyWith(secretKey).build()
            .parseSignedClaims(accessToken)
            .getPayload();
    }
    public AuthToken generate(Integer userId,String accessToken){

        Long now = (new Date()).getTime();
        Date accessTokenExpiredDate = new Date(now + ACCESS_TOKEN_VALIDITY_SECONDS);
        Date refreshTokenExpiredDate = new Date(now + REFRESH_TOKEN_VALIDITY_SECONDS);

        String subject = userId.toString();

        // 기존 엔트리 제거
        tokenService.deleteRefreshToken(accessToken);
        // 새로운 토큰 발급
        String newAccessToken = generateAccessToken(subject, accessTokenExpiredDate);
        generateRefreshToken(subject,refreshTokenExpiredDate,newAccessToken);

        return AuthToken.of(newAccessToken);
    }

    public String reissueToken(String accessToken){

        log.warn("재발급 하기 전 엑세스 토큰 : {}",accessToken);
        if(StringUtils.hasText(accessToken)){

            // 오류가 발생한 경우, 다시 로그인 해야함
            // 해커가 토큰을 탈취하고 재발급을 받았다면, 내가 가진 토큰과는 다르므로 찾지 못할것임
            // 그때 예외가 발생
            String refreshToken = tokenService.findByAccessToken(accessToken)
                .orElseThrow(() -> new JDQRException(ErrorCode.TOKEN_REISSUE_FAIL));

            log.warn("발견한 리프레시 토큰 : {}",refreshToken);

            // refreshToken이 아직 유효하다면, accessToken을 재발급 받는다

            // 단, 리프레시 토큰이 Redis에 존재하여 Null이 아닐 경우에만 유효성을 검증해야한다
            if(!StringUtils.hasText(refreshToken) && validateToken(refreshToken)){
                log.warn("유효한 리프레시 토큰");
                String subject = extractSubject(refreshToken);
                AuthToken authToken = generate(Integer.parseInt(subject),accessToken);
                log.warn("재발급 된 엑세스 토큰 : {}",authToken.accessToken());
                return authToken.accessToken();
            }

        }

        return null;
    }
}
