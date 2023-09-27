package com.study.admin.qna.controller;

import com.study.admin.dto.AdminDTO;
import com.study.admin.dto.BoardSearchCondition;
import com.study.admin.dto.QnaDTO;
import com.study.admin.enums.SessionConst;
import com.study.admin.qna.service.QnaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 문의글(Qna) 컨트롤러
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class QnaController {

    /**
     * 문의글(Qna) 서비스
     */
    private final QnaService qnaService;


    /**
     * 주어진 검색 조건에 맞춰 문의글 리스트를 조회합니다.
     * - 나의 문의 내역만 조회하는 경우 이를 적용합니다.
     * @param boardSearch 검색조건
     * @return 문의글 리스트
     */
    @GetMapping("/qnas")
    public String list(@ModelAttribute("boardSearch") BoardSearchCondition boardSearch,
                       Model model, HttpServletRequest request) {

        // 나의 문의내역만 조회
        log.info("isMyQnaOnly? = {}", boardSearch.isMyQnaOnly());

        if (boardSearch.isMyQnaOnly()) {

            // 세션의 관리자 id를 DTO에 담습니다.
            HttpSession session = request.getSession();
            AdminDTO adminDTO = (AdminDTO) session.getAttribute(SessionConst.LOGIN_ADMIN);

            boardSearch.setMyId(adminDTO.getId());
        }

        // 검색 조건에 맞는 문의글을 조회합니다.
        List<QnaDTO> qnaList = qnaService.findQnaList(boardSearch);

        // 검색 조건에 맞는 문의글 수를 조회합니다.
        int totalListCount = qnaService.findQnaListCount(boardSearch);
        boardSearch.setTotalListCount(totalListCount);

        model.addAttribute("qnaList", qnaList);
        model.addAttribute("totalListCount", totalListCount);
        return "qna/qnaList";
    }

    /**
     * 문의글(Qna) 답변 작성 폼을 제공합니다.
     * @param seq 조회할 문의글 seq
     * @return
     */
    @GetMapping("/qna/{seq}")
    public String addForm(@PathVariable int seq, Model model) {

        QnaDTO qnaBySeq = qnaService.findQnaBySeq(seq);
        model.addAttribute("qnaDTO", qnaBySeq);
        log.info("qnaDTO = {}", qnaBySeq);

        return "qna/qnaForm";
    }

    /**
     * 문의글(Qna) 답변을 등록합니다.
     * @param qnaDTO 수정할 공지사항 정보
     * @return
     */
    @PostMapping("/qna/{seq}")
    public String add(@ModelAttribute QnaDTO qnaDTO,
                      HttpServletRequest request) {

        // 세션의 관리자 id를 DTO에 담습니다.
        HttpSession session = request.getSession();
        AdminDTO adminDTO = (AdminDTO) session.getAttribute(SessionConst.LOGIN_ADMIN);

        qnaDTO.setAdminId(adminDTO.getId());

        qnaService.saveQnaAnswer(qnaDTO);

        return "qna/qnaForm";
    }

    /**
     * 주어진 seq의 공지사항을 삭제합니다.
     * @param seq 삭제할 공지사항의 seq
     * @return
     */
    @GetMapping("/qna/{seq}/delete")
    public String delete(@PathVariable int seq) {

        qnaService.deleteQna(seq);

        return "redirect:/admin/qnas";
    }

}
