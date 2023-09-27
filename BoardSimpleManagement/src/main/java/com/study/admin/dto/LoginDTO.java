package com.study.admin.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 로그인(login) 작업을 위한 DTO 입니다.
 */
@Data
public class LoginDTO {

    /**
     * 입력받은 아이디
     */
    @NotEmpty
    private String id;

    /**
     * 입력받은 비밀번호
     */
    @NotEmpty
    private String password;

}
