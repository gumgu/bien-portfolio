package com.study.admin.qna.service;

import com.study.admin.dto.BoardSearchCondition;
import com.study.admin.dto.QnaDTO;
import com.study.admin.qna.repository.QnaRepository;
import com.study.admin.util.ParamUtil;
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
     * @param boardSearchCondition 검색조건
     * @return 문의글 리스트
     */
    public List<QnaDTO> findQnaList(BoardSearchCondition boardSearchCondition)
    {
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
        log.debug("startRow = {}", startRow);

        return qnaRepository.findQnaList(boardSearchCondition, startRow);
    }


    /**
     * 검색 조건에 맞는 문의글 갯수를 조회합니다.
     * - 검색조건이 null인 경우 default값을 설정합니다.
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
     * 주어진 seq와 일치하는 문의글을 조회합니다.
     * @param seq 조회할 문의글 번호
     * @return 조회한 문의글
     */
    public QnaDTO findQnaBySeq(int seq) {
        return qnaRepository.findQnaBySeq(seq);
    }


    /**
     * 답변을 등록합니다.
     * @param qnaDTO 등록할 답변 정보
     */
    public void saveQnaAnswer(QnaDTO qnaDTO) {
        qnaRepository.saveQnaAnswer(qnaDTO);
    }

    /**
     * 주어진 seq와 일치하는 문의글을 삭제합니다.
     * @param seq 삭제할 문의글
     */
    public void deleteQna(int seq) {
        qnaRepository.deleteQna(seq);
    }

}
