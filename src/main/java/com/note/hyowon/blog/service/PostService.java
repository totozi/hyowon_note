package com.note.hyowon.blog.service;

import com.note.hyowon.blog.dto.PageRequestDTO;
import com.note.hyowon.blog.dto.PageResultDTO;
import com.note.hyowon.blog.dto.PostDTO;
import com.note.hyowon.blog.entity.PostEntity;
import com.note.hyowon.blog.vo.PostContentVO;


/**
 * interface processes CRUD and others related to post
 */
public interface PostService {

    Long insert(PostDTO dto);

    PageResultDTO<PostDTO, PostEntity> selectList(PageRequestDTO requestDTO);


    /**
     * @param dto to be converted to entity
     * @return PostEntity
     */
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
     * @param entity to be converted to DTO
     * @return PostDTO
     */
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
