package com.study.refresh.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * JWT 토큰 전달을 위한 DTO입니다.
 */
@Data
public class TokenDTO {

    @NotEmpty
    /**
     * 사용자 Id
     */
    private String memberId;

    /**
     * Access Token 저장 변수
     */
    private String accessToken;

    /**
     * Refresh Token 저장 변수
     */
    private String refreshToken;

}
