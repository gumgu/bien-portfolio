package com.study.admin.argumentresolver;

import com.study.admin.dto.AdminDTO;
import com.study.admin.enums.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginAdminArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        log.info("supportsParameter 실행");

        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class); // 애노테이션(@Login) 포함 여부 확인
        boolean hasAdminType = AdminDTO.class.isAssignableFrom(parameter.getParameterType()); // AdminDTO가 맞는지 인

        // 둘 다 만족하는 경우 resolveArgument 실행
        return hasLoginAnnotation && hasAdminType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        log.info("resolveArgument 실행");

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false); // false: 새로운 세션 방지

        if (session == null) {
            // session이 null인 경우, AdminDTO에 null을 넣어줍니다.
            return null;
        }

        return session.getAttribute(SessionConst.LOGIN_ADMIN);
    }
}
