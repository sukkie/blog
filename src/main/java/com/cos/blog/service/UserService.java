package com.cos.blog.service;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.UserModel;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authenticationManager;

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

    // 수정시에는 영속석 컨테스 User 오브젝트를 영속화 시키고, 영속화된 User 오브젝트를 수정.
    @Transactional
    public void 회원수정(UserModel userModel) {
        UserModel persistence = userRepository.findById(userModel.getId()).orElseThrow(() -> {
            return new IllegalArgumentException("회원찾기 실패");
        });

        // kakao로 회원가입한 유저는 수정 못함
        if (persistence.getOauth() == null || persistence.getOauth().equals("")) {
            persistence.setPassword(encoder.encode(userModel.getPassword()));
            persistence.setEmail(userModel.getEmail());
        }
        // 회원수정 함수 종료시 커밋이 자동으로 진행 - 더티체킹

        // 세션등록
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userModel.getUsername(), userModel.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Transactional(readOnly = true)
    public UserModel 회원찾기(String username) {
        return userRepository.findByUsername(username).orElseGet(() -> {
            return null;
        });
    }

//    @Transactional(readOnly = true)
//    public UserModel 로그인(UserModel userModel) {
//        UserModel principal = userRepository.findByUserNameAndPassword(userModel.getUserName(),
//                userModel.getPassword());
//        return principal;
//    }

}
