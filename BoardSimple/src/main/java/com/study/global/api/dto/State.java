package com.study.global.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 요청 응답의 상태를 정의하는 Enum 입니다.
 */
@Getter
@AllArgsConstructor
public enum State {

    FAILURE(0), // 요청 응답 실패
    SUCCESS(1); // 요청 응답 성공

    private final int statueCode; // 상태 코드


}
