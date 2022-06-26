package com.cos.blog.service;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.UserModel;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    public int 회원가입(UserModel userModel) {
        try {
            String rawPassword = userModel.getPassword();
            String encPassword = encoder.encode(rawPassword);
            userModel.setPassword(encPassword);
            userModel.setRole(RoleType.USER);
            userRepository.save(userModel);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return -1;
    }

//    @Transactional(readOnly = true)
//    public UserModel 로그인(UserModel userModel) {
//        UserModel principal = userRepository.findByUserNameAndPassword(userModel.getUserName(),
//                userModel.getPassword());
//        return principal;
//    }

}
