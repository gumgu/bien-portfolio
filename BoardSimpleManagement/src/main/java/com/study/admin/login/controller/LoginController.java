package com.study.admin.login.controller;

import com.study.admin.dto.AdminDTO;
import com.study.admin.dto.LoginDTO;
import com.study.admin.enums.SessionConst;
import com.study.admin.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * 로그인(Login) 컨트롤러
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class LoginController {

    /**
     * 로그인(login) 서비스
     */
    private final LoginService loginService;

    /**
     * 로그인 폼을 제공합니다.
     *
     * @param loginDTO
     * @return
     */
    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginDTO") LoginDTO loginDTO) {
        return "admin/loginForm";
    }

    /**
     * 로그인을 수행합니다.
     *
     * @param loginDTO
     * @return
     */
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginDTO loginDTO,
                        @RequestParam(defaultValue = "/admin/frees") String redirectURL,
                        BindingResult bindingResult,
                        HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "admin/loginForm";
        }

        AdminDTO loginAdmin = loginService.loginAdmin(loginDTO.getId(), loginDTO.getPassword());
        log.info("loginAdmin = {}", loginAdmin);

        // 로그인이 실패한 경우 alert 후 다시 로그인 페이지 제공합니다.
        if (loginAdmin == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "admin/loginForm";
        }

        // 로그인 성공 처리
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_ADMIN, loginAdmin);

        return "redirect:" + redirectURL;
    }

    /**
     * 로그아웃을 수행합니다.
     *
     * @param request
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate(); // 세션 삭제
        }

        return "redirect:/admin/login";
    }

}

