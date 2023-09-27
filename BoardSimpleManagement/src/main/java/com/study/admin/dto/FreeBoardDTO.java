package com.study.admin.dto;

import com.study.admin.entity.Board;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 자유 게시판(Free Board) DTO
 * - Board 엔티티 클래스를 상속합니다.
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

    public void setFiles(List<FileDTO> files) {
        this.files = files;
    }

    public void setDeleteFileNames(List<String> deleteFileNames) {
        this.deleteFileNames = deleteFileNames;
    }

    public void setHasFile(boolean hasFile) {
        this.hasFile = hasFile;
    }

    public void setAddFiles(List<MultipartFile> addFiles) {
        this.addFiles = addFiles;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }
}
