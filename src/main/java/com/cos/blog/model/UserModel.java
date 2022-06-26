package com.cos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
//@DynamicInsert // 값이 null이면 insert퀴리에 추가 안함
public class UserModel {

    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에 연결된 디비의 넘버링 전략을 사용
    private int id; // sequence, auto_increment

    @Column(nullable = false, length = 30, unique = true)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

//    @ColumnDefault("'user'")
    @Enumerated(EnumType.STRING)
    private RoleType role;

    @CreationTimestamp // 시간이 자동 입력
    private Timestamp crateDate;
}
