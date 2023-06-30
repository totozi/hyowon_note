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
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;

@Controller
@RequestMapping("/admin")
@Log4j2
public class AdminController {

    @Autowired
    private JwtUtils jwtUtils;

    @Value("#{oauth['spring.security.oauth2.client.registration.google.client-id']}")
    private String googleClientId;

    @GetMapping("")
    public String getAdminDashboard() {
        log.info("getAdminDashboard..................");
        return "admin/dashboard";
    }

//    @PostMapping("dashboard")
//    public String showDashboardPage(HttpServletRequest request) {
//        log.info("showDashboardPage..................");
//        log.info("jwt : " + (String) request.getAttribute("jwt"));
//
//        // JWT를 세션에 저장
//        HttpSession session = request.getSession();
//        session.setAttribute("jwt", (String) request.getAttribute("jwt"));
//
//        // content html 보내기
//        return "/admin/dashboard";
//    }


    @PostMapping("dashboard")
    public ResponseEntity<?> showDashboardPage(HttpServletRequest request) {
        try {


            // 파일 읽기
            ClassPathResource resource = new ClassPathResource("templates/admin/dashboard.html");
            byte[] fileData = FileCopyUtils.copyToByteArray(resource.getInputStream());
            String fileContent = new String(fileData, StandardCharsets.UTF_8);

            // 기타 데이터
            String jsPath = "script.js";


            log.info("Authentication : " + request.getHeader("Authentication"));

            Jws<Claims> claims = jwtUtils.getInformation(request.getHeader("Authentication"));

            JsonObject jsonData = new JsonObject();
            jsonData.addProperty("content", fileContent);
            jsonData.addProperty("jsPath", jsPath);
            jsonData.addProperty("email", (String) claims.getBody().get("email"));

            Instant expiration = claims.getBody().getExpiration().toInstant();
            Instant now = Instant.now();
            Duration duration = Duration.between(now, expiration);
            String timer = String.valueOf(duration.getSeconds());


            jsonData.addProperty("timer", timer);


            return ResponseEntity.ok(jsonData.toString());
        } catch (IOException e) {
            // 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred");
        }
    }



    private boolean isValidJWT(String jwt) {
        // JWT 유효성 검사 로직을 구현합니다.
        // 인증 토큰을 파싱하고 유효성을 확인하는 로직을 추가해야 합니다.
        // 유효한 토큰인 경우 true를 반환하고, 그렇지 않은 경우 false를 반환합니다.

        // 예시: Authorization 헤더의 Bearer 토큰을 추출하여 유효성을 검사합니다.
        String token = jwt.replace("Bearer ", "");
        // 유효성 검사 로직 구현...

        // 예시로 true를 반환하도록 설정합니다.
        return true;
    }

    @PostMapping("login")
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
        //log.info(googleApiDTO.getCredential());
        //log.info(googleApiDTO.getG_csrf_token());

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

            //log.info("userId : " + userId);
            //log.info("email : " + email);


            // DB에 email로 조회해서 맞으면 토큰 발행...
            if(email.equals("skgydnjs@gmail.com")) {
                jwtToken = jwtUtils.createToken(email);
                //log.info("jwtToken : " + jwtToken);
            }


        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        log.info("adminLogin.......");

        // header의 authentication에 담아 response
        // body에는 요청받은 내용을 처리해서 보내줌
//        ResponseEntity response = ResponseEntity
//                .status(HttpStatus.OK)
//                .header("Authentication", jwtToken)
//                .body("Admin Is Logged In.");

        // 새로운 화면으로 리다이렉트
        return ResponseEntity.status(HttpStatus.OK)
                .header("Location", "/admin/dashboard")
                .header("Authentication", jwtToken)
                .build();

    }
}
