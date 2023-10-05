package com.study.filter;

import com.study.global.error.exception.ExpiredAccessTokenException;
import com.study.global.error.exception.InvalidTokenException;
import com.study.global.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    /**
     * Jwt 관련 Utility
     */
    private final JwtUtil jwtUtil;

    /**
     * 비로그인 사용자도 접근 가능한 경로 리스트
     */
    private static final String[] whiteList = {"/", "/api/member/login", "/api/member/signup", "/api/member/checkId", "/api/refresh"};

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        log.info("JWT Filter 적용====================================================");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String jwt = jwtUtil.resolveToken(httpRequest);
        log.info("jwt = {}", jwt);

        String requestURI = httpRequest.getRequestURI();
        log.info("requestUri ={}", requestURI);
        log.info("requestMethod = {}", httpRequest.getMethod());

        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3030");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, UseRefreshToken");
        response.setHeader("Access-Control-Allow-Credentials", "true"); // 필요에 따라 설정


        // preflight(Option method)요청이거나 Get 요청인 경우, jwt 필터링을 생략합니다.
        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod()) ||
                "GET".equalsIgnoreCase(httpRequest.getMethod())) {
            log.info("요청 메서드 = {}", httpRequest.getMethod());

            chain.doFilter(request, response);
            return;
        }

        if (isLoginCheckPath(requestURI)) {
            // 비로그인 사용자가 접근 불가능한 경로인 경우, jwt 검증을 시행합니다.

            try{
                jwtUtil.validateToken(jwt);

            } catch(ExpiredJwtException ex) {

                log.info("토큰 유효기간 만료");
                throw new ExpiredAccessTokenException();

            } catch (InvalidTokenException ex) {

                log.info("부적절한 토큰");
                throw ex;
            }

            // 검증에 성공한 경우 chain을 진행합니다.
            chain.doFilter(request, response);
            return;
        }

        // 비로그인 사용자가 접근 가능한 경로인 경우, 필터를 진행합니다.
        chain.doFilter(request, response);
        log.info("JwtFilter chain 밑");

    }

    /**
     * 화이트 리스트에 해당하는 경로인지 판별합니다.
     *
     * @param requestURL
     * @return 화이트 리스트에 포함 X : 인증체크 필요 = true
     * @return 화이트 리스트에 포함 O : 인증체크 불필요 = false
     */
    private boolean isLoginCheckPath(String requestURL) {
        return !PatternMatchUtils.simpleMatch(whiteList, requestURL);
    }
}

/**
 * response.setHeader("Access-Control-Allow-Origin", "*"); // 모든 Origin 허용, 필요에 따라 필요한 Origin을 설정할 수도 있습니다.
 * response.setHeader("Access-Control-Allow-Credentials", "true"); // 자격 증명 허용 (즉, 인증된 요청 허용)
 * response.setHeader("Access-Control-Allow-Methods", "HEAD, GET, POST, PUT, DELETE, PATCH, OPTIONS"); // 허용할 HTTP 메서드 목록
 * response.setHeader("Access-Control-Max-Age", "3600"); // Preflight 요청에 대한 캐시 유지 시간
 * response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Key, Authorization"); // 허용할 헤더 목록
 */

