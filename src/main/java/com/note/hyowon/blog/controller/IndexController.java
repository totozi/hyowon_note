package com.note.hyowon.blog.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/** idnex 페이지 컨트롤러
 *  사용자가 웹사이트에 처음 방문 시 보게되는 페이지를 호출
 * @author Hyowon Na
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/")
@Log4j2
public class IndexController {

    /** index페이지를 호출한다.
     *  @param
     *  @return mapped String to call index.html
     */
    @GetMapping
    public String home(){
        log.info("index..................");
        return "/blog/index";
    }
}
