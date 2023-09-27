package com.study.admin.file.service;

import com.study.admin.dto.FileDTO;
import com.study.admin.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 파일(File) 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    /**
     * 파일(File) 리포지터리
     */
    private final FileRepository fileRepository;

    /**
     * 주어진 게시글 번호에 첨부된 파일들을 조회합니다.
     * @param boardSeq 게시글 번호
     * @return 첨부파일 리스트
     */
    public List<FileDTO> findFileByBoardSeq(int boardSeq) {
        return fileRepository.findFileByBoardSeq(boardSeq);
    }

    /**
     * 주어진 첨부파일 번호의 첨부파일을 조회합니다.
     * @param fileSeq 첨부파일 번호
     * @return 첨부파일
     */
    public FileDTO fileFileByFileSeq(String fileSeq) {
        return fileRepository.findFileByFileSeq(fileSeq);
    }

    /**
     * 주어진 첨부파일명의 첨부파일을 삭제합니다.
     * @param fileName 첨부파일명
     */
    public int deleteFileByFileName(String fileName) {
        return fileRepository.deleteFileByFileName(fileName);
    }
}
