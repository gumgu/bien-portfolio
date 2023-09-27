package com.study.qna.service;

import com.study.board.dto.BoardSearchCondition;
import com.study.global.util.ParamUtil;
import com.study.qna.dto.QnaDTO;
import com.study.qna.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 문의글(Qna) 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class QnaService {

    /**
     * 문의글(Qna) 리포지터리
     */
    private final QnaRepository qnaRepository;


    /**
     * 검색 조건에 맞는 문의글을 조회합니다.
     * - 날짜 기본값을 설정합니다.
     *
     * @param boardSearchCondition 검색조건
     * @return 문의글 리스트
     */
    public List<QnaDTO> findQnaList(BoardSearchCondition boardSearchCondition) {

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

        return qnaRepository.findQnaList(boardSearchCondition, startRow);
    }


    /**
     * 검색 조건에 맞는 문의글 갯수를 조회합니다.
     * - 검색조건이 null인 경우 default값을 설정합니다.
     *
     * @param boardSearchCondition 검색조건
     * @return 문의글 수
     */
    public int findQnaListCount(BoardSearchCondition boardSearchCondition) {

        // 날짜 검색 시작일이 null인 경우, default 값을 설정합니다. (한달 전 금일)
        if (ParamUtil.isNull(boardSearchCondition.getFromDate())) {
            boardSearchCondition.setDefaultFromDate();
        }

        // 날짜 검색 종료일이 null인 경우, default 값을 설정합니다. (익일)
        if (ParamUtil.isNull(boardSearchCondition.getToDate())) {
            boardSearchCondition.setDefaultToDate();
        }

        return qnaRepository.findQnaListCount(boardSearchCondition);
    }

    /**
     * 새로운 문의글 등록합니다.
     *
     * @param qnaDTO 등록할 문의글 정보
     */
    public QnaDTO saveQna(QnaDTO qnaDTO) {
        qnaRepository.saveQna(qnaDTO);
        return qnaDTO;
    }

    /**
     * 주어진 seq와 일치하는 문의글을 조회합니다.
     *
     * @param seq 조회할 문의글 번호
     * @return 조회한 문의글
     */
    public QnaDTO findQnaBySeq(int seq) {
        return qnaRepository.findQnaBySeq(seq);
    }

    /**
     * 주어진 seq와 일치하는 문의글을 삭제합니다.
     *
     * @param seq 삭제할 문의글
     */
    public void deleteQna(int seq) {
        qnaRepository.deleteQna(seq);
    }

    /**
     * 주어진 문의글의 조회수를 1 증가시킵니다.
     *
     * @param seq 업데이트할 게시글 번호
     */
    public void updateReadCount(int seq) {
        qnaRepository.updateReadCount(seq);
    }

    /**
     * 주어진 seq의 문의글을 수정합니다.
     *
     * @param paramQna 수정할 문의글 정보
     */
    public void updateQna(QnaDTO paramQna) {
        qnaRepository.updateQna(paramQna);
    }
}
