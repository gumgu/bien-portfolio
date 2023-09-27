package com.study.admin.board.repository;

import com.study.admin.dto.BoardSearchCondition;
import com.study.admin.dto.NoticeBoardDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

/**
 * 공지 게시판(Notice Board) Mybatis Mapper
 */
@Mapper
public interface NoticeBoardMapper {

    /**
     * 주어진 검색조건에 일치하는 공지사항 목록을 조회합니다.
     *
     * @param boardSearchCondition 검색조건
     * @param startRow             페이지 시작 seq
     * @return 공지사항 목록
     */
    List<NoticeBoardDTO> findBoardList(@Param("boardSearchCondition") BoardSearchCondition boardSearchCondition,
                                       @Param("startRow") int startRow);

    /**
     * 검색 조건에 맞는 공지사항 목록의 갯수를 조회합니다.
     *
     * @param boardSearchCondition 검색조건
     * @return 공지사항 목록 갯수
     */
    int findBoardCount(@Param("boardSearchCondition") BoardSearchCondition boardSearchCondition);

    /**
     * 주어진 갯수 만큼의 알림글(공지사항)을 가져옵니다.
     *
     * @param size 가져올 알림글 갯수
     * @return 알림글(공지사항) 리스트
     */
    List<NoticeBoardDTO> getAlertList(int size);

    /**
     * 새로운 공지사항을 등록합니다.
     *
     * @param noticeBoardDTO 등록할 게시글 정보
     * @return
     */
    @SelectKey(statementType = StatementType.PREPARED,
            statement = "select last_insert_id() as no",
            keyProperty = "seq",
            before = false,
            resultType = int.class)
    int saveBoard(NoticeBoardDTO noticeBoardDTO);

    /**
     * 주어진 seq의 공지사항을 조회합니다.
     *
     * @param seq 조회할 공지사항 seq
     * @return 조회한 공지사항
     */
    NoticeBoardDTO findBoardBySeq(int seq);

    /**
     * 공지사항을 수정합니다.
     *
     * @param noticeBoardDTO
     */
    void modifyBoard(NoticeBoardDTO noticeBoardDTO);

    /**
     * 주어진 seq의 공지사항을 삭제합니다.
     *
     * @param seq
     */
    void deleteBoard(int seq);

}
