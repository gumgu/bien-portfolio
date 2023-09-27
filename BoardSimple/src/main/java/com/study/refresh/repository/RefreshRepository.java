package com.study.refresh.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * refresh 토큰 리포지터리
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class RefreshRepository {

    /**
     * refresh 토큰 MyBatis Mapper
     */
    private final RefreshMapper refreshMapper;

    /**
     * 회원 ID를 최초 등록합니다.
     * @param memberId
     */
    public void save(String memberId) {
        refreshMapper.save(memberId);
    }

    /**
     * refresh 토큰의 payload에 담길 uuid를 저장합니다.
     *
     * @param memberId
     * @param uuid
     */
    public void update(String memberId, UUID uuid) {
        refreshMapper.update(memberId, String.valueOf(uuid));
    }

    /**
     * 전달받은 id와 mapping된 Refresh Token을 삭제합니다.
     *
     * @param memberId
     * @return
     */
    public void delete(String memberId) {
        refreshMapper.delete(memberId);
    }

    /**
     * 전달받은 id와 mapping된 Refresh Token의 uuid를 반환합니다.
     *
     * @param memberId
     * @return uuid(RefreshToken)
     */
    public String findByMemberId(String memberId) {
        return refreshMapper.findByMemberId(memberId);
    }
}
