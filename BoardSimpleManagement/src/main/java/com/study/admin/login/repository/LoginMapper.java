package com.study.admin.login.repository;

import com.study.admin.dto.AdminDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 로그인(Login) MyBatis Mapper
 */
@Mapper
public interface LoginMapper {

    /**
     * 주어진 아이디, 비밀번호와 일치하는 관리자 정보를 조회합니다.
     * @param id 주어진 아이디
     * @param password 주어진 비밀번호
     * @return 로그인 실패 시, 빈 AdminDTO 반환
     */
    AdminDTO loginAdmin(@Param("id") String id,
                        @Param("password") String password);
}
