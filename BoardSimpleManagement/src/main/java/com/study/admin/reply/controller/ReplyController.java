package com.study.admin.reply.controller;

import com.study.admin.dto.AdminDTO;
import com.study.admin.dto.ReplyDTO;
import com.study.admin.enums.SessionConst;
import com.study.admin.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3030")
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReplyController {

    /**
     * 댓글(Reply) 서비스
     */
    private final ReplyService replyService;

    /**
     * 새로운 댓글을 등록합니다.
     *
     * @param paramReply 등록할 댓글 정보
     * @return
     */
    @PostMapping("/reply")
    public void saveReply(@RequestBody ReplyDTO paramReply,
                          HttpServletRequest request) {
        log.info("paramReply = {}", paramReply);

        // 세션의 관리자 id를 DTO에 담습니다.
        HttpSession session = request.getSession();
        AdminDTO adminDTO = (AdminDTO) session.getAttribute(SessionConst.LOGIN_ADMIN);

        paramReply.setAdminId(adminDTO.getId());

        replyService.saveReply(paramReply);
    }

    /**
     * 주어진 댓글 식별자(seq)의 댓글을 삭제합니다.
     *
     * @param seq 삭제할 댓글 seq
     */
    @DeleteMapping("/reply/{seq}")
    public void deleteByReplySeq(@PathVariable int seq) {
        replyService.deleteByReplySeq(seq);
    }
}
