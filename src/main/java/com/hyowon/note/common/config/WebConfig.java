package com.hyowon.note.common.config;

import com.hyowon.note.admin.util.AdminInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // admin에 대한 요청이 수행되기 전 실행되는 인터셉터
        registry.addInterceptor(new AdminInterceptor()).addPathPatterns("/admin/**");
    }
}
