package com.study.admin.file.repository;

import com.study.admin.dto.FileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 파일(File) 리포지터리
 */
@Repository
@RequiredArgsConstructor
public class FileRepository {

    /**
     * 파일(File) Mapper
     */
    private final FileMapper fileMapper;

    /**
     * 새로운 첨부파일을 저장합니다.
     *
     * @param fileDTO  저장할 첨부파일 정보
     * @param boardSeq 파일이 첨부된 게시글 번호
     */
    public void saveFile(FileDTO fileDTO, int boardSeq) {
        fileMapper.save(fileDTO, boardSeq);
    }

    /**
     * 주어진 게시글 번호에 첨부된 파일들을 조회합니다.
     *
     * @param boardSeq 게시글 번호
     * @return 첨부파일 리스트
     */
    public List<FileDTO> findFileByBoardSeq(int boardSeq) {
        return fileMapper.findFileByBoardSeq(boardSeq);
    }

    /**
     * 주어진 첨부파일 번호의 첨부파일을 조회합니다.
     *
     * @param fileSeq 첨부파일 번호
     * @return 첨부파일
     */
    public FileDTO findFileByFileSeq(String fileSeq) {
        return fileMapper.findFileByFileSeq(fileSeq);
    }

    /**
     * 주어진 첨부파일 번호의 첨부파일을 삭제합니다.
     *
     * @param fileName 첨부파일명
     */
    public int deleteFileByFileName(String fileName) {
        return fileMapper.deleteFileByFileName(fileName);
    }

    /**
     * 주어진 게시글 번호의 첨부파일을 모두 삭제합니다.
     *
     * @param boardSeq 게시글 번호
     */
    public void deleteFileByBoardSeq(int boardSeq) {
        fileMapper.deleteFileByBoardSeq(boardSeq);
    }

}
