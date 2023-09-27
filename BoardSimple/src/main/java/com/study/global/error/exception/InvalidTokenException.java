package com.study.global.error.exception;

import com.study.global.error.dto.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 유효하지 않은 JWT 토큰 예외 클래스
 * - 유효하지 않은 JWT가 전송된 경우 발생하는 예외를 나타냅니다.
 */
@AllArgsConstructor
@Getter
public class InvalidTokenException extends RuntimeException{

    private final ErrorCode errorCode = ErrorCode.INVALID_TOKEN;

}
