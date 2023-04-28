package com.backend.back.domain.user;

import com.backend.back.domain.board.Board;
import com.backend.back.domain.comment.Comment;
import com.backend.back.domain.problem.LevelProblemType;
import com.backend.back.domain.problem.Problem;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor(access= AccessLevel.PROTECTED) //빈 생성자를 만드는 어노테이션
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private long id;
    private String mail;
    private String password;
    @Enumerated(EnumType.STRING)
    private LevelProblemType level; // PLATINUM,GOLD,SILVER,BRONZE
    private int problem_count; //받을 문제 수
    // 지금 몇번까지 받았는지?
    private int problem_current;

    private String token;

    @OneToMany(mappedBy = "user")
    private List<Problem> problems = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Board> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();
    @Builder
    public User(String mail, String password, LevelProblemType level, int problem_count ,int problem_current) {
        this.mail = mail;
        this.password = password;
        this.level = level;
        this.problem_count = problem_count;
        this.problem_current = problem_current;
        this.token= UUID.randomUUID().toString();
    }
}
