package com.hyowon.note.example.login;


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.*;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Properties;

@Controller
@RequestMapping("login")
public class GoogleLoginController {

    String CLIENT_ID = "";
    String CLIENT_SECRET = "";
    String REDIRECT_URI = "";

    @GetMapping("/main")
    public String readPage(Model model) {
        // properties 파일 읽어오기
        String propFileName = "/properties/appication-oauth.properties";
        Properties prop = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

        try {
            if (inputStream != null) {
                prop.load(inputStream);
                
            } else {
                throw new FileNotFoundException("프로퍼티 파일 '" + propFileName + "'을 resource에서 찾을 수 없습니다.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        CLIENT_ID = prop.getProperty("client-id");
        CLIENT_SECRET = prop.getProperty("client-secret");
        REDIRECT_URI = prop.getProperty("redirect-url");


        model.addAttribute("clientId", CLIENT_ID);
        return "test/login";
    }

    @PostMapping("/logged-in")
    public String afterLogin(@RequestParam String credential) throws IOException {

        // GoogleIdToken 발급
        try {

            HttpTransport transport = new NetHttpTransport();
            JsonFactory jsonFactory = new JacksonFactory();


            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    // Specify the CLIENT_ID of the app that accesses the backend:
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    // Or, if multiple clients access the backend:
                    //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                    .build();

            // (Receive idTokenString by HTTPS POST)
            // credential 이 idTokenString 임
            System.out.println("credential : " + credential);

            // 이걸로 유저 정보 가져오는걸 보니 이 토큰이 Access 토큰인듯 
            //  TODO refresh 토큰, 클라이언트가 이 토큰을 쿠키에 저장하고 있도록 하기
            GoogleIdToken idToken = verifier.verify(credential);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                // Print user identifier
                String userId = payload.getSubject();
                System.out.println("User ID: " + userId);

                // Get profile information from payload
                String email = payload.getEmail();
                boolean emailVerified = payload.getEmailVerified();
                String name = (String) payload.get("name");
                String pictureUrl = (String) payload.get("picture");
                String locale = (String) payload.get("locale");
                String familyName = (String) payload.get("family_name");
                String givenName = (String) payload.get("given_name");

                System.out.println("email : " + email);
                System.out.println("name : " + name);

                // Use or store profile information
                // ...

            } else {
                System.out.println("Invalid ID token.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "test/login";
    }
}
