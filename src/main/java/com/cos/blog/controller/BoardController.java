package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class BoardController {

    @GetMapping("/")
    public String index() {
        // /WEB-INF/views/index.jsp
        return "index";
    }
}
