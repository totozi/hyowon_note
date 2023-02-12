package com.hyowon.note.common.controller;

import com.hyowon.note.user.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


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

//    @PostMapping("get/1")
//    public ResponseEntity returnAdminEmail(HttpServletRequest request) {
//        System.out.println("request : " + request.getParameter("credential"));
//
//        return new ResponseEntity(HttpStatus.OK);
//    }



}
