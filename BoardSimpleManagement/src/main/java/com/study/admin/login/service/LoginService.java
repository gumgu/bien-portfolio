package com.study.admin.login.service;

import com.study.admin.dto.AdminDTO;
import com.study.admin.login.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 로그인(Login) 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    /**
     * 로그인(login) CRUD 리포지터리
     */
    private final LoginRepository loginRepository;

    /**
     * 주어진 아이디, 비밀번호 정보로 로그인 기능을 수행합니다.
     *
     * @param id 주어진 아이디
     * @param password 주어진 비밀번호
     * @return 로그인 실패 시, 빈 AdminDTO 반환
     */
    public AdminDTO loginAdmin(String id, String password) {
        return loginRepository.loginAdmin(id, password);
    }

}
