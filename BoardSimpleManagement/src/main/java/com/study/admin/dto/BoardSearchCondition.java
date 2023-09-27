package com.study.admin.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 게시글(Board) 검색 DTO
 * - 이 클래스는 게시글 검색 조건, 페이징 변수를 표현합니다.
 */
@Data
public class BoardSearchCondition {

    /**
     * 게시판의 타입 (자유:F, 공지사항:N, 갤러리:G)
     */
    private String type;

    /**
     * 등록일 검색 시작일: (default) 한달 전 금일
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String fromDate;

    /**
     * 등록일 검색 종료일: (default) 금일
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String toDate;

    /**
     * 검색어 (제목, 본문)
     */
    private String search;

    /**
     * 카테고리 검색
     */
    private String categoryId;

    /**
     * 현재 페이지 : (default) 1
     */
    private int currentPage = 1;

    /**
     * 한 페이지의 게시글 수 : (default) 10
     */
    private int pageSize = 10;

    /**
     * 조회한 게시글 수
     */
    private int totalListCount;

    /**
     * 정렬 기준: (default) 등록일시 date
     * - 등록일시 : date
     * - 분류 : categoryName
     * - 제목 : subject
     * - 조회수: readCount
     */
    private String orderStandard = "date";

    /**
     * 정렬 순차 : (default) 내림차순
     * - 내림차순: 0
     * - 오름차순: 1
     */
    private int orderSequence = 0;

    /**
     * 자신의 문의글만 조회
     */
    private boolean myQnaOnly;

    /**
     * 접속자의 id (자신의 문의글 조회용)
     */
    private String myId;

    /**
     * 검색 조건 시작일을 기본값으로 적용합니다.
     * - (default) 1년 전 금일
     */
    public void setDefaultFromDate() {
        this.fromDate = LocalDateTime.now().minusYears(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    /**
     * 검색 조건 종료일을 기본값으로 적용합니다.
     * - (default) 익일
     */
    public void setDefaultToDate() {
        this.toDate = LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

}

