package com.study.member.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

/**
 * 관리자(Admin) DTO 입니다.
 */
@Data
public class MemberDTO {

    /**
     * 사용자 ID: pk, nn
     */
    @NotEmpty
    private String id;

    /**
     * 비밀번호
     */
    @NotEmpty
    private String password;

    /**
     * 사용자 이름
     */
    @NotEmpty
    private String name;

    /**
     * 회원가입일
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime date;

}
