package com.hyowon.note.common.controller;

import com.hyowon.note.user.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@Controller("/")
@Log4j2
public class MainController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String main() {
        log.info("main...............");

        return "index.html";
    }

    @GetMapping("login")
    public String getLogin() {
        log.info("getLogin..................");
        return "login.html";
    }




}
