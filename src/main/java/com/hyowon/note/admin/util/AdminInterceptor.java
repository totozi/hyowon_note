package com.hyowon.note.admin.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Log4j2
public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            String className = handlerMethod.getBeanType().getName();
            String methodName = handlerMethod.getMethod().getName();
            String message = "Intercepted method: " + className + "." + methodName;
            // 로그 출력
            System.out.println(message);
        }

        // 여기에서 admin으로 로그인한 사용자인지 확인하는 로직을 구현합니다.
        // 로그인 여부, 사용자의 권한 등을 확인하여 처리하면 됩니다.

        log.info("preHandle@@@@@@@@@@@@@@@@");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            System.out.println(headerName + ": " + headerValue);
        }
        if(request.getHeader("Authentication") != null) {
            log.info(request.getHeader("Authentication"));
            log.info("===jwt 인터셉트 성공 ====");
        }
        if (true) {
            return true; // 요청을 계속 진행합니다.
        } else {
            // 오류 페이지로 이동합니다.
            request.getRequestDispatcher("/error-page").forward(request, response);
            return false; // 요청을 중단합니다.
        }
    }
}
