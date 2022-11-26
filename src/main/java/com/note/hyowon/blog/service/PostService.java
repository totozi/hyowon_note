package com.note.hyowon.blog.service;

import com.note.hyowon.blog.dto.PageRequestDTO;
import com.note.hyowon.blog.dto.PageResultDTO;
import com.note.hyowon.blog.dto.PostDTO;
import com.note.hyowon.blog.entity.PostEntity;

public interface PostService {


    Long insert(PostDTO dto);

    PageResultDTO<PostDTO, PostEntity> selectList(PageRequestDTO requestDTO);

    default PostEntity dtoToEntity(PostDTO dto) {
        PostEntity entity = PostEntity.builder()
                .postNo(dto.getPostNo())
                .writer(dto.getWriter())
                .title(dto.getTitle())
                .contentAsMarkdown(dto.getContentAsMarkdown())
                .contentAsHtml(dto.getContentAsHtml())
                .viewCount(dto.getViewCount())
                .visibleYn(dto.getVisibleYn())
                .regDate(dto.getRegDate())
                .updDate(dto.getUpdDate())
                .build();

        return entity;
    }

    default PostDTO entityToDto(PostEntity entity) {
        PostDTO dto = PostDTO.builder()
                .postNo(entity.getPostNo())
                .writer(entity.getWriter())
                .title(entity.getTitle())
                .contentAsMarkdown(entity.getContentAsMarkdown())
                .contentAsHtml(entity.getContentAsHtml())
                .viewCount(entity.getViewCount())
                .visibleYn(entity.getVisibleYn())
                .regDate(entity.getRegDate())
                .updDate(entity.getUpdDate())
                .lastChgDateTime(entity.getLastChgDateTime())
                .build();

        return dto;
    }


}
