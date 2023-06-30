package com.hyowon.note.common.util;

import io.jsonwebtoken.*;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.util.Date;
import java.util.Properties;

@Component
@Log4j2
public class JwtUtils {


    private String secretKeyStr;

    private Key secretKey;

    // 기본 생성자로 secretKeyStr, secretKey 생성
    public JwtUtils() throws IOException {

        // 키 생성을 위한 String값 가져오기
        Properties properties = new Properties();
        FileInputStream fileInputStream = null;

        String secretKeyStr = "";

        ClassPathResource classPathResource = new ClassPathResource("application-oauth.properties");

        InputStream inputStream = classPathResource.getInputStream();

        properties.load(inputStream);


        // 특정 키에 대한 값을 가져옴
        secretKeyStr = properties.getProperty("jwt.secretKey");

        // properties 파일에서 알고리즘 정보를 가져옴
        String algorithm = properties.getProperty("signature.algorithm");

        // 알고리즘 선택
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.forName(algorithm);

        // 키 생성
        secretKey = new SecretKeySpec(secretKeyStr.getBytes(),  signatureAlgorithm.getJcaName());
    }

    // 토큰 발급
    public String createToken(String email) throws Exception {

        if(!email.equals("skgydnjs@gmail.com")) {
            log.warn("email is not valid.");
            return null;
        }
        Claims claims = Jwts.claims(); // 나중에 서버에서 파싱해서 볼 데이터
        claims.put("email", email);
        Date now = new Date();
        
        return Jwts.builder().setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims) // 데이터 넣기
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + (1000L * 60 * 30))) // 만료 기간
                .signWith(secretKey, SignatureAlgorithm.HS256) // 암호화 알고리즘과 암복호화에 사용할 키
                .compact(); // 토큰 생성
    }

    // jwt token 유효성 및 만료 기간 검사
    public boolean isValidToken(String jwtToken) {

        if(jwtToken == null || jwtToken.trim().equals("")) {
            return false;
        }
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwtToken);

            if(!claims.getBody().get("email").equals("skgydnjs@gmail.com")) {
                log.warn("email is not valid.");
                return false;
            }

            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // jwt token에서 데이터를 전달
    public Jws<Claims> getInformation(String token) {
        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
        //Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        return claims;
    }



}
