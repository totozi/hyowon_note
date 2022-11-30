package com.note.hyowon.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;


/**
 * Class        : PageRequestDTO
 * Desc         : 페이징 처리를 요청하는 DTO
 * Author       : Hyowon Na
 * Version      : 1.0.0
 * Created Date : 2022-11-30
**/
@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {
    
    private int page;
    
    private int size;
    
    public PageRequestDTO() {
        this.page = 1;
        this.size = 10;
    }



    /**
     * Method       : getPageable
     * Desc         : Pageable 객체 반환
     * Author       : Hyowon Na
     * Param        : [sort]
     * Return       : org.springframework.data.domain.Pageable
     * throws       : 
     * Created Date : 2022-11-30
    **/
    public Pageable getPageable(Sort sort) {
        return PageRequest.of(page - 1, size, sort);
        // JPA는 페이지 번호가 0부터 시작해서 page - 1 형태로 작성
        // TODO 추후 음수가 들어올 여지가 있어 수정 필요
    }
}
