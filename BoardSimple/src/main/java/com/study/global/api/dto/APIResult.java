package com.study.global.api.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * API 요청시 답변을 담아주는 ResultDTO입니다.
 */
@Data
public class APIResult {

    /**
     * 요청에 대한 답변의 상태를 의미합니다.
     * 1. 요청 처리 실패: FAILURE (상태코드: 0)
     * 2. 요청 처리 성공: SUCCESS (상태코드: 1)
     */
    private State state = State.SUCCESS;

    /**
     * 요청 처리 실패(FAIL) 시, 실패 원인에 대해 설명합니다.
     * 1. "클라이언트 오류"
     * 2. "서버 오류"
     */
    private String message;

    /**
     * 요청 처리 성공(SUCCESS) 시, API로 요청받은 정보를 담아 제공합니다.
     * 요청 처리 실패(FAILURE) 시, ErrorDTO로 에러에 관한 정보를 전달합니다.
     */
    private Map<String, Object> result = new HashMap<>();

    public void putResult(String key, Object value) {
        result.put(key, value);
    }
}
