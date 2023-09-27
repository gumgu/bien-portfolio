package com.study.admin.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 댓글(Reply) DTO
 */
@Data
public class ReplyDTO {
    /**
     * 댓글 ID: pk, auto_increase
     */
    @NotNull
    int seq;

    /**
     * 게시글 ID: 댓글이 작성된 게시글 번호
     */
    @NotNull
    int boardSeq;

    /**
     * 댓글 작성자: 사용자
     */
    String memberId;

    /**
     * 댓글 작성자: 관리자
     */
    String adminId;

    /**
     * 댓글 내용
     */
    @NotEmpty
    String content;

    /**
     * 댓글 등록일, 시간
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime date;

    /**
     * 댓글 수정일, 시간
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime updateDate;

}
