package com.backend.back.api.dto.problem;

import com.backend.back.domain.problem.LevelProblemType;
import com.backend.back.domain.problem.Problem;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProblemResponse {

    private Long id;
    private String url;
    private Integer problem_order;
    private LocalDate date;
    private LevelProblemType level;

    public ProblemResponse(Problem problem) {
        this.id=problem.getId();
        this.url= problem.getUrl();
        this.problem_order=problem.getProblem_order();
        this.date=problem.getDate();
        this.level=problem.getLevelProblem().getLevel();
    }

    public static ProblemResponse toDto(Problem problem) {
        return new ProblemResponse(problem);
    }
}
