package com.study.member.service;

import com.study.member.dto.MemberDTO;
import com.study.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 사용자(Member) 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    /**
     * 사용자(Member) 리포지터리
     */
    private final MemberRepository memberRepository;

    /**
     * 아이디, 비밀번호 정보와 일치하는 사용자 유무를 조회합니다.
     * @param memberId
     * @param password
     * @return 일치 여부(boolean)
     */
    public boolean isValidLogin(String memberId, String password) {
        return memberRepository.findMember(memberId, password).isPresent();
    }

    /**
     * 아이디의 중복 여부를 확인합니다.
     * @param loginId 확인할 아이디
     * @return 아이디 중복 여부 (true:비중복, false:중복)
     */
    public boolean isUnique(String loginId) {
        return !memberRepository.findMemberById(loginId).isPresent();
    }

    /**
     * 사용자 정보를 저장합니다.
     * @param memberDTO 저장할 사용자 정보
     */
    public void saveMember(MemberDTO memberDTO) {
        memberRepository.saveMember(memberDTO);
    }
}
