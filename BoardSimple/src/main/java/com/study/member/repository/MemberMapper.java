package com.study.member.repository;

import com.study.member.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 사용자(member) MyBatis Mapper
 */
@Mapper
public interface MemberMapper {

    /**
     * 주어진 id, password와 일치하는 사용자를 조회합니다.
     *
     * @param memberId
     * @param password
     */
    MemberDTO findMember(@Param("memberId") String memberId,
                         @Param("password") String password);

    /**
     * 주어진 id와 일치하는 사용자 정보를 조회합니다.
     *
     * @param loginId
     * @return 회원정보
     */
    MemberDTO findMemberById(String loginId);

    /**
     * 주어진 회원 정보를 저장합니다.
     *
     * @param memberDTO
     */
    void saveMember(MemberDTO memberDTO);
}
