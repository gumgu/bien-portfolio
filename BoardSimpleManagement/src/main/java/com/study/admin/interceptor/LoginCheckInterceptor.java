package com.study.admin.interceptor;

import com.study.admin.enums.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();

        HttpSession session = request.getSession();

        if(session == null || session.getAttribute(SessionConst.LOGIN_ADMIN) == null) {
            // 로그인으로 redirect
            response.sendRedirect("/admin/login?redirectURL=" + requestURI);
            return false;
        }

        return true;
    }
}
