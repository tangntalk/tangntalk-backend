package com.example.yonseitalk.interceptor;

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

        log.info("인증 체크 인터셉터 실행 {}", requestURI);

        HttpSession session = request.getSession(false);

//        log.info("session; {}",session.getAttribute("loginUser"));

        if (session == null || session.getAttribute("loginUser") == null) {
            log.info("미인증 사용자 요청");
            //로그인으로 redirect
            //response.sendRedirect("/");
            //상태 코드 전달
            response.setStatus(403);

            return false;
        }

        return true;
    }

}
