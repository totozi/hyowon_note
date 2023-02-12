package com.hyowon.note.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LoginTests {

    @Value("#{oauth['spring.security.oauth2.client.registration.google.client-id']}")
    private String googleClientId;

    @Test
    public void readProperties(){
        System.out.println(googleClientId);
    }
}
