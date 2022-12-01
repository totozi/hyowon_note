package com.note.hyowon.blog.vo;

import lombok.Data;

/**
 * Markdown과 Html 형식으로 post의 내용을 출력 및 상호 변환하기 위한 Value Object
 */
@Data
public class PostContentVO {

    // CONTENT_AS_Markdown
    private String contentAsMarkdown;

    // CONTENT_AS_HTML
    private String contentAsHtml;

    public String type;

    public PostContentVO() {
    }

    public PostContentVO(String contentAsMarkdown, String contentAsHtml) {
        this.setContentAsMarkdown(contentAsMarkdown);
        this.setContentAsHtml(contentAsHtml);
    }


    /**
     * TODO markdown 을 html으로 변환
     *
     * @param contentAsMarkdown String content input from a client
     * @return String contentAsHtml
     */
    public String markdownToHtml(String contentAsMarkdown) {
        return null;
    }

    /**
     * TODO html을 markdown으로 변환
     *
     * @param contentAsHtml String content output from a database
     * @return String contentAsMarkdown
     */
    public String htmlToMarkdown(String contentAsHtml) {
        return null;
    }


}
