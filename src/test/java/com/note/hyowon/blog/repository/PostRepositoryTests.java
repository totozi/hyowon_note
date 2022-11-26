package com.note.hyowon.blog.repository;

import com.note.hyowon.blog.entity.PostEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@SpringBootTest
public class PostRepositoryTests {

    @Autowired
    private PostRepository postRepository;

    @Test
    public void insertDummies() {
        LongStream.rangeClosed(1,300).forEach(i -> {
            PostEntity post = PostEntity.builder()
                    .postNo(i)
                    .title("title is " + i)
                    .writer("TestWriter" + (i%10))
                    .contentAsMarkdown("# 1. 제목입니다.\n" +
                            "## 2. TITLE is TITLE\n" +
                            "### 3. TITLE is TITLE")
                    .contentAsHtml("<h1>1. 제목입니다.</h1>\n" +
                            "<h2>2. TITLE is TITLE</h2>\n" +
                            "<h3>3. TITLE is TITLE</h3>")
                    .viewCount(0L)
                    .visibleYn("Y")
                    .regDate(LocalDate.now())
                    .build();

            System.out.println(postRepository.save(post));
        });
    }
}
