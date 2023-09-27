package com.study.board.entity;

import lombok.Data;

/**
 * 카테고리(Category) Entity Class
 * - 게시글이 가지는 타입을 정의합니다.
 * - 각 타입별로 가지는 카테고리 종류를 정의합니다.
 */
@Data
public class Category {

    /**
     * 카테고리 ID(카테고리명): pk
     */
    private String id;

    /**
     * 게시글의 한글 카테고리명
     */
    private String categoryName;

    /**
     * 게시글의 타입
     */
    private String type;

}
