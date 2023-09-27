package com.study.admin.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 첨부파일(File) DTO
 */
@Data
public class FileDTO {

    /**
     * 파일 ID: pk, auto_increase
     */
    @NotNull
    int seq;

    /**
     * 첨부파일이 포함된 게시글 ID
     */
    int boardSeq;

    /**
     * 사용자가 업로드한 첨부파일의 원본명
     */
    String originName;

    /**
     * 서버 저장용 파일명 (중복 예방)
     */
    String fileName;

    /**
     * 파일 업로드 경로
     */
    String path;

    /**
     * 파일 업로드 날짜
     */
    String date;

}
