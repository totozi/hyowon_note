package com.hyowon.note.admin.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hyowon.note.admin.dto.GoogleApiDTO;
import com.hyowon.note.common.util.JwtUtils;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Controller
@RequestMapping("/admin")
@Log4j2
public class AdminController {

    @Autowired
    private JwtUtils jwtUtils;

    @Value("#{oauth['spring.security.oauth2.client.registration.google.client-id']}")
    private String googleClientId;

    @GetMapping("/")
    public String showLoginPage() {
        log.info("showLoginPage..................");
        return "admin/login";
    }


    @PostMapping("login")
//    public ResponseEntity<String> adminLogin(GoogleApiDTO googleApiDTO) {
//    public ResponseEntity<String> adminLogin(GoogleApiDTO googleApiDTO) {
    public ResponseEntity<String> adminLogin(@RequestBody String response) {

         /*
            1. 구글 로그인 후 컨트롤러로 credential 전송 ( V )
            2. 서버에서 credential로 사용자 확인
            3. DB의 admin 계정과 일치하면 jwt 토큰 발행해서 admin 페이지 url과 함께 전송
            3-1. 일치하지 않으면 access denied 와 함께 index 페이지로 이동
         */
        // Gson 객체 생성
        Gson gson = new Gson();

        // JsonObject 생성
        JsonObject jsonObject = gson.fromJson(response, JsonObject.class);

        // 필드 값 추출
        String credential = jsonObject.get("credential").getAsString();

        GoogleApiDTO googleApiDTO = new GoogleApiDTO();
        googleApiDTO.setCredential(credential);
        log.info(googleApiDTO.getCredential());
        log.info(googleApiDTO.getG_csrf_token());

        HttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(googleClientId))
                        .build();

        String userId = null;
        String email = null;

        String jwtToken = null;

        try {
            GoogleIdToken idToken = verifier.verify(googleApiDTO.getCredential());
            GoogleIdToken.Payload payload = idToken.getPayload();

            userId = payload.getSubject();
            email = payload.getEmail();

            log.info("userId : " + userId);
            log.info("email : " + email);


            // DB에 email로 조회해서 맞으면 토큰 발행...
            if(email.equals("skgydnjs@gmail.com")) {
                jwtToken = jwtUtils.createToken(userId);
                log.info("jwtToken : " + jwtToken);
            }


        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        log.info("adminLogin.......");

        // HttpHeaders 객체 생성 및 Location 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(UriComponentsBuilder.fromPath("/admin/").build().toUri());

        // header의 authentication에 담아 response
        // body에는 요청받은 내용을 처리해서 보내줌
//        ResponseEntity response = ResponseEntity
//                .status(HttpStatus.OK)
//                .header("Authentication", jwtToken)
//                .body("Admin Is Logged In.");

        // 새로운 화면으로 리다이렉트
        return ResponseEntity.status(HttpStatus.OK)
                .header("Location", "/admin/")
                .header("Authentication", jwtToken)
                .build();

        // ResponseEntity 생성 및 응답 본문 설정
        //ResponseEntity<String> responseEntity = new ResponseEntity<>("Admin Is Logged In.", headers, HttpStatus.FOUND);


        //return responseEntity;
    }
}
