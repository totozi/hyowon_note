package com.note.hyowon.blog.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@Log4j2
public class IndexController {

    @GetMapping
    public String home(){
        log.info("index..................");
        return "/blog/index";
    }
}
