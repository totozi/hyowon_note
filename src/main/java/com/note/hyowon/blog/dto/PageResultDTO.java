package com.note.hyowon.blog.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class        : PageResultDTO
 * Desc         : pagination 결과 반환
 * Author       : Hyowon Na
 * Version      : 1.0.0
 * Created Date : 2022-11-30
**/
@Data
public class PageResultDTO<DTO, EN> { // DTO, Entity라는 의미

    // DTO 리스트
    private List<DTO> dtoList;

    // 총 페이지 번호
    private int totalPage;

    // 현재 페이지 번호
    private int page;

    // 목록 사이즈
    private int size;

    // 시작페이지번호, 끝 페이지 번호
    private int start, end;

    // 이전 ,다음
    private boolean prev, next;

    // 페이지 번호 목록
    private List<Integer> pageList;


    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
        // Page<EN> 타입 이용 -> PageResultDTO 생성
        // Function<EN, DTO> 는 엔티티 객체들을 DTO로 변환해 주는 기능
        this.dtoList = result.stream().map(fn).collect(Collectors.toList());

        this.totalPage = result.getTotalPages();

        makePageList(result.getPageable());
    }


    /**
     * Method       : makePageList
     * Desc         : 한 화면에 출력될 페이지 목록 데이터 생성
     * Author       : Hyowon Na
     * Param        : pageable
     * Return       : void
     * throws       :
     * Created Date : 2022-11-30
    **/
    private void makePageList(Pageable pageable) {
        this.page = pageable.getPageNumber() + 1; // 0부터 시작하므로 1을 추가
        this.size = pageable.getPageSize();

        // tempEnd page
        int tempEnd = (int)(Math.ceil(page/10.0) * 10);

        start = tempEnd - 9;

        prev = start > 1;

        end = totalPage > tempEnd ? tempEnd : totalPage;

        next = totalPage > tempEnd;

        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

    }
}
