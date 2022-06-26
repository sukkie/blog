package com.cos.blog.service;

import com.cos.blog.model.Board;
import com.cos.blog.model.UserModel;
import com.cos.blog.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public void 글쓰기(Board board, UserModel userModel) {
        board.setCount(0);
        board.setUserModel(userModel);
        boardRepository.save(board);
    }

    @Transactional
    public Page<Board> 글목록(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

}
