package com.study.admin.login.repository;

import com.study.admin.dto.AdminDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 로그인(Login) 리포지터리
 */
@Repository
@RequiredArgsConstructor
public class LoginRepository {

    /**
     * 로그인(Login) Mapper
     */
    private final LoginMapper loginMapper;

    /**
     * 주어진 아이디, 비밀번호와 일치하는 관리자 정보를 조회합니다.
     * @param id 주어진 아이디
     * @param password 주어진 비밀번호
     * @return 로그인 실패 시, 빈 AdminDTO 반환
     */
    public AdminDTO loginAdmin(String id, String password) {
        return loginMapper.loginAdmin(id, password);
    }

}
