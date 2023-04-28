package com.backend.back.domain.problem;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor //빈 생성자를 만드는 어노테이션
public class LevelProblem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="levelProblem_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private LevelProblemType level; // PLATINUM,GOLD,SILVER,BRONZE

    @OneToMany(mappedBy = "levelProblem")
    private List<Problem> problem = new ArrayList<>();
}
