package com.cos.blog.controller;

import com.cos.blog.model.UserModel;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyController {

    @Autowired
    private UserRepository userRepository;

    // http://localhost:8080/dummy/join
    @PostMapping("dummy/join")
    public String join(@RequestBody UserModel userModel) {
        System.out.println(userModel);
        userRepository.save(userModel);
        return "완료";
    }
}
