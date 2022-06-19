package com.cos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 255)
    private String content;

    @ManyToOne
    @JoinColumn(name = "boardId")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserModel userModel;

    // mappedBy가 있으면 연관관계가 없는거. 즉 테이블에 컬럼을 만들지 마세요.
    // OneToMany의 기본 페치전략은 LAZY로 조인해서 취득 안함.
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
    private List<Reply> replyList;

    @CreationTimestamp
    private Timestamp timestamp;
}

