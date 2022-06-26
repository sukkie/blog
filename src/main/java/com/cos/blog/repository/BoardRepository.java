package com.cos.blog.repository;

import com.cos.blog.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

// 자동으로 빈등록이 가능하여 @Repository 생략 가능
//@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {
    // JPA 네이밍 퀴리
    // SELECT * FROM UserModel WHERE username = ? AND password = ?
//    UserModel findByUserNameAndPassword(String username, String password);

    // 위와 같은 쿼리 실행
//    @Query(value = "SELECT * FROM UserModel WHERE username = ?1 AND password = ?2", nativeQuery = true )
//    UserModel login(String username, String password);


    // 네이밍 쿼리
    // SELECT * FROM user WHERE username = ?
    Optional<UserModel> findByUsername(String username);
}
