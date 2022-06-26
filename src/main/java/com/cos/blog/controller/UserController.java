package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// 인증이 안된 사용자들도 출입할 수 있는 경로 /auth/**
// /index.jsp 도 허용
// /static 이하도 허용 /js/**, /css/**, /image/**
@Controller
@RequestMapping("/auth")
public class UserController {

    @GetMapping("joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("loginForm")
    public String loginForm() {
        return "user/loginForm";
    }
}
