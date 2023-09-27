package com.study.admin.board.repository;

import com.study.admin.dto.BoardSearchCondition;
import com.study.admin.dto.GalleryBoardDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 갤러리 게시글(Gallery Board) 리포지터리
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class GalleryBoardRepository {

    /**
     * 갤러리 게시글 (Gallery Board) Mapper
     */
    private final GalleryBoardMapper galleryBoardMapper;


    /**
     * 검색 조건에 맞는 게시글 목록을 조회합니다.
     *
     * @param boardSearchCondition 검색 조건
     * @param startRow             페이지의 시작 seq
     * @return 갤러리 게시글 리스트
     */
    public List<GalleryBoardDTO> findBoardList(BoardSearchCondition boardSearchCondition, int startRow) {
        log.info("repository BoardSearch = {}", boardSearchCondition);
        return galleryBoardMapper.findBoardList(boardSearchCondition, startRow);
    }

    /**
     * 검색 조건에 맞는 게시글 목록의 갯수를 조회합니다.
     *
     * @param boardSearchCondition 검색조건
     * @return 자유게시글 목록 갯수
     */
    public int findBoardCount(BoardSearchCondition boardSearchCondition) {
        return galleryBoardMapper.findBoardCount(boardSearchCondition);
    }

    /**
     * 주어진 seq와 일치하는 게시글을 조회하고, 수정 폼을 제공합니다.
     *
     * @param seq 조회할 seq
     * @return 조회된 게시글
     */
    public GalleryBoardDTO findBoardBySeq(int seq) {
        return galleryBoardMapper.findBoardBySeq(seq);
    }

    /**
     * 새로운 게시글을 생성합니다.
     *
     * @param board 생성할 게시글 정보
     * @return 생성된 게시글
     */
    public GalleryBoardDTO saveBoard(GalleryBoardDTO board) {
        galleryBoardMapper.saveBoard(board);
        return board;
    }

    /**
     * 게시글을 수정합니다.
     *
     * @param board 수정할 자유게시글 정보
     */
    public void modifyBoard(GalleryBoardDTO board) {
        galleryBoardMapper.modifyBoard(board);
    }

    /**
     * 주어진 seq의 게시글을 삭제합니다.
     *
     * @param seq 삭제할 seq
     */
    public void deleteBoard(int seq) {
        galleryBoardMapper.deleteBoard(seq);
    }

}
