package com.note.hyowon.blog.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    // CONTENT_AS_MARKDOWN
    private String contentAsMarkdown;

    // CONTENT_AS_HTML
    private String contentAsHtml;

    // VIEW_COUNT
    private Long viewCount;

    // VISIBLE_YN
    private String visibleYn;

    // REG_DATE
    private LocalDate regDate;

    // UPD_DATE
    private LocalDate updDate;

}
