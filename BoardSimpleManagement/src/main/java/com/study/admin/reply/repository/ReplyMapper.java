package com.study.admin.reply.repository;

import com.study.admin.dto.ReplyDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

/**
 * 댓글(Reply) Mybatis Mapper
 */
@Mapper
public interface ReplyMapper {

    /**
     * 새로운 댓글을 등록합니다.
     *
     * @param replyDTO 등록할 댓글 정보
     */
    @SelectKey(statementType = StatementType.PREPARED,
            statement = "select last_insert_id() as no",
            keyProperty = "seq",
            before = false,
            resultType = int.class)
    void saveReply(ReplyDTO replyDTO);

    /**
     * 주어진 게시글(Board)에 작성된 댓글 리스트를 조회합니다.
     *
     * @param seq 게시글 seq
     * @return 댓글 리스트
     */
    List<ReplyDTO> findRepliesByBoardSeq(int seq);

    /**
     * 주어진 seq의 댓글을 삭제합니다.
     *
     * @param replySeq 삭제할 댓글 seq
     */
    void deleteByReplySeq(int replySeq);

    /**
     * 주어진 게시글 seq의 댓글을 모두 삭제합니다.
     *
     * @param boardSeq 삭제할 게시글 seq
     */
    void deleteByBoardSeq(int boardSeq);
}
