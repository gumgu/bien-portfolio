package com.study.admin.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 문의글(Qna) DTO
 */
@Data
public class QnaDTO {

    /**
     * 문의글 ID: pk, auto_increase
     */
    @NotNull
    private int seq;

    /**
     * 문의글 작성자: 사용자
     */
    private String memberId;

    /**
     * 문의글 답변(answer) 작성자: 관리자
     */
    private String adminId;

    /**
     * 문의글 제목
     */
    @NotEmpty
    private String questionSubject;

    /**
     * 문의글 내용
     */
    @NotEmpty
    private String questionContent;

    /**
     * 문의글 답번
     */
    private String answer;

    /**
     * 문의글 등록일, 시간
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")

    private LocalDateTime date;

    /**
     * 문의글 수정일 ,시간
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updateDate;

    /**
     * 문의글 조회수
     */
    private int readCount;

    /**
     * 비밀글 등록여부
     */
    private boolean privacy;

    /**
     * 답변 등록여부
     */
    private boolean hasAnswer;

}
