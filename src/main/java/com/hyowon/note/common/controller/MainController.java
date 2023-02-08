package com.hyowon.note.common.controller;

import com.hyowon.note.user.entity.User;
import com.hyowon.note.user.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller("/")
@Log4j2
public class MainController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String main() {
        log.info("main...............");

        return "index";
    }

    @GetMapping(value = "get/1")
    public ResponseEntity returnAdminEmail(){
        log.info("returnAdminEmail..........");

        Optional<User> optionalUser = userRepository.findByEmail("skgydnjs@gmail.com");
        User user = optionalUser.get();
        ResponseEntity response = new ResponseEntity<>(user, HttpStatus.OK);

        return response;
    }
}
