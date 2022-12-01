package com.note.hyowon.blog.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@Table(name = "POST")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_NO")
    private Long postNo;

    @Column(name = "WRITER", length = 50, nullable = false)
    private String writer;

    @Column(name = "TITLE", length = 100, nullable = false)
    private String title;

    @Column(name = "CONTENT_AS_MARKDOWN", nullable = false)
    private String contentAsMarkdown;

    @Column(name = "CONTENT_AS_HTML", nullable = false)
    private String contentAsHtml;

    @Column(name = "VIEW_COUNT", nullable = false)
    private Long viewCount;

    @Column(name = "VISIBLE_YN", length = 1, nullable = false)
    private String visibleYn;

    @CreatedDate
    @Column(name = "REG_DATE", nullable = false)
    private LocalDate regDate;

    @LastModifiedDate
    @Column(name = "UPD_DATE", nullable = true)
    private LocalDate updDate;


}
