package com.cos.blog.controller.api;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.UserModel;
import com.cos.blog.repository.UserRepository;
import com.cos.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

@RestController
@RequestMapping("/auth")
public class UserApiController {

    @Autowired
    private UserService userService;

    @PostMapping("/joinProc")
    public ResponseDto<?> save(@RequestBody UserModel userModel) {
        System.out.println(userModel);
        int result = userService.회원가입(userModel);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), result);
    }

    // spring security로 대체
//    @PostMapping("/api/user/login")
//    public ResponseDto<?> login(@RequestBody UserModel userModel, HttpSession session) {
//        System.out.println(userModel);
//        userModel.setRole(RoleType.USER);
//        // 접근주체
//        UserModel principal = userService.로그인(userModel);
//        if (principal != null) {
//            session.setAttribute("principal", principal);
//        }
//        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
//    }
}
