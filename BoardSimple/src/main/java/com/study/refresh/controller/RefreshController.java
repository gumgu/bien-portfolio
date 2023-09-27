package com.study.refresh.controller;

import com.study.global.api.dto.APIResult;
import com.study.global.api.dto.State;
import com.study.global.error.exception.InvalidTokenException;
import com.study.global.util.JwtUtil;
import com.study.refresh.service.RefreshService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * refresh 토큰 컨트롤러
 * - Access Token이 만료된 경우 클라이언트에게 403 Error("Access Token Expired")를 반환합니다.
 * - Access Token 갱신을 위해 보낸 요청을 처리하기 위한 컨트롤러 입니다.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3030/", allowedHeaders = "UseRefreshToken")
@RestControllerAdvice
@RequestMapping("/api")
public class RefreshController {

    /**
     * Refresh 토큰 CRUD 관련 서비스
     */
    private final RefreshService refreshService;

    /**
     * jwt Utility
     */
    private final JwtUtil jwtUtil;

    @PostMapping("/refresh")
    public ResponseEntity<APIResult> validateRefreshToken(@RequestBody String id,
                                                          HttpServletRequest request) throws InvalidTokenException {
        log.info("id={}", id);

        String refreshToken = jwtUtil.resolveToken(request);
        String accessToken = null;

        try{
            // 토큰 유효성 검증
            jwtUtil.validateToken(refreshToken);
            // DB 데이터와 일치 여부 확인
            refreshService.validateRefreshTokenAgainstDb(id, refreshToken);
        } catch(InvalidTokenException | ExpiredJwtException ex) {
            log.info("refresh 토큰 검증 실패");
            ex.printStackTrace();
            refreshService.deleteByMemberId(id);
            throw ex;
        }

        // 토큰 유효 & db와 일치
        log.info("refresh 토큰 정보 디비와 일치");

        accessToken = jwtUtil.createAccessToken(id);
        log.info("생성한 accessToken = {}", accessToken);

        APIResult apiResult = new APIResult();
        apiResult.putResult("accessToken", accessToken);
        apiResult.setState(State.SUCCESS);

        return ResponseEntity.ok(apiResult);

    }


}
