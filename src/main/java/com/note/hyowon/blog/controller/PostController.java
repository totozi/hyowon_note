package com.note.hyowon.blog.controller;

import com.note.hyowon.blog.dto.PageRequestDTO;
import com.note.hyowon.blog.dto.PageResultDTO;
import com.note.hyowon.blog.dto.PostDTO;
import com.note.hyowon.blog.entity.PostEntity;
import com.note.hyowon.blog.exception.PostErrorResponse;
import com.note.hyowon.blog.service.PostService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Class        : PostController
 * Desc         : post CRUD Controller
 * Author       : Hyowon Na
 * Version      : 1.0.0
 * Created Date : 2022-11-30
**/
@Controller
@RequestMapping("/post")
@Log4j2
public class PostController {

    // bean 자동 주입
    @Autowired
    PostService postService;



    /**
     * Method       : selectList
     * Desc         : TODO
     * Author       : Hyowon Na
     * Param        : [model, pageNo]
     * Return       : java.lang.String
     * throws       :
     * Created Date : 2022-11-30
    **/
    @GetMapping("/list")
    public String selectList(Model model, Integer pageNo) {

        // ----------------------------------------------------------------------------------
        // 페이징 처리된 DTO list 결과 반환
        // ----------------------------------------------------------------------------------
        PageResultDTO<PostDTO, PostEntity> resultDTO = getPagedList(pageNo);


        // ----------------------------------------------------------------------------------
        // PageResultDTO 결과값 확인
        // ----------------------------------------------------------------------------------
        log.debug("---------------------------------------------------------------");
        log.debug("PREV  : {}  ", resultDTO.isPrev()       ); // 이전 페이지 존재 여부
        log.debug("NEXT  : {}  ", resultDTO.isNext()       ); // 다음 페이지 존재 여부
        log.debug("TOTAL : {}  ", resultDTO.getTotalPage() ); // 전체 페이지 수
        log.debug("---------------------------------------------------------------");


        // ----------------------------------------------------------------------------------
        // model에 변수 담아 보내기
        // ----------------------------------------------------------------------------------
        model.addAttribute("list"      , resultDTO.getDtoList()   ); // PostDTO List
        model.addAttribute("prev"      , resultDTO.isPrev()       ); // 이전 페이지 존재 여부
        model.addAttribute("next"      , resultDTO.isNext()       ); // 다음 페이지 존재 여부
        model.addAttribute("pageList"  , resultDTO.getPageList()  ); // 화면에 출력될 페이지 목록

        return "/blog/post/list";
    }


    /**
     * Method       : selectOne
     * Desc         : 특정 post의 내용 반환(html)
     * Author       : Hyowon Na
     * Param        : [model, postNo]
     * Return       : java.lang.String
     * throws       :
     * Created Date : 2022-11-30
    **/
    @GetMapping("/detail")
    public String selectOne(Model model, Integer postNo) {
        PostDTO dto = postService.selectOne(postNo);

        model.addAttribute("dto",dto);

        return "/blog/post/detail";
    }

    /**
     * Method       : getPagedList
     * Desc         : 페이징 처리된 결과를 반환
     * Author       : Hyowon Na
     * Param        : [pageNo]
     * Return       : com.note.hyowon.blog.dto.PageResultDTO<com.note.hyowon.blog.dto.PostDTO,
     *                com.note.hyowon.blog.entity.PostEntity>
     * throws       :
     * Created Date : 2022-11-30
    **/
    private PageResultDTO<PostDTO, PostEntity> getPagedList(Integer pageNo) {
        // ----------------------------------------------------------------------------------
        // pageNo(페이지번호)가 null이면 1 세팅
        // ----------------------------------------------------------------------------------
        if(pageNo == null) {
            pageNo = 1;
        }

        // ----------------------------------------------------------------------------------
        // PageRequestDTO 객체 생성
        // ----------------------------------------------------------------------------------
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(pageNo).size(10).build();

        // ----------------------------------------------------------------------------------
        // pageRequestDTO를 파라미터로 select 후 return
        // ----------------------------------------------------------------------------------
        return postService.selectList(pageRequestDTO);
    }

}
