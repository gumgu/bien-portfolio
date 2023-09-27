package com.study.global.error.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 발생 가능한 에러를 정리하는 Enum 입니다.
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_TOKEN(401,"token invalid"),
    DUPLICATE_USERNAME(402, "중복된 아이디 입니다."),
    EXPIRED_ACCESS_TOKEN(403, "Access Token이 만료되었습니다."),
    NO_SUCH_BOARD_ID(404, "존재하지 않는 게시글 ID 입니다."),
    LOGIN_FAILED(405, "아이디와 비밀번호가 일치하지 않아 로그인에 실패했습니다."),
    EXCEPTION(400, "서버 오류 발생");

    private final int status; // 에러코드
    private final String message; // 에러 메시지

}
