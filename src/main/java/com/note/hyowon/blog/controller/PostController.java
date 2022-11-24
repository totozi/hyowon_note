package com.note.hyowon.blog.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/post")
@Log4j2
public class PostController {

    @GetMapping("/list")
    public String getList(){
        log.info("post/list..................");
        return "/blog/post/list";
    }
}
