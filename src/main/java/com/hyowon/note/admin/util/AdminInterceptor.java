package com.hyowon.note.admin.util;

import com.hyowon.note.common.util.JwtUtils;
import io.jsonwebtoken.Jwt;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Component
@Log4j2
public class AdminInterceptor implements HandlerInterceptor {

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // jwt가 null 이거나 만료되면 로그인 페이지로 보내기
        String jwtStr =  request.getHeader("Authentication");

        log.info("jwtStr : " + jwtStr);

        boolean isValidToken = false;

        if(jwtStr == null) {
            log.info("Authentication is null.");
            request.getRequestDispatcher("/login").forward(request, response);
            return false; // 요청을 중단합니다.
        }

        // AdminInterceptor가 Configuration으로 먼저 생성되어서 JwtUtils가 자동 주입되지 않는 경우가 있는 듯
        if(jwtUtils == null) {
            jwtUtils = new JwtUtils();
        }

        // 토큰이 유효하지 않으면
        if(!jwtUtils.isValidToken(jwtStr)) {
            log.info("Authentication is not valid.");
            request.getRequestDispatcher("/login").forward(request, response);
        } else {

            log.info("Authentication is valid.");
            return true;

        }

        return true;


    }

}
