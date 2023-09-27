package com.study.admin.reply.service;

import com.study.admin.dto.ReplyDTO;
import com.study.admin.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 댓글(Reply) 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReplyService {

    /**
     * 댓글(Reply) 리포지터리
     */
    private final ReplyRepository replyRepository;

    /**
     * 새로운 댓글을 등록합니다.
     *
     * @param paramReply 등록할 댓글 정보
     * @return
     */
    public ReplyDTO saveReply(ReplyDTO paramReply) {
        replyRepository.saveReply(paramReply);
        return paramReply;
    }

    /**
     * 주어진 게시글(Board)에 작성된 댓글 리스트를 조회합니다.
     *
     * @param seq 게시글 seq
     * @return 댓글 리스트
     */
    public List<ReplyDTO> findRepliesByBoardSeq(int seq) {
        return replyRepository.findRepliesByBoardSeq(seq);
    }

    /**
     * 주어진 seq의 댓글을 삭제합니다.
     *
     * @param replySeq 삭제할 댓글 seq
     */
    public void deleteByReplySeq(int replySeq) {
        replyRepository.deleteByReplySeq(replySeq);
    }

    /**
     * 주어진 게시글 seq의 댓글을 모두 삭제합니다.
     *
     * @param boardSeq 삭제할 게시글 seq
     */
    public void deleteByBoardSeq(int boardSeq) {
        replyRepository.deleteByBoardSeq(boardSeq);
    }


}
