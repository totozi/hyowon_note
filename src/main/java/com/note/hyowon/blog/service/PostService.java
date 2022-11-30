package com.note.hyowon.blog.service;

import com.note.hyowon.blog.dto.PageRequestDTO;
import com.note.hyowon.blog.dto.PageResultDTO;
import com.note.hyowon.blog.dto.PostDTO;
import com.note.hyowon.blog.entity.PostEntity;
import com.note.hyowon.blog.vo.PostContentVO;


/**
 * Class        : PostService
 * Desc         : Post 관련 서비스 인터페이스
 * Author       : Hyowon Na
 * Version      : 1.0.0
 * Created Date : 2022-11-30
**/
public interface PostService {


    Long insert(PostDTO dto);

    PageResultDTO<PostDTO, PostEntity> selectList(PageRequestDTO requestDTO);


    /**
     * Method       : dtoToEntity  
     * Desc         : PostDTO를 PostEntity로 변환
     * Author       : Hyowon Na
     * Param        : [dto]
     * Return       : com.note.hyowon.blog.entity.PostEntity
     * throws       : 
     * Created Date : 2022-11-30
    **/ 
    default PostEntity dtoToEntity(PostDTO dto) {
        PostEntity entity = PostEntity.builder()
                .postNo(dto.getPostNo())
                .writer(dto.getWriter())
                .title(dto.getTitle())
                .contentAsMarkdown(dto.getContent().getContentAsMarkdown())
                .contentAsHtml(dto.getContent().getContentAsHtml())
                .viewCount(dto.getViewCount())
                .visibleYn(dto.getVisibleYn())
                .regDate(dto.getRegDate())
                .updDate(dto.getUpdDate())
                .build();

        return entity;
    }


    /**
     * Method       : entityToDto  
     * Desc         : PostEntity를 PostDTO 변환
     * Author       : Hyowon Na
     * Param        : [entity]
     * Return       : com.note.hyowon.blog.dto.PostDTO
     * throws       : 
     * Created Date : 2022-11-30
    **/ 
    default PostDTO entityToDto(PostEntity entity) {
        PostDTO dto = PostDTO.builder()
                .postNo(entity.getPostNo())
                .writer(entity.getWriter())
                .title(entity.getTitle())
                .content(new PostContentVO(
                         entity.getContentAsMarkdown()
                       , entity.getContentAsHtml()
                        )
                )
                .viewCount(entity.getViewCount())
                .visibleYn(entity.getVisibleYn())
                .regDate(entity.getRegDate())
                .updDate(entity.getUpdDate())
                .lastChgDateTime(entity.getLastChgDateTime())
                .build();

        return dto;
    }


    PostDTO selectOne(Integer postNo);
}
