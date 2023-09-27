package com.study.board.controller;

import com.study.board.dto.BoardSearchCondition;
import com.study.board.dto.GalleryBoardDTO;
import com.study.board.entity.Board;
import com.study.board.entity.Category;
import com.study.board.entity.SaveBoardCheck;
import com.study.board.service.GalleryBoardService;
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
 * 갤러리(Gallery) 게시글 컨트롤러
 */
@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3030")
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class GalleryBoardController {

    /**
     * 갤러리(Gallery) 게시글 서비스
     */
    private final GalleryBoardService galleryBoardService;

    /**
     * 파일(File) 서비스
     */
    private final FileService fileService;

    /**
     * 주어진 검색 조건에 맞춰 갤러리 게시글 리스트를 조회합니다.
     *
     * @param boardSearch 검색조건
     * @return 갤러리 게시글 리스트
     */
    @GetMapping("/galleries")
    public APIResult getGalleries(@ModelAttribute("boardSearchCondition") BoardSearchCondition boardSearch) {
        log.info("GalleryController boardSearch = {}", boardSearch);

        // 게시판의 종류를 지정합니다.
        boardSearch.setType("G");

        // 검색 조건에 맞는 게시글 수를 조회합니다.
        int totalListCount = galleryBoardService.findGalleryBoardListCount(boardSearch);

        // 게시판의 카테고리를 조회합니다.
        List<Category> categoryList = galleryBoardService.getCategoryList("G");

        // 검색 조건에 맞는 게시글을 조회합니다.
        List<GalleryBoardDTO> galleryList = galleryBoardService.findGalleryBoardList(boardSearch);
        log.info("조회한 galleryList = {}", galleryList);

        APIResult apiResult = new APIResult();
        apiResult.setState(State.SUCCESS);
        apiResult.putResult("categoryList", categoryList);
        apiResult.putResult("galleryList", galleryList);
        apiResult.putResult("totalListCount", totalListCount);
        return apiResult;
    }

    /**
     * 새로운 갤러리 게시글을 등록합니다.
     *
     * @param paramBoard 등록할 게시글 정보
     * @throws IOException 파일 저장 오류
     */
    @PostMapping("/gallery")
    public APIResult save(@Validated(SaveBoardCheck.class)
                          @ModelAttribute("paramBoard") GalleryBoardDTO paramBoard) throws IOException {
        log.info("save GalleryBoardDTO = {}", paramBoard.toString());
        log.info("save board = {}", paramBoard.toBoardString());

        Board board = galleryBoardService.saveGalleryBoard(paramBoard);

        APIResult apiResult = new APIResult();
        apiResult.setState(State.SUCCESS);
        apiResult.putResult("seq", board.getSeq());
        return apiResult;
    }

    /**
     * 주어진 seq와 일치하는 갤러리 게시글을 조회합니다.
     *
     * @param seq
     * @return
     */
    @GetMapping("/gallery/{seq}")
    public APIResult getGallery(@PathVariable int seq) {

        Board board = galleryBoardService.findGalleryBoard(seq);

        if (board == null) {
            log.info("BoardSeq 에러 발생");
            throw new NoSuchBoardSeqException();
        }

        List<FileDTO> fileList = fileService.findFileByBoardSeq(seq);

        // 조회한 게시글, 파일 정보를 galleryBoardDTO에 담습니다.
        GalleryBoardDTO galleryBoard = new GalleryBoardDTO();
        galleryBoard.setBoard(board);
        galleryBoard.setFiles(fileList);


        // 조회된 게시글의 조회수를 1 증가시킵니다.
        galleryBoardService.updateReadCount(seq);

        APIResult apiResult = new APIResult();
        apiResult.setState(State.SUCCESS);
        apiResult.putResult("board", galleryBoard);
        return apiResult;
    }

    /**
     * 주어진 seq의 게시글을 수정합니다.
     *
     * @param paramBoard 수정할 게시글 정보
     * @return
     */
    @PostMapping("/gallery/{seq}")
    public APIResult updateGallery(GalleryBoardDTO paramBoard) throws IOException {

        log.info("update galleryBoard = {}", paramBoard.toString());
        log.info("update board = {}", paramBoard.toBoardString());

        galleryBoardService.updateGalleryBoard(paramBoard);

        APIResult apiResult = new APIResult();
        apiResult.setState(State.SUCCESS);
        return apiResult;
    }

    /**
     * 메인 페이지에 출력될 최신 갤러리 게시글 조회합니다.
     *
     * @param count 갤러리 게시글 리스트 사이즈
     * @return 갤러리 게시글 리스트
     */
    @GetMapping("/galleries/{count}")
    public APIResult getMainGallery(@PathVariable Integer count) {

        BoardSearchCondition boardSearch = new BoardSearchCondition();

        // 게시판의 종류를 지정합니다.
        boardSearch.setType("G");
        boardSearch.setPageSize(count);

        // 검색 조건에 맞는 게시글 리스트를 조회합니다.
        List<GalleryBoardDTO> galleryList = galleryBoardService.findGalleryBoardList(boardSearch);


        APIResult apiResult = new APIResult();
        apiResult.setState(State.SUCCESS);
        apiResult.putResult("galleryList", galleryList);
        return apiResult;
    }

    /**
     * 주어진 게시글 번호의 게시글을 삭제합니다.
     *
     * @param seq 삭제할 게시글 번호
     */
    @DeleteMapping("/gallery/{seq}")
    public APIResult deleteGallery(@PathVariable int seq) throws UnsupportedEncodingException {

        galleryBoardService.deleteGalleryBoard(seq);

        APIResult apiResult = new APIResult();
        apiResult.setState(State.SUCCESS);
        return apiResult;
    }
}
