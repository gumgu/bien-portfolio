package com.study.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.global.error.dto.ErrorCode;
import com.study.global.error.dto.ErrorDTO;
import com.study.global.error.exception.ExpiredAccessTokenException;
import com.study.global.error.exception.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    /**
     * 토큰 관련 에러 핸들링
     * - JwtFilter에서 발생하는 에러를 핸들링합니다.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        try {
            filterChain.doFilter(request, response);
        } catch (InvalidTokenException e) {

            log.error("ExceptionHandlerFilter: 정상적이지 않은 토큰입니다.");

            ErrorDTO errorDTO = new ErrorDTO(ErrorCode.INVALID_TOKEN);

            response.setStatus(HttpStatus.UNAUTHORIZED.value()); //401
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            objectMapper.writeValue(response.getWriter(), errorDTO);

        } catch (ExpiredAccessTokenException e) {

            log.error("ExceptionHandlerFilter: 만료된 access 토큰 입니다.");

            ErrorDTO errorDTO = new ErrorDTO(ErrorCode.EXPIRED_ACCESS_TOKEN);

            response.setStatus(HttpStatus.FORBIDDEN.value()); //403
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            objectMapper.writeValue(response.getWriter(), errorDTO);

        }
    }
}
