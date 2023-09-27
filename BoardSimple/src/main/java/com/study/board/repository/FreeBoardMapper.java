package com.study.board.repository;

import com.study.board.dto.BoardSearchCondition;
import com.study.board.dto.FreeBoardDTO;
import com.study.board.entity.Board;
import com.study.board.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

/**
 * 자유 게시글(Free Board) Mybatis Mapper
 */
@Mapper
public interface FreeBoardMapper {

    /**
     * 새로운 게시글을 등록합니다.
     *
     * @param board 등록할 게시글 정보
     * @return
     */
    @SelectKey(statementType = StatementType.PREPARED,
            statement = "select last_insert_id() as no",
            keyProperty = "seq",
            before = false,
            resultType = int.class)
    int saveBoard(Board board);

    /**
     * 주어진 seq의 게시글을 조회합니다.
     *
     * @param seq 조회할 게시글 seq
     * @return 조회한 게시글
     */
    FreeBoardDTO findBoardBySeq(int seq);

    /**
     * 게시글을 수정합니다.
     *
     * @param board 게시글 정보
     */
    void modifyBoard(FreeBoardDTO board);

    /**
     * 주어진 seq의 게시글을 삭제합니다.
     *
     * @param seq 주어진 게시글 식별자
     */
    void deleteBoard(int seq);

    /**
     * 주어진 검색조건에 일치하는 게시글 목록을 조회합니다.
     *
     * @param boardSearchCondition 검색조건
     * @param startRow             페이지 시작 seq
     * @return 게시글 목록
     */
    List<Board> findBoardList(@Param("boardSearchCondition") BoardSearchCondition boardSearchCondition,
                              @Param("startRow") int startRow);

    /**
     * 검색 조건에 맞는 게시글 목록의 갯수를 조회합니다.
     *
     * @param boardSearchCondition 검색조건
     * @return 게시글 목록 갯수
     */
    int findBoardCount(@Param("boardSearchCondition") BoardSearchCondition boardSearchCondition);


    /**
     * 주어진 게시글의 조회수를 1 증가시킵니다.
     *
     * @param seq 주어진 공지사항 seq
     */
    void updateReadCount(int seq);

    /**
     * 타입(게시글 종류)에 포함된 카테고리 리스트를 조회합니다.
     *
     * @param type (게시글 종류)
     * @return 카테고리 리스트
     */
    List<Category> getCategoryList(String type);

}
