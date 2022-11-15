package com.hyowon.note.example.login;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.util.*;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Handles requests for the application home page.
 */
@Controller
public class LoginController {

	// TODO 파일 읽어서 불러올 것
	// Annotation으로 구글 api 가져와서 변수 사용하기
	// 싱글톤으로 토큰 객체 생성하기
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);

		// 구글 api 정보 가져오기
		List<String> list = clientInfo();
		
		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("client_id", list.get(0).toString()); // client_id 보내기

		return "/test/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model) throws GeneralSecurityException, IOException {
		logger.info("Welcome login!");

		// 구글 api 정보 가져오기
		List<String> list = clientInfo();

		// assert 문법 알아보기
		assert list != null;

		// 구글 api 정보 대입
		String client_id = list.get(0).toString();

		String credential = request.getParameter("credential");

		System.out.println("credential : " + credential);

		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
				.setAudience(Collections.singletonList(client_id))
				.build();

		GoogleIdToken idToken = verifier.verify(credential);

		if(idToken != null){
			GoogleIdToken.Payload payload = idToken.getPayload();

			String userEmail = payload.getEmail();
			System.out.println(idToken.toString());
			System.out.println("user Email : " + userEmail);
			model.addAttribute("email", userEmail);
			model.addAttribute("client_id", client_id);

			return "/test/login";
		}


		return "/test/login";
	}

	private static List<String> clientInfo(){
		// google-api client info 문자열 선언
		String client_id     = "";

		try {
			//google-api.properties 가져오기

			// 프로퍼티 파일 resouce
			ClassPathResource resource = new ClassPathResource("properties/google-api.properties");

			//프로퍼티 객체 생성
			Properties properties = new Properties();

			// 프로퍼티 파일 스트림에 담기
			FileInputStream fis = new FileInputStream(resource.getFile());

			// 프로퍼티 파일 로딩
			properties.load(new BufferedInputStream(fis));

			// key로 항목 읽기
			client_id = properties.getProperty("client_id");

			List<String> list = new ArrayList<>();

			list.add(client_id);

			return list;


		} catch (Exception e) {
			logger.error("reading google-api.properties has failed."); // TODO Error msg 통합관리하기
		}

		return null;
	}
	
}
