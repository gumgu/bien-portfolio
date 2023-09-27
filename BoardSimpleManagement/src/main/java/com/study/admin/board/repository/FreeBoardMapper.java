package com.study.admin.board.repository;

import com.study.admin.dto.BoardSearchCondition;
import com.study.admin.dto.FreeBoardDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

/**
 * 자유 게시판(Free Board) Mybatis Mapper
 */
@Mapper
public interface FreeBoardMapper {

    /**
     * 주어진 검색조건에 일치하는 자유게시글 목록을 조회합니다.
     *
     * @param boardSearchCondition 검색조건
     * @param startRow             페이지 시작 seq
     * @return 자유게시글 목록
     */
    List<FreeBoardDTO> findBoardList(@Param("boardSearchCondition") BoardSearchCondition boardSearchCondition,
                                     @Param("startRow") int startRow);

    /**
     * 검색 조건에 맞는 자유게시글 목록의 갯수를 조회합니다.
     *
     * @param boardSearchCondition 검색조건
     * @return 자유게시글 목록 갯수
     */
    int findBoardCount(@Param("boardSearchCondition") BoardSearchCondition boardSearchCondition);

    /**
     * 새로운 자유 게시글을 등록합니다.
     *
     * @param freeBoardDTO 등록할 게시글 정보
     * @return
     */
    @SelectKey(statementType = StatementType.PREPARED,
            statement = "select last_insert_id() as no",
            keyProperty = "seq",
            before = false,
            resultType = int.class)
    int saveBoard(FreeBoardDTO freeBoardDTO);

    /**
     * 주어진 seq의 자유게시글을 조회합니다.
     *
     * @param seq 조회할 자유게시글 seq
     * @return 조회한 자유게시글
     */
    FreeBoardDTO findBoardBySeq(int seq);

    /**
     * 자유게시글을 수정합니다.
     *
     * @param freeBoardDTO
     */
    void modifyBoard(FreeBoardDTO freeBoardDTO);

    /**
     * 주어진 seq의 자유게시글을 삭제합니다.
     *
     * @param seq
     */
    void deleteBoard(int seq);

}
