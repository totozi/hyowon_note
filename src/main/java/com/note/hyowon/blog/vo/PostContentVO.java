package com.note.hyowon.blog.vo;

import lombok.Data;

/**
 * Class        : PostContentVO
 * Desc         : Markdown과 Html 형식으로 post의 내용을 출력 및 상호 변환
 * Author       : Hyowon Na
 * Version      : 1.0.0
 * Created Date : 2022-11-30
**/
@Data
public class PostContentVO {

    // CONTENT_AS_Markdown
    public String contentAsMarkdown;

    // CONTENT_AS_HTML
    public String contentAsHtml;

    public String type;

    public PostContentVO() {
    }

    public PostContentVO(String contentAsMarkdown, String contentAsHtml) {
    }


    /**
     * Method       : markdownToHtml
     * Desc         : markdown 을 html으로 변환
     * Author       : Hyowon Na
     * Param        : [contentAsMarkdown]
     * Return       : java.lang.String
     * throws       :
     * Created Date : 2022-11-30
    **/
    public String markdownToHtml(String contentAsMarkdown){
        return null;
    }


    /**
     * Method       : htmlToMarkdown
     * Desc         : html을 markdown으로 변환
     * Author       : Hyowon Na
     * Param        : [contentAsHtml]
     * Return       : java.lang.String
     * throws       :
     * Created Date : 2022-11-30
    **/
    public String htmlToMarkdown(String contentAsHtml){
        return null;
    }





}
