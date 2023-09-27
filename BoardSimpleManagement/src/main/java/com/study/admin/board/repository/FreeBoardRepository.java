package com.study.admin.board.repository;

import com.study.admin.dto.BoardSearchCondition;
import com.study.admin.dto.FreeBoardDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 자유 게시판(Free Board) 리포지터리
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class FreeBoardRepository {

    /**
     * 자유 게시판(Free Board) Mapper
     */
    private final FreeBoardMapper freeBoardMapper;

    /**
     * 검색 조건에 맞는 자유게시글 목록을 조회합니다.
     *
     * @param boardSearchCondition 검색 조건
     * @param startRow             페이지의 시작 seq
     * @return 자유게시글 리스트
     */
    public List<FreeBoardDTO> findBoardList(BoardSearchCondition boardSearchCondition, int startRow) {
        log.debug("repository BoardSearch = {}", boardSearchCondition);
        return freeBoardMapper.findBoardList(boardSearchCondition, startRow);
    }

    /**
     * 검색 조건에 맞는 자유게시글 목록의 갯수를 조회합니다.
     *
     * @param boardSearchCondition 검색조건
     * @return 자유게시글 목록 갯수
     */
    public int findBoardCount(BoardSearchCondition boardSearchCondition) {
        return freeBoardMapper.findBoardCount(boardSearchCondition);
    }

    /**
     * 주어진 seq와 일치하는 게시글을 조회하고, 수정 폼을 제공합니다.
     *
     * @param seq 조회할 seq
     * @return 조회된 자유게시글
     */
    public FreeBoardDTO findBoardBySeq(int seq) {
        return freeBoardMapper.findBoardBySeq(seq);
    }

    /**
     * 새로운 자유 게시글(Free)를 생성합니다.
     *
     * @param freeBoardDTO 생성할 공지사항 정보
     * @return 생성된 공지사항
     */
    public FreeBoardDTO saveBoard(FreeBoardDTO freeBoardDTO) {
        freeBoardMapper.saveBoard(freeBoardDTO);
        return freeBoardDTO;
    }

    /**
     * 게시글을 수정합니다.
     *
     * @param freeBoardDTO 수정할 자유게시글 정보
     */
    public void modifyBoard(FreeBoardDTO freeBoardDTO) {
        freeBoardMapper.modifyBoard(freeBoardDTO);
    }

    /**
     * 주어진 seq의 자유게시글을 삭제합니다.
     *
     * @param seq 삭제할 seq
     */
    public void deleteBoard(int seq) {
        freeBoardMapper.deleteBoard(seq);
    }

}
