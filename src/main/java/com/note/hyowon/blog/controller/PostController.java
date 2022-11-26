package com.note.hyowon.blog.controller;

import com.note.hyowon.blog.dto.PageRequestDTO;
import com.note.hyowon.blog.dto.PageResultDTO;
import com.note.hyowon.blog.dto.PostDTO;
import com.note.hyowon.blog.entity.PostEntity;
import com.note.hyowon.blog.service.PostService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/post")
@Log4j2
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("/list")
    public String selectList(Model model, Integer pageNo){
        log.info("post/list..................");

        System.out.println("pageNo : " + pageNo);

        PageRequestDTO pageRequestDTO = null;

        if(pageNo != null) {
            pageRequestDTO = PageRequestDTO.builder().page(pageNo).size(10).build();
        } else {
            pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();
        }

        PageResultDTO<PostDTO, PostEntity> resultDTO = postService.selectList(pageRequestDTO);

        System.out.println("PREV : " + resultDTO.isPrev());
        System.out.println("NEXT : " + resultDTO.isNext());
        System.out.println("TOTAL : " + resultDTO.getTotalPage());
        System.out.println("--------------------------------");

        model.addAttribute("list", resultDTO.getDtoList());
        model.addAttribute("prev",resultDTO.isPrev());
        model.addAttribute("next",resultDTO.isNext());
        model.addAttribute("pageList", resultDTO.getPageList());

        for (PostDTO postDTO : resultDTO.getDtoList()) {
            System.out.println(postDTO);
        }

        return "/blog/post/list";
    }
}
