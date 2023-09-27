package com.study.admin.board.service;

import com.study.admin.board.repository.NoticeBoardRepository;
import com.study.admin.dto.BoardSearchCondition;
import com.study.admin.dto.NoticeBoardDTO;
import com.study.admin.util.ParamUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 공지 게시판(Notice Board) 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeBoardService {

    /**
     * 공지 게시판(Notice Board) 리포지터리
     */
    private final NoticeBoardRepository noticeBoardRepository;

    /**
     * 검색 조건에 맞는 게시글을 조회합니다.
     * 날짜 기본값을 설정합니다.
     *
     * @param boardSearchCondition 검색조건
     * @return 게시글 리스트
     */
    public List<NoticeBoardDTO> findBoardList(BoardSearchCondition boardSearchCondition) {

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
        log.info("startRow = {}", startRow);

        return noticeBoardRepository.findBoardList(boardSearchCondition, startRow);
    }

    /**
     * 검색 조건에 맞는 게시글 갯수를 조회합니다.
     * - 검색조건이 null인 경우 default값을 설정합니다.
     * @param boardSearchCondition 검색조건
     * @return 게시글 수
     */
    public int findBoardCount(BoardSearchCondition boardSearchCondition) {

        // 날짜 검색 시작일이 null인 경우, default 값을 설정합니다. (한달 전 금일)
        if (ParamUtil.isNull(boardSearchCondition.getFromDate())) {
            boardSearchCondition.setDefaultFromDate();
        }

        // 날짜 검색 종료일이 null인 경우, default 값을 설정합니다. (익일)
        if (ParamUtil.isNull(boardSearchCondition.getToDate())) {
            boardSearchCondition.setDefaultToDate();
        }

        return noticeBoardRepository.findBoardCount(boardSearchCondition);
    }

    /**
     * 주어진 갯수 만큼의 알림글(공지사항)을 가져옵니다.
     * @param size 가져올 알림글 갯수
     * @return 알림글(공지사항) 리스트
     */
    public List<NoticeBoardDTO> getAlertList(int size) {
        return noticeBoardRepository.getAlertList(size);
    }


    /**
     * 새로운 공지사항을 저장합니다.
     *
     * @param noticeBoardDTO 저장할 공지사항 정보
     * @return 생성된 공지사항
     */public NoticeBoardDTO saveBoard(NoticeBoardDTO noticeBoardDTO) {
        return noticeBoardRepository.saveBoard(noticeBoardDTO);
    }

    /**
     * 주어진 seq와 일치하는 공지사항을 조회하고, 수정 폼을 제공합니다.
     * @param seq 조회할 공지사항 번호
     * @return 조회한 공지사항
     */
    public NoticeBoardDTO findBoardBySeq(int seq) {
        return noticeBoardRepository.findBoardBySeq(seq);
    }

    /**
     * 공지사항을 수정합니다.
     * @param noticeBoardDTO 수정할 공지사항 정보
     */
    public void modifyBoard(NoticeBoardDTO noticeBoardDTO) {
        noticeBoardRepository.modifyBoard(noticeBoardDTO);
    }

    /**
     * 주어진 seq의 공지사항을 삭제합니다.
     * @param seq 삭제할 seq
     */
    public void deleteBoard(int seq) {
        noticeBoardRepository.deleteBoard(seq);
    }


}
