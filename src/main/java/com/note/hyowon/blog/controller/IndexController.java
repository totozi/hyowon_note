package com.note.hyowon.blog.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Class        : IndexController
 * Desc         : index page controller
 * Author       : Hyowon Na
 * Version      : 1.0.0
 * Created Date : 2022-11-30
**/
@Controller
@RequestMapping("/")
@Log4j2
public class IndexController {


    /**
     * Method       : home
     * Desc         : TODO
     * Author       : Hyowon Na
     * Param        : []
     * Return       : java.lang.String
     * throws       :
     * Created Date : 2022-11-30
    **/
    @GetMapping
    public String home(){
        log.info("index..................");
        return "/blog/index";
    }
}
