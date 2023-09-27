package com.study.reply.controller;

import com.study.global.api.dto.APIResult;
import com.study.global.api.dto.State;
import com.study.global.util.JwtUtil;
import com.study.reply.dto.ReplyDTO;
import com.study.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
     * Jwt 관련 Utility
     */
    private final JwtUtil jwtUtil;

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

        String memberId = jwtUtil.extractMemberId(jwtUtil.resolveToken(request));
        paramReply.setMemberId(memberId);
        replyService.saveReply(paramReply);
    }

    /**
     * 주어진 게시글(Board)에 작성된 댓글 리스트를 조회합니다.
     *
     * @param seq 게시글 seq
     * @return 댓글 리스트
     */
    @GetMapping("/replies/{seq}")
    public APIResult list(@PathVariable int seq) {

        List<ReplyDTO> replyList = replyService.findRepliesByBoardSeq(seq);

        APIResult apiResult = new APIResult();
        apiResult.setState(State.SUCCESS);
        apiResult.putResult("replyList", replyList);
        return apiResult;

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
