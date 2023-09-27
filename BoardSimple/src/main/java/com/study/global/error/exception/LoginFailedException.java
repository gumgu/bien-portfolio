package com.study.global.error.exception;

import com.study.global.error.dto.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 로그인 실패 예외 클래스
 * - 아이디와 비밀번호가 회원 정보와 일치하지 않을 때 발생하는 예외를 나타냅니다.
 */
@AllArgsConstructor
@Getter
public class LoginFailedException extends RuntimeException {

    private final ErrorCode errorCode = ErrorCode.LOGIN_FAILED;

}
