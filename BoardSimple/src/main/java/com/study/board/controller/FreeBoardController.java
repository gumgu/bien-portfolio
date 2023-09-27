package com.study.board.controller;

import com.study.board.dto.BoardSearchCondition;
import com.study.board.dto.FreeBoardDTO;
import com.study.board.entity.Board;
import com.study.board.entity.Category;
import com.study.board.entity.SaveBoardCheck;
import com.study.board.entity.UpdateBoardCheck;
import com.study.board.service.FreeBoardService;
import com.study.file.dto.FileDTO;
import com.study.file.service.FileService;
import com.study.global.api.dto.APIResult;
import com.study.global.api.dto.State;
import com.study.global.error.exception.NoSuchBoardSeqException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 자유(Free) 게시글 컨트롤러
 */
@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3030")
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class FreeBoardController {

    /**
     * 자유(Free) 게시글 서비스
     */
    private final FreeBoardService freeBoardService;

    /**
     * 파일(File) 서비스
     */
    private final FileService fileService;

    /**
     * 새로운 자유 게시글을 등록합니다.
     *
     * @param paramBoard 등록할 게시글 정보
     * @throws IOException 파일 저장 오류
     */
    @PostMapping("/free")
    public APIResult save(@Validated(SaveBoardCheck.class) @ModelAttribute("paramBoard") FreeBoardDTO paramBoard) throws IOException {
        log.info("save freeBoard = {}", paramBoard.toString());
        log.info("save board = {}", paramBoard.toBoardString());

        Board board = freeBoardService.saveFreeBoard(paramBoard);

        APIResult apiResult = new APIResult();
        apiResult.setState(State.SUCCESS);
        apiResult.putResult("seq", board.getSeq());
        return apiResult;
    }

    /**
     * 주어진 seq와 일치하는 자유 게시글을 조회합니다.
     *
     * @param seq
     * @return
     */
    @GetMapping("/free/{seq}")
    public APIResult getFree(@PathVariable int seq) {

        FreeBoardDTO board = freeBoardService.findFreeBySeq(seq);

        if (board == null) {
            log.info("BoardSeq 에러 발생");
            throw new NoSuchBoardSeqException();
        }

        List<FileDTO> fileList = fileService.findFileByBoardSeq(seq);
        log.info("fileList = {}", fileList);

        // 조회한 게시글, 파일 정보를 freeBoardDTO에 담습니다.
        FreeBoardDTO freeBoard = new FreeBoardDTO();
        freeBoard.setBoard(board);
        freeBoard.setFiles(fileList);

        // 조회된 게시글의 조회수를 1 증가시킵니다.
        freeBoardService.updateReadCount(seq);

        APIResult apiResult = new APIResult();
        apiResult.setState(State.SUCCESS);
        apiResult.putResult("board", freeBoard);
        return apiResult;
    }

    /**
     * 주어진 seq의 게시글을 수정합니다.
     *
     * @param paramBoard 수정할 게시글 정보
     * @return
     */
    @PostMapping("/free/{seq}")
    public APIResult updateFree(@Validated(UpdateBoardCheck.class) @ModelAttribute("paramBoard") FreeBoardDTO paramBoard) throws IOException {

        log.info("update freeBoard = {}", paramBoard.toString());
        log.info("update board = {}", paramBoard.toBoardString());

        freeBoardService.updateFreeBoard(paramBoard);

        APIResult apiResult = new APIResult();
        apiResult.setState(State.SUCCESS);
        return apiResult;
    }

    /**
     * 주어진 검색 조건에 맞춰 자유게시글 리스트를 조회합니다.
     *
     * @param boardSearch 검색조건
     * @return 자유게시글 리스트
     */
    @GetMapping("/frees")
    public APIResult getFrees(@ModelAttribute("boardSearchCondition") BoardSearchCondition boardSearch) {

        log.info("FreeController getFress 호출됨");
        log.info("FreeController boardSearch = {}", boardSearch);

        // 게시판의 종류를 지정합니다.
        boardSearch.setType("F");

        // 게시판의 카테고리를 조회합니다.
        List<Category> categoryList = freeBoardService.getCategoryList("F");

        // 검색 조건에 맞는 게시글을 조회합니다.
        List<Board> boardList = freeBoardService.findFreeBoardList(boardSearch);

        // 검색 조건에 맞는 게시글 수를 조회합니다.
        int totalListCount = freeBoardService.findFreeBoardListCount(boardSearch);

        APIResult apiResult = new APIResult();
        apiResult.setState(State.SUCCESS);
        apiResult.putResult("categoryList", categoryList);
        apiResult.putResult("boardList", boardList);
        apiResult.putResult("totalListCount", totalListCount);
        return apiResult;
    }

    /**
     * 메인 페이지에 출력될 최신 자유게시글 조회합니다.
     *
     * @param count 자유게시글 리스트 사이즈
     * @return 자유게시글 리스트
     */
    @GetMapping("/frees/{count}")
    public APIResult getMainFrees(@PathVariable Integer count) {
        log.info("count = {}", count);

        BoardSearchCondition boardSearch = new BoardSearchCondition();

        // 게시판의 종류를 지정합니다.
        boardSearch.setType("F");
        boardSearch.setPageSize(count);

        // 검색 조건에 맞는 게시글 리스트를 조회합니다.
        List<Board> boardList = freeBoardService.findFreeBoardList(boardSearch);
        log.info("controller freeList = {}", boardList);

        APIResult apiResult = new APIResult();
        apiResult.putResult("boardList", boardList);
        return apiResult;
    }

    /**
     * 주어진 게시글 번호의 게시글을 삭제합니다.
     *
     * @param seq 삭제할 게시글 번호
     */
    @DeleteMapping("/free/{seq}")
    public APIResult deleteFree(@PathVariable int seq) throws UnsupportedEncodingException {

        freeBoardService.deleteFreeBoard(seq);

        APIResult apiResult = new APIResult();
        apiResult.setState(State.SUCCESS);
        return apiResult;
    }

}
