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
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Controller to deal with requests related to post
 * like reading posts, searching posts, creating a post, deleting posts, updating posts...
 */
@Controller
@RequestMapping("/post")
@Log4j2
public class PostController {

    // bean 자동 주입
    @Autowired
    private PostService postService;


    /**
     * @param model  contains list of posts to be transfer to a client
     * @param pageNo request page number from a client
     * @return String url
     */
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
        log.debug("PREV  : {}  ", resultDTO.isPrev()); // 이전 페이지 존재 여부
        log.debug("NEXT  : {}  ", resultDTO.isNext()); // 다음 페이지 존재 여부
        log.debug("TOTAL : {}  ", resultDTO.getTotalPage()); // 전체 페이지 수
        log.debug("currentPage : {}  ", resultDTO.getPage()); // 전체 페이지 수
        log.debug("---------------------------------------------------------------");


        // ----------------------------------------------------------------------------------
        // model에 변수 담아 보내기
        // ----------------------------------------------------------------------------------
        model.addAttribute("list"       , resultDTO.getDtoList() ); // PostDTO List
        model.addAttribute("prev"       , resultDTO.isPrev()     ); // 이전 페이지 존재 여부
        model.addAttribute("next"       , resultDTO.isNext()     ); // 다음 페이지 존재 여부
        model.addAttribute("pageList"   , resultDTO.getPageList()); // 화면에 출력될 페이지 목록
        model.addAttribute("currentPage", resultDTO.getPage()    ); // 현재 페이지 번호

        return "/blog/post/list";
    }


    /**
     * @param model  contains the data of a post
     * @param postNo contains the post index of a post
     * @return String url
     */
    @GetMapping("/detail")
    public String selectOne(Model model, Integer postNo) {
        PostDTO dto = postService.selectOne(postNo);

        model.addAttribute("dto", dto);

        return "/blog/post/detail";
    }


    /**
     * processes paganation with pageNo from a client
     *
     * @param pageNo a page number
     * @return PageResultDTO<PostDTO, PostEntity>
     */
    private PageResultDTO<PostDTO, PostEntity> getPagedList(Integer pageNo) {
        // ----------------------------------------------------------------------------------
        // pageNo(페이지번호)가 null이면 1 세팅
        // ----------------------------------------------------------------------------------
        if (pageNo == null) {
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
