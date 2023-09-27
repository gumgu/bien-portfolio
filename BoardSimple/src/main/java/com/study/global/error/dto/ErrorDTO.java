package com.study.global.error.dto;

import lombok.Data;

/**
 * 에러(Exception) 정보를 표현하는 DTO입니다.
 */
@Data
public class ErrorDTO {

    private int status; // 에러 코드
    private String message; // 에러 정보

    public ErrorDTO() {}

    /**
     * ErrorCode를 이용하여 ErrorDTO를 생성하는 생성자
     *
     * @param errorCode 에러 코드와 메시지를 가진 ErrorCode 객체
     */
    public ErrorDTO(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
    }
}
