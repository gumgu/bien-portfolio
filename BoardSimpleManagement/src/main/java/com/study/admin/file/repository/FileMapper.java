package com.study.admin.file.repository;

import com.study.admin.dto.FileDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 파일(File) Mapper
 */
@Mapper
public interface FileMapper {

    /**
     * 새로운 첨부파일을 저장합니다.
     *
     * @param fileDTO  저장할 첨부파일 정보
     * @param boardSeq 파일이 첨부된 게시글 번호
     */
    int save(@Param("fileDTO") FileDTO fileDTO,
             @Param("boardSeq") int boardSeq);

    /**
     * 주어진 게시글 번호에 첨부된 파일들을 조회합니다.
     *
     * @param boardSeq 게시글 번호
     * @return 첨부파일 리스트
     */
    List<FileDTO> findFileByBoardSeq(int boardSeq);

    /**
     * 주어진 첨부파일 번호의 첨부파일을 조회합니다.
     *
     * @param fileSeq 첨부파일 번호
     * @return 첨부파일
     */
    FileDTO findFileByFileSeq(String fileSeq);

    /**
     * 주어진 첨부파일 번호의 첨부파일을 삭제합니다.
     *
     * @param fileName 첨부파일명
     */
    int deleteFileByFileName(String fileName);

    /**
     * 주어진 게시글 번호의 첨부파일을 모두 삭제합니다.
     *
     * @param boardSeq 게시글 번호
     */
    int deleteFileByBoardSeq(int boardSeq);

}
