package com.hyowon.note.example.login;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	// 토큰 생성하기
	final String ClientId = "abc";
	
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
		
		model.addAttribute("serverTime", formattedDate );
		
		return "/test/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model) throws GeneralSecurityException, IOException {
		logger.info("Welcome login!");

		String credential = request.getParameter("credential");

		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
				.setAudience(Collections.singletonList(ClientId))
				.build();

		GoogleIdToken idToken = verifier.verify(credential);

		if(idToken != null){
			GoogleIdToken.Payload payload = idToken.getPayload();

			String userEmail = payload.getEmail();
			System.out.println(idToken.toString());
			System.out.println("user Email : " + userEmail);
			model.addAttribute("email", userEmail);

			return "/test/login";
		}


		return "/test/login";
	}
	
}
