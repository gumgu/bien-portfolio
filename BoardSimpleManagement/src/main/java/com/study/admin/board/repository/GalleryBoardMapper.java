package com.study.admin.board.repository;

import com.study.admin.dto.BoardSearchCondition;
import com.study.admin.dto.GalleryBoardDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

/**
 * 갤러리 게시글(Gallery Board) Mybatis Mapper
 */
@Mapper
public interface GalleryBoardMapper {

    /**
     * 주어진 검색조건에 일치하는 게시글 목록을 조회합니다.
     *
     * @param boardSearchCondition 검색조건
     * @param startRow             페이지 시작 seq
     * @return 게시글 목록
     */
    List<GalleryBoardDTO> findBoardList(@Param("boardSearchCondition") BoardSearchCondition boardSearchCondition,
                                        @Param("startRow") int startRow);

    /**
     * 검색 조건에 맞는 게시글 목록의 갯수를 조회합니다.
     *
     * @param boardSearchCondition 검색조건
     * @return 게시글 목록 갯수
     */
    int findBoardCount(@Param("boardSearchCondition") BoardSearchCondition boardSearchCondition);

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
    int saveBoard(GalleryBoardDTO board);

    /**
     * 주어진 seq의 게시글을 조회합니다.
     *
     * @param seq 조회할 게시글 seq
     * @return 조회한 게시글
     */
    GalleryBoardDTO findBoardBySeq(int seq);

    /**
     * 게시글을 수정합니다.
     *
     * @param board 게시글 정보
     */
    void modifyBoard(GalleryBoardDTO board);

    /**
     * 주어진 seq의 게시글을 삭제합니다.
     *
     * @param seq 주어진 게시글 식별자
     */
    void deleteBoard(int seq);

}
