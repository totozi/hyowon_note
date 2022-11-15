package com.hyowon.note.example.login;


import org.springframework.core.io.ClassPathResource;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Properties;

public class UserDto {

    // user의 email -> 어드민임을 확인하기 위해 사용
    private String email;

    // admin 인지 아닌지, 이것에 따라 글쓰기와 관리자 전용 페이지 접근이 활성화/비활성화
    private boolean AdminLogined = false;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdminLogined() {
        return AdminLogined;
    }

    public void setAdminLogined(boolean adminLogined) {
        AdminLogined = adminLogined;
    }
}
