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
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    private String content;

    @ColumnDefault("0")
    private int count;

    @ManyToOne(fetch = FetchType.EAGER) // Board가 Many, UserModel이 One
    @JoinColumn(name = "userId")
    // RDB는 오브젝트를 저장 못하지만 Java는 오브젝트를 저장가능
    // 실제 RDB에는 int userId로 저장
    private UserModel userModel;

    @CreationTimestamp
    private Timestamp createDate;

    public UserModel getUserModel() {
        return userModel;
    }
}
