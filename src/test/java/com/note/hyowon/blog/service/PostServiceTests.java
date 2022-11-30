package com.note.hyowon.blog.service;

import com.note.hyowon.blog.dto.PageRequestDTO;
import com.note.hyowon.blog.dto.PageResultDTO;
import com.note.hyowon.blog.dto.PostDTO;
import com.note.hyowon.blog.entity.PostEntity;
import groovy.util.logging.Log4j2;
import org.junit.jupiter.api.Test;
import org.slf4j.ILoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostServiceTests {

    /**
     * PostService bean자동 주입
     */
    @Autowired
    private PostService service;

    @Test
    public void testSelect() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();

        PageResultDTO<PostDTO, PostEntity> resultDTO = service.selectList(pageRequestDTO);

        System.out.println("------------- testSelect -------------------");
        System.out.println("PREV : " + resultDTO.isPrev());
        System.out.println("NEXT : " + resultDTO.isNext());
        System.out.println("TOTAL : " + resultDTO.getTotalPage());
        System.out.println("--------------------------------");

        for (PostDTO postDTO : resultDTO.getDtoList()) {
            System.out.println(postDTO);
        }

        System.out.println("===============================");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }

    @Test
    public void testSelectOne() {
        PostDTO dto = service.selectOne(1);

        System.out.println("-------------- testSelectOne ------------------");
        System.out.println("ContentAsMarkdown : " + dto.getContent().getContentAsMarkdown());
        System.out.println("ContentAsHtml     : " + dto.getContent().getContentAsHtml());
        System.out.println("PostNo            : " + dto.getPostNo());
        System.out.println("Title             : " + dto.getTitle());
        System.out.println("-----------------------------------------------");

    }


}
