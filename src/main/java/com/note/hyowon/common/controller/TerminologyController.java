package com.note.hyowon.common.controller;

import com.note.hyowon.common.service.TerminologyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Class        : TerminologyController
 * Desc         : Terminology, Word Controller
 * Author       : Hyowon Na
 * Version      : 1.0.0
 * Created Date : 2023-02-01
 **/
@Controller
@RequestMapping("/term")
@Log4j2
public class TerminologyController {

    @Autowired
    TerminologyService terminologyService;
    
    // TODO 용어 등록 + 단어 등록하는 CRUD 화면 만들기
}
