package com.hyowon.note.common.util;

import io.jsonwebtoken.*;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Component
@Log4j2
public class JwtUtils {


//    @Value("#{oauth['jwt.secretKey']}")
    //private String secretKeyStr = "skgydnjs123456skgydnjs123456skgydnjs123456"; // TODO 정상적인 문구로 바꾸거나 외부에서 가져오기

    // 암복호화에 사용되는 키
    //private Key secretKey = new SecretKeySpec(secretKeyStr.getBytes(),  SignatureAlgorithm.HS256.getJcaName());

    public String createToken(String email) {

        // 키 생성을 위한 String값 가져오기
        Properties properties = new Properties();
        FileInputStream fileInputStream = null;

        private String secretKeyStr = "";

        private Key secretKey;

        try {
            // properties 파일 경로
            String filePath = "classpath:application.properties";
            
            // 파일을 FileInputStream을 통해 읽어옴
            fileInputStream = new FileInputStream(filePath);
            
            // Properties 객체에 파일 내용을 로드
            properties.load(fileInputStream);
            
            // 특정 키에 대한 값을 가져옴
            secretKeyStr = properties.getProperty("jwt.secretKey");
            
            // 값을 출력
            System.out.println(secretKeyStr);

            // 암복호화에 사용되는 키
            secretKey = new SecretKeySpec(secretKeyStr.getBytes(),  SignatureAlgorithm.HS256.getJcaName());

        } catch (IOException e) {
            e.printStackTrace();
            // 오류 리턴
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
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    // jwt token에서 데이터를 전달
    public Claims getInformation(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        return claims;
    }


    // 어떤 알고리즘으로 암호화할지 properties에서 가져오기
    public Key generateKeyFromProperties(Properties properties) {
        // properties 파일에서 알고리즘 정보를 가져옴
        String algorithm = properties.getProperty("signature.algorithm");

        // 알고리즘에 따라 키 생성
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.forName(algorithm);
        byte[] secretKeyBytes = // 키 생성 로직 (예: properties 파일에서 가져오는 등)
        Key secretKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());

        return secretKey;
    }
}
