package com.study.qna.repository;

import com.study.board.dto.BoardSearchCondition;
import com.study.qna.dto.QnaDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.mapping.StatementType;

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

    /**
     * 새로운 문의글을 생성합니다.
     *
     * @param qnaDTO 생성할 문의글 정보
     */
    @SelectKey(statementType = StatementType.PREPARED,
            statement = "select last_insert_id() as no",
            keyProperty = "seq",
            before = false,
            resultType = int.class)
    QnaDTO saveQna(QnaDTO qnaDTO);


    /**
     * 주어진 문의글의 조회수를 1 증가시킵니다.
     *
     * @param seq 주어진 문의글 seq
     */
    void updateReadCount(int seq);

    /**
     * 주어진 seq의 문의글을 수정합니다.
     *
     * @param paramQna 수정할 문의글 정보
     */
    void updateQna(QnaDTO paramQna);
}
