package com.study.admin.qna.repository;

import com.study.admin.dto.BoardSearchCondition;
import com.study.admin.dto.QnaDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 문의글(Qna) Mybatis Mapper
 */
@Mapper
public interface QnaMapper {

    /**
     * 주어진 seq의 문의글을 조회합니다.
     * @param seq 조회할 문의글 seq
     * @return 조회한 문의글
     */
    QnaDTO findQnaBySeq(int seq);

    /**
     * 주어진 답변을 등록합니다.
     * @param qnaDTO 등록할 답변 정보
     */
    void saveQnaAnswer(QnaDTO qnaDTO);

    /**
     * 주어진 seq와 일치하는 문의글을 삭제합니다.
     * @param seq 삭제할 문의글
     */
    void deleteQna(int seq);

    /**
     * 주어진 검색조건과 일치하는 문의글을 조회합니다.
     * @param boardSearchCondition 검색조건
     * @param startRow 페이지 시작 seq
     * @return 문의글 목록
     */
    List<QnaDTO> findQnaList(@Param("boardSearchCondition") BoardSearchCondition boardSearchCondition,
                             @Param("startRow") int startRow);

    /**
     * 검색조건과 일치하는 문의글 목록의 갯수를 조회합니다.
     * @param boardSearchCondition 검색조건
     * @return 문의글 목록 갯수
     */
    int findQnaListCount(@Param("boardSearchCondition") BoardSearchCondition boardSearchCondition);

}
