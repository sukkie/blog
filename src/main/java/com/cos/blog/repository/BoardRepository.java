package com.cos.blog.repository;

import com.cos.blog.model.Board;
import com.cos.blog.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 자동으로 빈등록이 가능하여 @Repository 생략 가능
//@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

}
