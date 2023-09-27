package com.study.global.error.exception;

import com.study.global.error.dto.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 존재하지 않는 게시물 식별자 예외 클래스
 * - 해당 식별자의 게시물이 존재하지 않을 때 발생하는 예외를 나타냅니다.
 */
@AllArgsConstructor
@Getter
public class NoSuchBoardSeqException extends RuntimeException{

    private final ErrorCode errorCode = ErrorCode.NO_SUCH_BOARD_ID;

}
