package com.cos.blog.repository;

import com.cos.blog.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

// 자동으로 빈등록이 가능하여 @Repository 생략 가능
//@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {

}
