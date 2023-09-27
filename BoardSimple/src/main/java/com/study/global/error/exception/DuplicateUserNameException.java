package com.study.global.error.exception;

import com.study.global.error.dto.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 아이디 중복 예외 클래스
 * - 회원가입시 아이디가 중복되는 경우 발생하는 예외를 나타냅니다.
 */
@AllArgsConstructor
@Getter
public class DuplicateUserNameException extends RuntimeException{

    private final ErrorCode errorCode = ErrorCode.LOGIN_FAILED;

}
