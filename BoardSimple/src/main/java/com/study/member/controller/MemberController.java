package com.study.member.controller;

import com.study.global.api.dto.APIResult;
import com.study.global.api.dto.State;
import com.study.global.error.exception.DuplicateUserNameException;
import com.study.global.error.exception.LoginFailedException;
import com.study.global.util.JwtUtil;
import com.study.member.dto.MemberDTO;
import com.study.member.service.MemberService;
import com.study.refresh.service.RefreshService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * 사용자(Member) 컨트롤러
 */
@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3030")
@RequestMapping("/api/member")
@RequiredArgsConstructor
@RestControllerAdvice
public class MemberController {

    /**
     * 사용자(Member) 서비스
     */
    private final MemberService memberService;

    /**
     * refresh 토큰 서비스
     */
    private final RefreshService refreshService;

    /**
     * JWT Util
     */
    private final JwtUtil jwtUtil;

    /**
     * 로그인 기능을 수행합니다.
     * - 아이디, 비밀번호가 db와 일치하는 경우 access, refresh 토큰을 갱신합니다.
     *
     * @Return JWT token
     */
    @PostMapping("/login")
    public ResponseEntity<APIResult> login(@RequestBody MemberDTO memberDTO) {

        log.info("login memberDTO = {}", memberDTO);

        boolean validLogin = memberService.isValidLogin(memberDTO.getId(), memberDTO.getPassword());
        log.info("validLogin = {}", validLogin);

        // 아이디, 비밀번호가 일치하지 않는 경우 로그인 오류를 발생시킵니다.
        if (!validLogin) {
            throw new LoginFailedException();
        }

        String accessToken = jwtUtil.createAccessToken(memberDTO.getId());
        String refreshToken = refreshService.updateToken(memberDTO.getId());

        APIResult apiResult = new APIResult();
        apiResult.putResult("accessToken", accessToken);
        apiResult.putResult("refreshToken", refreshToken);
        apiResult.setState(State.SUCCESS);
        return ResponseEntity.ok(apiResult);
    }

    /**
     * 비밀번호와 토큰의 ID를 비교하여 일치 여부를 반환합니다.
     *
     * @param memberDTO 확인할 사용자 정보
     * @return 일치 여부
     */
    @PostMapping("/checkPassword")
    public ResponseEntity<APIResult> checkPassword(@RequestBody MemberDTO memberDTO) {
        log.info("memberDTO = {}", memberDTO);

        boolean result = memberService.isValidLogin(memberDTO.getId(), memberDTO.getPassword());

        APIResult apiResult = new APIResult();
        apiResult.setState(State.SUCCESS);
        apiResult.putResult("result", result);
        return ResponseEntity.ok(apiResult);
    }

    /**
     * 주어진 아이디의 중복여부를 확인합니다.
     *
     * @param loginId
     * @return boolean 아이디 중복 여부
     */
    @GetMapping("/checkId/{loginId}")
    public ResponseEntity<APIResult> checkId(@PathVariable String loginId) {

        boolean isUnique = memberService.isUnique(loginId);
        log.debug("isUnique = {}", isUnique);

        APIResult apiResult = new APIResult();
        apiResult.setState(State.SUCCESS);
        apiResult.putResult("isUnique", isUnique);
        return ResponseEntity.ok(apiResult);
    }

    /**
     * 회원가입 작업을 수행합니다.
     *
     * @param memberDTO 사옹자 정보
     */
    @PostMapping("/signup")
    public ResponseEntity<APIResult> signUp(@RequestBody MemberDTO memberDTO) {
        log.info("save member = {}", memberDTO);

        // 아이디가 중복되는 경우 중복 아이디 예외를 발생시킵니다.
        if (!memberService.isUnique(memberDTO.getId())) {
            throw new DuplicateUserNameException();
        }

        // 회원 정보 저장
        UUID uuid = UUID.randomUUID();
        memberService.saveMember(memberDTO);
        refreshService.createRefresh(memberDTO.getId());

        APIResult apiResult = new APIResult();
        apiResult.setState(State.SUCCESS);

        return ResponseEntity.ok(apiResult);
    }

    /**
     * 로그아웃을 시행합니다.
     * - 해당 id와 mapping된 Refresh Token을 삭제합니다.
     *
     * @param memberId
     * @return
     */
    @PostMapping("/logout")
    public ResponseEntity logout(@RequestBody String memberId) {
        log.info("member = {}", memberId);

        // 회원 id로 저장된 refreshToken을 삭제합니다.
        refreshService.deleteByMemberId(memberId);
        return ResponseEntity.ok().build();
    }

}
