package com.study.admin.dto;

import com.study.admin.entity.Board;
import lombok.Data;

/**
 * 공지사항 게시글(Notice Board) DTO
 * - Board 엔티티 클래스를 상속합니다.
 */
@Data
public class NoticeBoardDTO extends Board {

    /**
     * 알림글 여부
     */
    private boolean alert;

    public String toString() {
        return "seq:" + getSeq() +
                ", subject:" + getSubject() +
                ", content:" + getContent() +
                ", alert: " + isAlert() +
                ", adminId:" + getAdminId();
    }

}
