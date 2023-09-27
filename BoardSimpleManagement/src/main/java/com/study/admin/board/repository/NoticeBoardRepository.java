package com.study.admin.board.repository;

import com.study.admin.dto.BoardSearchCondition;
import com.study.admin.dto.NoticeBoardDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 공지 게시판(Notice Board) 리포지터리
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class NoticeBoardRepository {

    /**
     * 공지 게시판(Notice Board) Mapper
     */
    private final NoticeBoardMapper noticeBoardMapper;


    /**
     * 검색 조건에 맞는 공지사항 목록을 조회합니다.
     * @param boardSearchCondition 검색 조건
     * @param startRow 페이지의 시작 seq
     * @return 공지사항 리스트
     */
    public List<NoticeBoardDTO> findBoardList(BoardSearchCondition boardSearchCondition, int startRow) {
        log.info("repository BoardSearch = {}", boardSearchCondition);
        return noticeBoardMapper.findBoardList(boardSearchCondition, startRow);
    }

    /**
     * 검색 조건에 맞는 공지사항 목록의 갯수를 조회합니다.
     * @param boardSearchCondition 검색조건
     * @return 공지사항 목록 갯수
     */
    public int findBoardCount(BoardSearchCondition boardSearchCondition) {
        return noticeBoardMapper.findBoardCount(boardSearchCondition);
    }

    /**
     * 주어진 갯수 만큼의 알림글(공지사항)을 가져옵니다.
     * @param size 가져올 알림글 갯수
     * @return 알림글(공지사항) 리스트
     */
    public List<NoticeBoardDTO> getAlertList(int size) {
        return noticeBoardMapper.getAlertList(size);
    }


    /**
     * 새로운 공지사항(Notice)를 생성합니다.
     *
     * @param noticeBoardDTO 생성할 공지사항 정보
     * @return 생성된 공지사항
     */
    public NoticeBoardDTO saveBoard(NoticeBoardDTO noticeBoardDTO) {
        noticeBoardMapper.saveBoard(noticeBoardDTO);
        return noticeBoardDTO;
    }

    /**
     * 주어진 seq와 일치하는 공지사항을 조회하고, 수정 폼을 제공합니다.
     * @param seq 조회할 seq
     * @return 조회된 공지사항
     */
    public NoticeBoardDTO findBoardBySeq(int seq) {
        return noticeBoardMapper.findBoardBySeq(seq);
    }

    /**
     * 공지사항을 수정합니다.
     * @param noticeBoardDTO 수정할 공지사항 정보
     */
    public void modifyBoard(NoticeBoardDTO noticeBoardDTO) {
        noticeBoardMapper.modifyBoard(noticeBoardDTO);
    }

    /**
     * 주어진 seq의 공지사항을 삭제합니다.
     * @param seq 삭제할 seq
     */
    public void deleteBoard(int seq) {
        noticeBoardMapper.deleteBoard(seq);
    }


}
