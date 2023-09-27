package com.study.refresh.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * refresh 토큰 MyBatis Mapper
 */
@Mapper
public interface RefreshMapper {

    /**
     * 회원 ID를 최초 등록합니다.
     *
     * @param memberId
     */
    void save(@Param("memberId") String memberId);

    /**
     * refresh 토큰의 payload에 담길 uuid를 갱신합니다.
     *
     * @param memberId
     * @param uuid
     */
    void update(@Param("memberId") String memberId,
                @Param("uuid") String uuid);

    /**
     * 전달받은 id와 mapping된 Refresh Token을 삭제합니다.
     *
     * @param memberId
     * @return
     */
    void delete(String memberId);

    /**
     * 전달 받은 사용자 id와 매핑된 refresh token을 반환합니다.
     *
     * @param memberId
     * @return id와 매핑된 토큰이 있는 경우 : refreshToken
     * @return id와 매핑된 토큰이 없거나, id가 없는 경우: null
     */
    String findByMemberId(String memberId);
}
