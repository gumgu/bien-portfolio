package com.study.board.dto;

import com.study.board.entity.Board;
import com.study.file.dto.FileDTO;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 자유 게시글(Free Board) DTO
 * - Board 엔티티를 상속합니다.
 */
@Data
public class FreeBoardDTO extends Board {

    /**
     * 첨부파일 목록
     */
    private List<FileDTO> files;

    /**
     * 삭제할 파일명 목록
     */
    private List<String> deleteFileNames;

    /**
     * 파일 유무
     */
    private boolean hasFile;

    /**
     * 업로드할 파일 변수
     * - 파일 추가 업로드에 사용되는 변수
     */
    private List<MultipartFile> addFiles;

    /**
     * 댓글 수
     */
    private Integer replyCount;


}
