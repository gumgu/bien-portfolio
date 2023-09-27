package com.study.admin.board.service;

import com.study.admin.board.repository.GalleryBoardRepository;
import com.study.admin.dto.BoardSearchCondition;
import com.study.admin.dto.FileDTO;
import com.study.admin.dto.GalleryBoardDTO;
import com.study.admin.file.repository.FileRepository;
import com.study.admin.util.FileUtil;
import com.study.admin.util.ParamUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 갤러리 게시글(Gallery Board) 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GalleryBoardService {

    /**
     * 갤러리 게시글 (Gallery Board) 리포지터리
     */
    private final GalleryBoardRepository galleryBoardRepository;

    /**
     * 파일(File) CRUD 리포지터리
     */
    private final FileRepository fileRepository;

    /**
     * 검색 조건에 맞는 갤러리 게시글 리스트를 조회합니다.
     * - 날짜 기본값을 설정합니다.
     *
     * @param boardSearchCondition 검색조건
     * @return 갤러리게시글 리스트
     */
    public List<GalleryBoardDTO> findBoardList(BoardSearchCondition boardSearchCondition) {

        // 날짜 검색 시작일이 null인 경우, default 값을 설정합니다. (한달 전 금일)
        if (ParamUtil.isNull(boardSearchCondition.getFromDate())) {
            boardSearchCondition.setDefaultFromDate();
        }

        // 날짜 검색 종료일이 null인 경우, default 값을 설정합니다. (익일)
        if (ParamUtil.isNull(boardSearchCondition.getToDate())) {
            boardSearchCondition.setDefaultToDate();
        }

        // startRow: 한 페이지에 출력될 가장 첫 게시글의 번호
        int startRow = (boardSearchCondition.getCurrentPage()-1) * boardSearchCondition.getPageSize();
        log.info("startRow = {}", startRow);

        return galleryBoardRepository.findBoardList(boardSearchCondition, startRow);
    }


    /**
     * 검색 조건에 맞는 갤러리 게시글 갯수를 조회합니다.
     * - 검색조건이 null인 경우 default값을 설정합니다.
     * @param boardSearchCondition 검색조건
     * @return 게시글 수
     */
    public int findBoardCount(BoardSearchCondition boardSearchCondition) {

        // 날짜 검색 시작일이 null인 경우, default 값을 설정합니다. (한달 전 금일)
        if (ParamUtil.isNull(boardSearchCondition.getFromDate())) {
            boardSearchCondition.setDefaultFromDate();
        }

        // 날짜 검색 종료일이 null인 경우, default 값을 설정합니다. (익일)
        if (ParamUtil.isNull(boardSearchCondition.getToDate())) {
            boardSearchCondition.setDefaultToDate();
        }

        return galleryBoardRepository.findBoardCount(boardSearchCondition);
    }


    /**
     * 주어진 seq와 일치하는 갤러리 게시글을 조회하고, 수정 폼을 제공합니다.
     * @param seq 조회할 게시글 번호
     * @return 조회한 게시글
     */
    public GalleryBoardDTO findBoardBySeq(int seq) {
        return galleryBoardRepository.findBoardBySeq(seq);
    }

    /**
     * 새로운 갤러리 게시글을 저장합니다.
     * - 파일을 등록합니다.
     *
     * @param galleryBoardDTO 저장할 갤러리 게시글 정보
     * @return 생성된 갤러리 게시글
     */
    public GalleryBoardDTO saveBoard(GalleryBoardDTO galleryBoardDTO) throws IOException {

        galleryBoardRepository.saveBoard(galleryBoardDTO);

        if (!ParamUtil.isNull(galleryBoardDTO.getAddFiles())) {

            for(MultipartFile file :  galleryBoardDTO.getAddFiles()) {
                log.info("파일 저장 ={}", file);
                FileDTO fileDTO = FileUtil.uploadFile(file);
                fileRepository.saveFile(fileDTO, galleryBoardDTO.getSeq());
            }

        }

        return galleryBoardDTO;
    }

    /**
     * 갤러리 게시글을 수정합니다.
     * @param galleryBoardDTO 수정할 갤러리 게시글 정보
     */
    public void modifyBoard(GalleryBoardDTO galleryBoardDTO) {
        galleryBoardRepository.modifyBoard(galleryBoardDTO);
    }

    /**
     * 주어진 seq의 갤러리 게시글을 삭제합니다.
     * @param seq 삭제할 seq
     */
    public void deleteBoard(int seq) {
        fileRepository.deleteFileByBoardSeq(seq);
        galleryBoardRepository.deleteBoard(seq);    }

}
