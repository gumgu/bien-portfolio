package com.study.global.util;

import com.study.global.error.exception.InvalidTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

/**
 * JWT(JSON Web Token) UTIL
 * - JWT와 관련된 기능을 제공합니다.
 */
@Component
@Slf4j
public class JwtUtil {

    /**
     * jwt 헤더명
     */
    private static final String AUTHORIZATION_HEADER = "Authorization";

    /**
     * Jwt secretKey
     */
    @Value("${jwt.secret}")
    private String secretKey;

    /**
     * accessToken 만료 시간 설정
     */
    private Long accessTokenValidTime = 60 * 1000L; // 60초

    /**
     * refreshToken 만료 시간 설정
     */
    private Long refreshTokenValidTime = 2 * 7 * 24 * 60 * 60 * 1000L; // 2주

    /**
     * Access Token을 생성하는 메서드입니다.
     *
     * @param memberId 토큰에 담을 회원 ID
     * @return 생성된 Access Token
     */
    public String createAccessToken(String memberId) {
        Claims claims = Jwts.claims();
        claims.put("memberId", memberId);
        claims.put("type", "accessToken");

        // Access Token 발행
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * Refresh Token을 생성하는 메서드입니다.
     *
     * @param uuid 사용자 별 uuid
     * @return 생성된 Refresh Token
     */
    public String createRefreshToken(UUID uuid) {
        Claims claims = Jwts.claims();
        claims.put("uuid", uuid);
        claims.put("type", "refreshToken");

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * 토큰에서 Claims를 추출합니다.
     *
     * @param token
     * @return Claims
     */
    public Claims getClaims(String token) throws RuntimeException {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 주어진 Access JWT 토큰에서 사용자 아이디를 추출하는 메서드입니다.
     *
     * @param token 추출할 access 토큰
     * @return 추출된 사용자 아이디
     */
    public String extractMemberId(String token) {
        return getClaims(token)
                .get("memberId")
                .toString();
    }

    /**
     * 주어진 Refresh JWT 토큰에서 uuid를 추출하는 메서드 입니다.
     *
     * @param token 주어진 refresh 토큰
     * @return 추출된 uuid
     */
    public String extractUuid(String token) {
        return getClaims(token)
                .get("uuid")
                .toString();
    }

    public void validateToken(String token) {
        try {
            getClaims(token);
        } catch(ExpiredJwtException ex) {
            ex.printStackTrace();
            throw ex;
        } catch(MalformedJwtException | UnsupportedJwtException |
                IllegalArgumentException | SignatureException ex) {
            ex.printStackTrace();
            throw new InvalidTokenException();
        }
    }

    /**
     * 토큰의 적합성 여부를 평가합니다.
     * - 주의!) ExpiredJwtException 예외
     *
     * @param token 확인할 토큰
     * @return boolean 적합성 판단 결과
     */
    public boolean isValidToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
            e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
            e.printStackTrace();
        } catch (SignatureException e) {
            log.info("JWT 서명이 옳지 않습니다.");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 토큰의 유효기간 만료 여부를 확인합니다.
     *
     * @param token
     * @return boolean 토큰 유효기간 만료 여부
     */
    public boolean isExpiredToken(String token) {
        try {
            getClaims(token);
            return false;
        } catch (ExpiredJwtException ex) {
            log.info("util: JWT 토큰이 만료되었습니다.");
            ex.printStackTrace();
        }
        return true;
    }

    /**
     * jwt 토큰 전달 형식으로 HTTP 요청이 온 경우, 토큰을 추출합니다.
     *
     * @param request 요청
     * @return 토큰 추출
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        log.info("bearerToken = {}", bearerToken);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
