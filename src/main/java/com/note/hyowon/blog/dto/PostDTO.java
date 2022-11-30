package com.note.hyowon.blog.dto;

import com.note.hyowon.blog.vo.PostContentVO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 * Class        : PostDTO
 * Desc         : post entity와 매핑되는 DTO
 * Author       : Hyowon Na
 * Version      : 1.0.0
 * Created Date : 2022-11-30
**/
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class PostDTO extends CommonDTO {

    // POST_NO
    private Long postNo;

    // WRITER
    private String writer;

    // TITLE
    private String title;

    // ContentAsMarkdown , ContentAsHtml
    private PostContentVO content;

    // VIEW_COUNT
    private Long viewCount;

    // VISIBLE_YN
    private String visibleYn;

    // REG_DATE
    private LocalDate regDate;

    // UPD_DATE
    private LocalDate updDate;

}
