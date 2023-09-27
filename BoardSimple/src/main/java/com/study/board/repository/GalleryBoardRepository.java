package com.study.board.repository;

import com.study.board.dto.BoardSearchCondition;
import com.study.board.dto.GalleryBoardDTO;
import com.study.board.entity.Board;
import com.study.board.entity.Category;
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
    private final GalleryBoardMapper boardMapper;

    /**
     * 주어진 seq와 일치하는 게시글을 조회합니다.
     *
     * @param seq 조회할 seq
     * @return 조회된 게시글
     */
    public Board findBoardBySeq(int seq) {
        return boardMapper.findBoardBySeq(seq);
    }

    /**
     * 새로운 게시글을 생성합니다.
     *
     * @param board 생성할 게시글 정보
     * @return 생성된 게시글
     */
    public Board saveBoard(Board board) {
        boardMapper.saveBoard(board);
        return board;
    }

    /**
     * 게시글을 수정합니다.
     *
     * @param board 수정할 자유게시글 정보
     */
    public void modifyBoard(Board board) {
        boardMapper.modifyBoard(board);
    }

    /**
     * 주어진 seq의 게시글을 삭제합니다.
     *
     * @param seq 삭제할 seq
     */
    public void deleteBoard(int seq) {
        boardMapper.deleteBoard(seq);
    }

    /**
     * 검색 조건에 맞는 게시글 목록을 조회합니다.
     *
     * @param boardSearchCondition 검색 조건
     * @param startRow             페이지의 시작 seq
     * @return 자유게시글 리스트
     */
    public List<GalleryBoardDTO> findBoardList(BoardSearchCondition boardSearchCondition, int startRow) {
        log.info("repository BoardSearch = {}", boardSearchCondition);
        return boardMapper.findBoardList(boardSearchCondition, startRow);
    }

    /**
     * 검색 조건에 맞는 게시글 목록의 갯수를 조회합니다.
     *
     * @param boardSearchCondition 검색조건
     * @return 자유게시글 목록 갯수
     */
    public int findBoardListCount(BoardSearchCondition boardSearchCondition) {
        return boardMapper.findBoardCount(boardSearchCondition);
    }

    /**
     * 주어진 seq 공지사항의 조회수를 1 증가시킵니다.
     *
     * @param seq 주어진 공지사항 seq
     */
    public void updateReadCount(int seq) {
        boardMapper.updateReadCount(seq);
    }


    /**
     * 타입(게시글 종류)에 포함된 카테고리 리스트를 조회합니다.
     *
     * @param type (게시글 종류)
     * @return 카테고리 리스트
     */
    public List<Category> getCategoryList(String type) {
        return boardMapper.getCategoryList(type);
    }


}
