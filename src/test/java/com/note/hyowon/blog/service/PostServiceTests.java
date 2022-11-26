package com.note.hyowon.blog.service;

import com.note.hyowon.blog.dto.PageRequestDTO;
import com.note.hyowon.blog.dto.PageResultDTO;
import com.note.hyowon.blog.dto.PostDTO;
import com.note.hyowon.blog.entity.PostEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostServiceTests {

    @Autowired
    private PostService service;

    @Test
    public void testSelect(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();

        PageResultDTO<PostDTO, PostEntity> resultDTO = service.selectList(pageRequestDTO);

        System.out.println("PREV : " + resultDTO.isPrev());
        System.out.println("NEXT : " + resultDTO.isNext());
        System.out.println("TOTAL : " + resultDTO.getTotalPage());
        System.out.println("--------------------------------");

        for (PostDTO postDTO : resultDTO.getDtoList()) {
            System.out.println(postDTO);
        }

        System.out.println("============================");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }


}
