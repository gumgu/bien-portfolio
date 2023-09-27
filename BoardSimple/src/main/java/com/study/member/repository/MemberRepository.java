package com.study.member.repository;

import com.study.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 사용자(member)리포지터리
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberRepository {

    /**
     * 사용자(member) MyBatis Mapper
     */
    private final MemberMapper memberMapper;

    /**
     * 아이디, 비밀번호가 모두 일치하는 사용자를 조회합니다.
     *
     * @param memberId
     * @param password
     * @return Optional<사용자 정보>
     */
    public Optional findMember(String memberId, String password) {
        return Optional.ofNullable(memberMapper.findMember(memberId, password));
    }

    /**
     * 주어진 아이디와 일치하는 사용자를 조회합니다.
     *
     * @param loginId
     * @return Optional<사용자 정보>
     */
    public Optional findMemberById(String loginId) {
        return Optional.ofNullable(memberMapper.findMemberById(loginId));
    }

    /**
     * 주어진 사용자 정보를 등록합니다.
     * @param memberDTO
     */
    public void saveMember(MemberDTO memberDTO) {
        memberMapper.saveMember(memberDTO);
    }
}
