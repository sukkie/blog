package com.cos.blog.controller;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.UserModel;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;

@RestController
public class DummyController {

    @Autowired
    private UserRepository userRepository;

    @DeleteMapping("/dummy/user/{id}")
    public String delete(@PathVariable int id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return "fail";
        }
        return "id : " + id;
    }

    // email, password
    @Transactional
    @PutMapping("/dummy/user/{id}")
    public UserModel updateUser(@PathVariable int id, @RequestBody UserModel userModel) {
        System.out.println(userModel);
        userModel.setId(id);

       UserModel _usermodel = userRepository.findById(id).orElseThrow(() -> {
           return new IllegalArgumentException("유저가 없습니다.");
//           throw new IllegalArgumentException("유저가 없습니다.");
        });
        _usermodel.setPassword(userModel.getPassword());
        _usermodel.setEmail(userModel.getEmail());

//        userRepository.save(_usermodel);
        // 더티체킹
        // Transactional을 추가함으로서 영속된 데이터가 변경되면 커밋 종료시 갱신 됨.
        return _usermodel;
    }

    // http://localhost:8080/dummy/user
    @GetMapping("dummy/users")
    public List<UserModel> users() {
        throw new IllegalArgumentException("aaa");
//        return userRepository.findAll();
    }

    // http://localhost:8080/dummy/user
    // http://localhost:8080/dummy/user?page=0
    @GetMapping("dummy/user")
    public List<UserModel> user(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return userRepository.findAll(pageable).getContent();
    }

    // http://localhost:8080/dummy/user/3
    @GetMapping("dummy/user/{id}")
    public UserModel detail(@PathVariable int id) {
//        UserModel userModel = userRepository.findById(id).orElseGet(() -> {
//            return new UserModel();
//        });
        UserModel userModel = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
//                throw new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
            }
        });
        return userModel;
    }

    // http://localhost:8080/dummy/join
    @PostMapping("dummy/join")
    public String join(@RequestBody UserModel userModel) {
        System.out.println(userModel);
        userModel.setRole(RoleType.USER);
        userRepository.save(userModel);
        return "완료";
    }
}
