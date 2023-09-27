package com.study.admin.board.controller;

import com.study.admin.board.service.NoticeBoardService;
import com.study.admin.dto.AdminDTO;
import com.study.admin.dto.BoardSearchCondition;
import com.study.admin.dto.NoticeBoardDTO;
import com.study.admin.enums.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 공지사항(Notice) 게시판 컨트롤러
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
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
    public String list(@ModelAttribute("boardSearch") BoardSearchCondition boardSearch,
                       Model model) {

        log.info("NoticeController boardSearch = {}", boardSearch);

        // 게시판의 종류를 지정합니다.
        boardSearch.setType("N");

        // 검색 조건에 맞는 게시글을 조회합니다.
        List<NoticeBoardDTO> noticeList = noticeBoardService.findBoardList(boardSearch);

        // 검색 조건에 맞는 게시글 수를 조회합니다.
        int totalListCount = noticeBoardService.findBoardCount(boardSearch);
        boardSearch.setTotalListCount(totalListCount);

        // 알림글을 가져옵니다.
        List<NoticeBoardDTO> alertList = noticeBoardService.getAlertList(5);

        model.addAttribute("noticeList", noticeList);
        model.addAttribute("alertList", alertList);
        model.addAttribute("totalListCount", totalListCount);
        return "board/noticeList";
    }

    /**
     * 공지사항 게시글 작성 폼을 제공합니다.
     */
    @GetMapping("/notice")
    public String addForm(@ModelAttribute("noticeBoardDTO") NoticeBoardDTO noticeBoardDTO) {
        return "board/noticeForm";
    }

    /**
     * 전달받은 공지사항을 등록합니다.
     * - 공지사항 등록 조건을 만족하지 않는 경우, 작성 페이지로 되돌아갑니다.
     *
     * @param noticeBoardDTO 저장할 공지사항 정보
     * @return
     */
    @PostMapping("/notice")
    public String add(@Validated @ModelAttribute NoticeBoardDTO noticeBoardDTO,
                      BindingResult bindingResult,
                      HttpServletRequest request) {

        // binding 실패 시, 다시 작성 폼을 제공합니다.
        if (bindingResult.hasErrors()) {
            log.debug("error={}", bindingResult);
            return "board/noticeForm";
        }

        // 세션의 관리자 id를 DTO에 담습니다.
        HttpSession session = request.getSession();
        AdminDTO adminDTO = (AdminDTO) session.getAttribute(SessionConst.LOGIN_ADMIN);

        noticeBoardDTO.setAdminId(adminDTO.getId());

        log.info("저장할 noticeBoardDTO={}", noticeBoardDTO.toString());
        NoticeBoardDTO savedNotice = noticeBoardService.saveBoard(noticeBoardDTO);

        return "redirect:/admin/notice/" + savedNotice.getSeq();
    }

    /**
     * 주어진 seq와 일치하는 공지사항을 조회하고, 수정 폼을 제공합니다.
     *
     * @param seq
     * @return
     */
    @GetMapping("/notice/{seq}")
    public String modifyForm(@PathVariable int seq, Model model) {

        NoticeBoardDTO noticeBySeq = noticeBoardService.findBoardBySeq(seq);
        model.addAttribute("noticeBoardDTO", noticeBySeq);

        return "board/noticeForm";
    }

    /**
     * 공지사항을 수정합니다.
     *
     * @param noticeBoardDTO 수정할 공지사항 정보
     * @return
     */
    @PostMapping("/notice/{seq}")
    public String modify(@ModelAttribute NoticeBoardDTO noticeBoardDTO,
                         HttpServletRequest request) {

        log.info("수정할 notice = {}", noticeBoardDTO);

        // 세션의 관리자 id를 DTO에 담습니다.
        HttpSession session = request.getSession();
        AdminDTO adminDTO = (AdminDTO) session.getAttribute(SessionConst.LOGIN_ADMIN);

        log.info("adminDTO = {}", adminDTO);
        noticeBoardDTO.setAdminId(adminDTO.getId());

        noticeBoardService.modifyBoard(noticeBoardDTO);

        return "board/noticeForm";
    }

    /**
     * 주어진 seq의 공지사항을 삭제합니다.
     *
     * @param seq 삭제할 공지사항의 seq
     * @return
     */
    @GetMapping("/notice/delete/{seq}")
    public String delete(@PathVariable int seq) {

        noticeBoardService.deleteBoard(seq);

        return "redirect:/admin/notices";
    }

}
