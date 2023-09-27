package com.study.board.service;

import com.study.board.dto.BoardSearchCondition;
import com.study.board.entity.Board;
import com.study.board.entity.Category;
import com.study.board.repository.NoticeBoardRepository;
import com.study.global.util.ParamUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 공지 게시판(Notice Board) 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeBoardService {

    /**
     * 공지 게시판 (Notice Board) 리포지터리
     */
    private final NoticeBoardRepository boardRepository;

    /**
     * 주어진 seq와 일치하는 공지사항을 조회합니다.
     *
     * @param seq 조회할 공지사항 번호
     * @return 조회한 공지사항
     */
    public Board findNoticeBySeq(int seq) {
        return boardRepository.findBoardBySeq(seq);
    }

    /**
     * 검색 조건에 맞는 게시글을 조회합니다.
     * 날짜 기본값을 설정합니다.
     *
     * @param boardSearchCondition 검색조건
     * @return 게시글 리스트
     */
    public List<Board> findNoticeBoardList(BoardSearchCondition boardSearchCondition) {

        // 날짜 검색 시작일이 null인 경우, default 값을 설정합니다. (한달 전 금일)
        if (ParamUtil.isNull(boardSearchCondition.getFromDate())) {
            boardSearchCondition.setDefaultFromDate();
        }

        // 날짜 검색 종료일이 null인 경우, default 값을 설정합니다. (익일)
        if (ParamUtil.isNull(boardSearchCondition.getToDate())) {
            boardSearchCondition.setDefaultToDate();
        }

        // startRow: 한 페이지에 출력될 가장 첫 게시글의 번호
        int startRow = (boardSearchCondition.getCurrentPage() - 1) * boardSearchCondition.getPageSize();
        log.info("startRow = {}", startRow);

        return boardRepository.findBoardList(boardSearchCondition, startRow);
    }

    /**
     * 검색 조건에 맞는 게시글 갯수를 조회합니다.
     * - 검색조건이 null인 경우 default값을 설정합니다.
     *
     * @param boardSearchCondition 검색조건
     * @return 게시글 수
     */
    public int findNoticeBoardListCount(BoardSearchCondition boardSearchCondition) {

        // 날짜 검색 시작일이 null인 경우, default 값을 설정합니다. (한달 전 금일)
        if (ParamUtil.isNull(boardSearchCondition.getFromDate())) {
            boardSearchCondition.setDefaultFromDate();
        }

        // 날짜 검색 종료일이 null인 경우, default 값을 설정합니다. (익일)
        if (ParamUtil.isNull(boardSearchCondition.getToDate())) {
            boardSearchCondition.setDefaultToDate();
        }

        return boardRepository.findBoardListCount(boardSearchCondition);
    }

    /**
     * 주어진 갯수 만큼의 알림글(공지사항)을 가져옵니다.
     *
     * @param size 가져올 알림글 갯수
     * @return 알림글(공지사항) 리스트
     */
    public List<Board> findAlertList(int size) {
        return boardRepository.findAlertList(size);
    }

    /**
     * 주어진 공지사항 seq의 조회수를 1 증가시킵니다.
     *
     * @param seq 업데이트할 게시글 번호
     */
    public void updateReadCount(int seq) {
        boardRepository.updateReadCount(seq);
    }

    /**
     * 카테고리 리스트를 조회합니다.
     * @param type 게시판 타입
     * @return 타입안에 포함된 카테고리 리스트
     */
    public List<Category> getCategoryList(String type) {
        return boardRepository.getCategoryList(type);
    }

}
