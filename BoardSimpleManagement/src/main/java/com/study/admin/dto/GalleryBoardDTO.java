package com.study.admin.dto;

import com.study.admin.entity.Board;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 갤러리 게시글(Gallery Board) DTO
 * - Board 엔티티를 상속합니다.
 */
@Data
public class GalleryBoardDTO extends Board {

    /**
     * 첫번째 파일 업로드명
     * - 갤러리 리스트에서 preview로 제공될 첫번째 사진의 파일명
     */
    private String fileName;

    /**
     * 첨부파일 목록
     */
    private List<FileDTO> files;

    /**
     * 삭제할 파일명 목록
     */
    private List<String> deleteFileNames;

    /**
     * 업로드할 파일 변수
     * - 파일 추가 업로드에 사용되는 변수
     */
    private List<MultipartFile> addFiles;

}
