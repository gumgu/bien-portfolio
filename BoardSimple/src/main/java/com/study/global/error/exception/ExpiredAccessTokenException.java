package com.study.global.error.exception;

import com.study.global.error.dto.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 만료된 Access Token 예외 클래스
 * - Access Token의 유효기간이 만료된 경우 발생하는 예외를 나타냅니다.
 * - HTTP status code 403과 함께 전송됩니다.
 * - (이후 클라이언트측에서 refresh 토큰을 전송하도록 합니다.)
 */
@AllArgsConstructor
@Getter
public class ExpiredAccessTokenException extends RuntimeException {

    private final ErrorCode errorCode = ErrorCode.EXPIRED_ACCESS_TOKEN;

}
