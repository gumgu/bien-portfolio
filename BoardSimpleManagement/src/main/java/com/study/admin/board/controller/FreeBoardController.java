package com.study.admin.board.controller;

import com.study.admin.board.service.FreeBoardService;
import com.study.admin.dto.*;
import com.study.admin.entity.SaveCheck;
import com.study.admin.entity.UpdateCheck;
import com.study.admin.enums.SessionConst;
import com.study.admin.file.service.FileService;
import com.study.admin.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * 자유(Free) 게시판 컨트롤러
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
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
     * 댓글(Reply) 서비스
     */
    private final ReplyService replyService;

    /**
     * 주어진 검색 조건에 맞춰 자유게시글 리스트를 조회합니다.
     *
     * @param boardSearch 검색조건
     * @return 자유게시글 리스트
     */
    @GetMapping("/frees")
    public String list(@ModelAttribute("boardSearch") BoardSearchCondition boardSearch,
                       Model model) {

        log.info("FreeController boardSearch = {}", boardSearch);

        // 게시판의 종류를 지정합니다.
        boardSearch.setType("F");

        // 검색 조건에 맞는 게시글을 조회합니다.
        List<FreeBoardDTO> freeList = freeBoardService.findBoardList(boardSearch);

        // 검색 조건에 맞는 게시글 수를 조회합니다.
        int totalListCount = freeBoardService.findBoardCount(boardSearch);
        boardSearch.setTotalListCount(totalListCount);

        model.addAttribute("freeList", freeList);
        model.addAttribute("totalListCount", totalListCount);
        model.addAttribute("boardSearch", boardSearch);
        return "board/freeList";
    }

    /**
     * 자유 게시글 작성/수정 폼을 제공합니다.
     *
     * @param freeBoardDTO
     * @return
     */
    @GetMapping("/free")
    public String addForm(@ModelAttribute("freeBoardDTO") FreeBoardDTO freeBoardDTO) {
        return "board/freeForm";
    }

    /**
     * 주어진 seq와 일치하는 자유게시글 조회하고, 수정 폼을 제공합니다.
     *
     * @param seq
     * @return
     */
    @GetMapping("/free/{seq}")
    public String modifyForm(@PathVariable int seq, Model model) {

        FreeBoardDTO freeBoardDTO = freeBoardService.findBoardBySeq(seq);

        List<ReplyDTO> replyList = replyService.findRepliesByBoardSeq(seq);

        List<FileDTO> fileList = fileService.findFileByBoardSeq(seq);

        model.addAttribute("freeBoardDTO", freeBoardDTO);
        model.addAttribute("replyList", replyList);
        model.addAttribute("fileList", fileList);

        return "board/freeForm";
    }


    /**
     * 전달받은 자유게시글을 등록합니다.
     * - 자유게시글 등록 조건을 만족하지 않는 경우, 작성 페이지로 되돌아갑니다.
     *
     * @param freeBoardDTO 저장할 자유 게시글 정보
     * @return
     */
    @PostMapping("/free")
    public String add(@Validated(SaveCheck.class) @ModelAttribute("freeBoardDTO") FreeBoardDTO freeBoardDTO,
                      BindingResult bindingResult, HttpServletRequest request) throws IOException {

        log.info("저장할 freeBoardDTO={}", freeBoardDTO);
        log.info("저장할 board={}", freeBoardDTO.toBoardString());

        // binding 실패 시, 다시 작성 폼을 제공합니다.
        if (bindingResult.hasErrors()) {
            log.debug("error={}", bindingResult);
            return "board/freeForm";
        }

        // 세션의 관리자 id를 DTO에 담습니다.
        HttpSession session = request.getSession();
        AdminDTO adminDTO = (AdminDTO) session.getAttribute(SessionConst.LOGIN_ADMIN);

        if (adminDTO == null) {
            String redirectURL = "/admin/free";
            return "redirect:/admin/login?redirectURL=" + redirectURL;
        }

        freeBoardDTO.setAdminId(adminDTO.getId());

        log.debug("freeBoardDTO={}", freeBoardDTO.toString());
        FreeBoardDTO savedFree = freeBoardService.saveBoard(freeBoardDTO);

        return "redirect:/admin/free/" + savedFree.getSeq();
    }

    /**
     * 자유게시글을 수정합니다.
     *
     * @param freeBoardDTO 수정할 자유게시글 정보
     * @return
     */
    @PostMapping("/free/{seq}")
    public String modify(@Validated(UpdateCheck.class) @ModelAttribute FreeBoardDTO freeBoardDTO,
                         BindingResult bindingResult, HttpServletRequest request) {

        log.info("수정할 free = {}", freeBoardDTO);

        // binding 실패 시, 다시 작성 폼을 제공합니다.
        if (bindingResult.hasErrors()) {
            log.debug("error={}", bindingResult);
            return "board/freeForm";
        }

        // 세션의 관리자 id를 DTO에 담습니다.
        HttpSession session = request.getSession();
        AdminDTO adminDTO = (AdminDTO) session.getAttribute(SessionConst.LOGIN_ADMIN);

        if (adminDTO == null) {
            String redirectURL = "/admin/frees";
            return "redirect:/admin/login?redirectURL=" + redirectURL;
        }

        log.info("adminDTO = {}", adminDTO);
        freeBoardDTO.setAdminId(adminDTO.getId());

        freeBoardService.modifyBoard(freeBoardDTO);

        return "board/freeForm";
    }

    /**
     * 주어진 seq의 자유게시글을 삭제합니다.
     *
     * @param seq 삭제할 자유게시글 seq
     * @return
     */
    @GetMapping("/free/delete/{seq}")
    public String delete(@PathVariable int seq) {

        freeBoardService.deleteBoard(seq);

        return "redirect:/admin/frees";
    }
}
