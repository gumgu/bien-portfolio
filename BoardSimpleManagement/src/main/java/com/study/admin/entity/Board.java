package com.study.admin.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * 게시글(Board) Entity Class
 * - 모든 타입의 게시글이 공통으로 가지는 변수들을 정의합니다.
 */
@Data
public class Board {

    /**
     * 게시글 ID: pk, auto_increase
     */
    @NotBlank(groups = UpdateCheck.class)
    private int seq;

    /**
     * 게시글 작성자: 사용자
     */
    private String memberId;

    /**
     * 게시글 작성자: 관리자
     */
    private String adminId;

    /**
     * 카테고리명
     */
    private String categoryName;

    /**
     * 카테고리 아이디
     */
    @NotBlank(groups = {SaveCheck.class, UpdateCheck.class})
    private String categoryId;

    /**
     * 게시글 제목
     */
    @NotBlank(groups = {SaveCheck.class, UpdateCheck.class})
    @Size(min = 4, max = 100, groups = {SaveCheck.class, UpdateCheck.class})
    private String subject;

    /**
     * 게시글 내용
     */
    @NotBlank(groups = {SaveCheck.class, UpdateCheck.class})
    @Size(min = 4, max = 2000, groups = {SaveCheck.class, UpdateCheck.class})
    private String content;

    /**
     * 게시글 등록일, 시간
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime date;

    /**
     * 게시글 수정일, 시간
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updateDate;

    /**
     * 게시글 조회수
     */
    private int readCount;

    public String toBoardString() {
        return "seq:" + getSeq() +
                ", subject:" + getSubject() +
                ", content:" + getContent() +
                ", categoryName:" + getCategoryName() +
                ", categoryId:" + getCategoryId();
    }

    /**
     * 갤러리 게시판 DTO에 게시글 정보를 삽입합니다.
     * @param board 게시글 정보
     */
    public void setBoard(Board board) {
        this.setSeq(board.getSeq());
        this.setMemberId(board.getMemberId());
        this.setCategoryName(board.getCategoryName());
        this.setCategoryId(board.getCategoryId());
        this.setSubject(board.getSubject());
        this.setContent(board.getContent());
        this.setDate(board.getDate());
        this.setUpdateDate(board.getUpdateDate());
        this.setReadCount(board.getReadCount());
    }
}
