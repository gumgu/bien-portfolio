package com.study.qna.repository;

import com.study.board.dto.BoardSearchCondition;
import com.study.qna.dto.QnaDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 문의글(Qna) 리포지터리
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class QnaRepository {

    /**
     * 문의글(Qna) Mapper
     */
    private final QnaMapper qnaMapper;


    /**
     * 주어진 seq와 일치하는 문의글을 조회합니다.
     *
     * @param seq 조회할 문의글 번호
     * @return 조회한 문의글
     */
    public QnaDTO findQnaBySeq(int seq) {
        return qnaMapper.findQnaBySeq(seq);
    }

    /**
     * 주어진 seq와 일치하는 문의글을 삭제합니다.
     *
     * @param seq 삭제할 문의글
     */
    public void deleteQna(int seq) {
        qnaMapper.deleteQna(seq);
    }

    /**
     * 검색 조건에 맞는 문의글 목록을 조회합니다.
     *
     * @param boardSearchCondition 검색 조건
     * @param startRow             페이지의 시작 seq
     * @return 문의글 리스트
     */
    public List<QnaDTO> findQnaList(BoardSearchCondition boardSearchCondition, int startRow) {
        log.info("repository BoardSearch = {}", boardSearchCondition);
        return qnaMapper.findQnaList(boardSearchCondition, startRow);
    }


    /**
     * 검색 조건에 맞는 문의글 목록의 갯수를 조회합니다.
     *
     * @param boardSearchCondition 검색조건
     * @return 문의글 목록 갯수
     */
    public int findQnaListCount(BoardSearchCondition boardSearchCondition) {
        return qnaMapper.findQnaListCount(boardSearchCondition);
    }

    /**
     * 새로운 문의글을 등록합니다.
     *
     * @param qnaDTO 등록할 문의글 정보
     */
    public void saveQna(QnaDTO qnaDTO) {
        qnaMapper.saveQna(qnaDTO);
    }

    /**
     * 주어진 문의글의 조회수를 1 증가시킵니다.
     *
     * @param seq 주어진 seq
     */
    public void updateReadCount(int seq) {
        qnaMapper.updateReadCount(seq);
    }

    /**
     * 주어진 seq의 문의글을 수정합니다.
     *
     * @param paramQna 수정할 문의글 정보
     */
    public void updateQna(QnaDTO paramQna) {
        qnaMapper.updateQna(paramQna);
    }
}
