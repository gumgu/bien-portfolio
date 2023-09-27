package com.study.refresh.service;

import com.study.global.error.exception.InvalidTokenException;
import com.study.global.util.JwtUtil;
import com.study.refresh.repository.RefreshRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Refresh Token 관련 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshService {

    /**
     * Refresh 토큰 리포지터리
     */
    private final RefreshRepository refreshRepository;

    /**
     * jwt Utility
     */
    private final JwtUtil jwtUtil;

    /**
     * refresh 테이블에 회원 id를 최초등록 합니다.
     * @param memberId
     */
    public void createRefresh(String memberId) {
        refreshRepository.save(memberId);
    }

    /**
     * refresh 토큰을 갱신합니다.
     * - DB에 새롭게 생성된 uuid를 저장합니다.
     * - 저장한 refresh Token을 반환합니다.
     *
     * @param memberId
     * @return 갱신된 refresh Token
     */
    public String updateToken(String memberId) {

        UUID uuid = UUID.randomUUID();
        refreshRepository.update(memberId, uuid); // db에 uuid 갱신
        return jwtUtil.createRefreshToken(uuid); // 생성한 토큰 반환
    }

    /**
     * 전달받은 id와 mapping된 Refresh Token을 삭제합니다.
     *
     * @param memberId
     * @return
     */
    public void deleteByMemberId(String memberId) {
        refreshRepository.delete(memberId);
    }

    /**
     * 전달받은 id와 Refresh token의 uuid가 일치하는지 확인합니다.
     * @param memberId
     * @param refreshToken
     * @return uuid와 id의 일치여부
     */
    public void validateRefreshTokenAgainstDb(String memberId, String refreshToken) {
        String uuid = jwtUtil.extractUuid(refreshToken);
        String storeUuid = refreshRepository.findByMemberId(memberId);
        log.info("uuid = {}", uuid);
        log.info("storeUuid = {}", storeUuid);

        if (storeUuid == null || !storeUuid.equals(uuid)) {
            throw new InvalidTokenException();
        }
    }
}
