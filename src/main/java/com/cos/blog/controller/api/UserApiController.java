package com.cos.blog.controller.api;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.UserModel;
import com.cos.blog.repository.UserRepository;
import com.cos.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
public class UserApiController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/user")
    @Transactional
    public ResponseDto<?> save(@RequestBody UserModel userModel) {
        System.out.println(userModel);
        userModel.setRole(RoleType.USER);
        int result = userService.회원가입(userModel);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), result);
    }
}
