package com.backend.back.domain.problem;

import com.backend.back.domain.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="problem_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="levelProblem_id")
    private LevelProblem levelProblem;

    private String url;
    private int problem_order; // 문제 순서
    private String type; // 문제 알고리즘 타입
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd",timezone = "Asia/Seoul")
    private LocalDate date; // 문제 받은 날짜
    @Builder
    public Problem(User user, LevelProblem levelProblem, String url, int problem_order, String type, LocalDate date) {
        this.user = user;
        this.levelProblem = levelProblem;
        this.url = url;
        this.problem_order = problem_order;
        this.type = type;
        this.date = date;
    }
}
