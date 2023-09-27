package com.study.board.controller;

import com.study.board.dto.BoardSearchCondition;
import com.study.board.entity.Board;
import com.study.board.entity.Category;
import com.study.board.service.NoticeBoardService;
import com.study.global.api.dto.APIResult;
import com.study.global.api.dto.State;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 공지사항(Notice) 게시글 컨트롤러
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3030")
@RequestMapping("/api/board")
public class NoticeBoardController {

    /**
     * 공지사항(Notice) 게시글 서비스
     */
    private final NoticeBoardService noticeBoardService;

    /**
     * 주어진 검색 조건에 맞춰 공지사항 리스트를 조회합니다.
     *
     * @param boardSearch 검색조건
     * @return 공지사항 리스트
     */
    @GetMapping("/notices")
    public APIResult getNotices(@ModelAttribute("boardSearchCondition") BoardSearchCondition boardSearch) {

        log.info("NoticeController boardSearch = {}", boardSearch);

        // 게시판의 종류를 지정합니다.
        boardSearch.setType("N");

        // 게시판의 카테고리를 조회합니다.
        List<Category> categoryList = noticeBoardService.getCategoryList("N");

        // 검색 조건에 맞는 게시글을 조회합니다.
        List<Board> boardList = noticeBoardService.findNoticeBoardList(boardSearch);
        log.info("controller noticeList = {}", boardList);

        // 검색 조건에 맞는 게시글 수를 조회합니다.
        int totalListCount = noticeBoardService.findNoticeBoardListCount(boardSearch);
        boardSearch.setTotalListCount(totalListCount);

        // 알림글을 가져옵니다.
        List<Board> alertList = noticeBoardService.findAlertList(5);

        APIResult apiResult = new APIResult();
        apiResult.setState(State.SUCCESS);
        apiResult.putResult("categoryList", categoryList);
        apiResult.putResult("boardList", boardList);
        apiResult.putResult("alertList", alertList);
        apiResult.putResult("totalListCount", totalListCount);
        return apiResult;
    }

    /**
     * 주어진 seq와 일치하는 공지사항을 조회합니다.
     *
     * @param seq
     * @return
     */
    @GetMapping("/notice/{seq}")
    public APIResult getNotice(@PathVariable int seq) {

        Board board = noticeBoardService.findNoticeBySeq(seq);

        // 조회수 증가.
        noticeBoardService.updateReadCount(seq);

        APIResult apiResult = new APIResult();
        apiResult.putResult("board", board);
        return apiResult;
    }

    /**
     * 메인 페이지에 출력될 최신 공지사항을 조회합니다.
     *
     * @param count 공지사항 리스트 사이즈
     * @return 공지사항 리스트
     */
    @GetMapping("/notices/{count}")
    public APIResult getMainNotices(@PathVariable Integer count) {
        log.info("count = {}", count);

        BoardSearchCondition boardSearch = new BoardSearchCondition();

        // 게시판의 종류를 지정합니다.
        boardSearch.setType("N");
        boardSearch.setPageSize(count);

        // 검색 조건에 맞는 게시글 리스트를 조회합니다.
        List<Board> boardList = noticeBoardService.findNoticeBoardList(boardSearch);
        log.info("controller noticeList = {}", boardList);

        // 알림글을 가져옵니다.
        List<Board> alertList = noticeBoardService.findAlertList(1);

        APIResult apiResult = new APIResult();
        apiResult.setState(State.SUCCESS);
        apiResult.putResult("boardList", boardList);
        apiResult.putResult("alertList", alertList);
        return apiResult;
    }


}
