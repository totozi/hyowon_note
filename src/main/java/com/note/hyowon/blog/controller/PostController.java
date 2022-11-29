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


/** post관련 컨트롤러
 *  post 목록을 보고 검색하는 기능
 *  특정 post를 열람하는 기능
 * @author Hyowon Na
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/post")
@Log4j2
public class PostController {

    /** PostService bean 자동 주입
     */
    @Autowired
    PostService postService;

    /** post목록 페이지를 호출한다.
     *  Get Method로 첫 방문이면 default로 최신글 목록을 보여주고
     *  페이지 선택 시 해당 결과를 반환한다. (pagination)
     *  @param model, pageNo
     *  @return mapped String to call /blog/post/list.html
     */
    @GetMapping("/list")
    public String selectList(Model model, Integer pageNo){
        log.info("post/list..................");


        // ----------------------------------------------------------------------------------
        // 페이징 처리된 DTO list 결과 반환
        // ----------------------------------------------------------------------------------
        PageResultDTO<PostDTO, PostEntity> resultDTO = getPagedList(pageNo);

        // ----------------------------------------------------------------------------------
        // PageResultDTO 결과값 확인
        // ----------------------------------------------------------------------------------
        log.info("---------------------------------------------------------------");
        log.info("PREV  : {}  ", resultDTO.isPrev()       ); // 이전 페이지 존재 여부
        log.info("NEXT  : {}  ", resultDTO.isNext()       ); // 다음 페이지 존재 여부
        log.info("TOTAL : {}  ", resultDTO.getTotalPage() ); // 전체 페이지 수
        log.info("---------------------------------------------------------------");


        // ----------------------------------------------------------------------------------
        // model에 변수 담아 보내기
        // ----------------------------------------------------------------------------------
        model.addAttribute("list"      , resultDTO.getDtoList()   ); // PostDTO List
        model.addAttribute("prev"      , resultDTO.isPrev()       ); // 이전 페이지 존재 여부
        model.addAttribute("next"      , resultDTO.isNext()       ); // 다음 페이지 존재 여부
        model.addAttribute("pageList"  , resultDTO.getPageList()  ); // 화면에 출력될 페이지 목록

        return "/blog/post/list";
    }

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
