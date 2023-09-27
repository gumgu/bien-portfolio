package com.study.qna.controller;

import com.study.board.dto.BoardSearchCondition;
import com.study.global.api.dto.APIResult;
import com.study.global.api.dto.State;
import com.study.global.util.JwtUtil;
import com.study.qna.dto.QnaDTO;
import com.study.qna.service.QnaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 문의글(Qna) 컨트롤러
 */
@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3030")
@RequiredArgsConstructor
@RequestMapping("/api")
public class QnaController {

    /**
     * 문의글(Qna) 서비스
     */
    private final QnaService qnaService;

    /**
     * Jwt 관련 Utility
     */
    private final JwtUtil jwtUtil;

    /**
     * 주어진 검색 조건에 맞춰 문의글 리스트를 조회합니다.
     * - 나의 문의 내역만 조회하는 경우 이를 적용합니다.
     *
     * @param boardSearch 검색조건
     * @return 문의글 리스트
     */
    @GetMapping("/qnas")
    public APIResult list(@ModelAttribute("boardSearchCondition") BoardSearchCondition boardSearch,
                          HttpServletRequest request) {

        // "나의 문의내역만 조회" 선택 시, 토큰의 아이디를 검색 조건에 삽입합니다.
        log.info("isMyQnaOnly? = {}", boardSearch.isMyQnaOnly());
        if (boardSearch.isMyQnaOnly()) {
            String memberId = jwtUtil.extractMemberId(jwtUtil.resolveToken(request));
            boardSearch.setMyId(memberId);
        }

        // 검색 조건에 맞는 문의글을 조회합니다.
        List<QnaDTO> qnaList = qnaService.findQnaList(boardSearch);

        // 검색 조건에 맞는 문의글 수를 조회합니다.
        int totalListCount = qnaService.findQnaListCount(boardSearch);

        APIResult apiResult = new APIResult();
        apiResult.setState(State.SUCCESS);
        apiResult.putResult("qnaList", qnaList);
        apiResult.putResult("totalListCount", totalListCount);
        return apiResult;
    }

    /**
     * 메인 페이지에 출력될 최신 문의글 조회합니다.
     * @param count 문의글 리스트 사이즈
     * @return 문의글 리스트
     */
    @GetMapping("/qnas/{count}")
    public APIResult getMainNotices(@PathVariable Integer count) {

        BoardSearchCondition boardSearch = new BoardSearchCondition();

        // 조회할 게시판 숫자를 지정합니다.
        boardSearch.setPageSize(count);

        // 검색 조건에 맞는 게시글 리스트를 조회합니다.
        List<QnaDTO> qnaList = qnaService.findQnaList(boardSearch);
        log.info("controller qnaList = {}", qnaList);

        APIResult apiResult = new APIResult();
        apiResult.setState(State.SUCCESS);
        apiResult.putResult("qnaList", qnaList);
        return apiResult;
    }

    /**
     * 문의글(Qna)을 조회합니다.
     *
     * @param seq 조회할 문의글 번호
     * @return
     */
    @GetMapping("/qna/{seq}")
    public APIResult getQna(@PathVariable int seq) {

        QnaDTO qna = qnaService.findQnaBySeq(seq);

        // 조회수 증가
        qnaService.updateReadCount(seq);

        APIResult apiResult = new APIResult();
        apiResult.setState(State.SUCCESS);
        apiResult.putResult("qna", qna);
        return apiResult;
    }

    /**
     * 새로운 문의글을 등록합니다.
     *
     * @param paramQna 등록할 문의글
     * @return seq 등록된 문의글의 id
     */
    @PostMapping("/qna")
    public APIResult save(@RequestBody QnaDTO paramQna) {
        log.info("saveQna ={}", paramQna);

        QnaDTO qna = qnaService.saveQna(paramQna);
        log.info("seq={}", qna.getSeq());

        APIResult apiResult = new APIResult();
        apiResult.setState(State.SUCCESS);
        apiResult.putResult("seq", qna.getSeq());
        return apiResult;
    }

    @PostMapping("/qna/{seq}")
    public ResponseEntity<APIResult> updateQna(@RequestBody QnaDTO paramQna) {
        log.info("updateQna={}", paramQna);

        qnaService.updateQna(paramQna);

        APIResult apiResult = new APIResult();
        apiResult.setState(State.SUCCESS);
        return ResponseEntity.ok(apiResult);
    }

}
