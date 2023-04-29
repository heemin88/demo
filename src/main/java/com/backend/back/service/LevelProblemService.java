package com.backend.back.service;

import com.backend.back.domain.problem.LevelProblem;
import com.backend.back.domain.problem.LevelProblemType;
import com.backend.back.domain.problem.Problem;
import com.backend.back.repository.ProblemLevelRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LevelProblemService {

    final private ProblemLevelRepository levelProblemRepository;

    /**
     * 문제 등록 Service
     */
//    @Transactional
//    public  Long register_problems() {
//        LevelProblem levelProblem = new LevelProblem();
//        List<Problem> problems = new ArrayList<>();
//        Problem problem = Problem.builder(
//                .problem_order(1)
//                .url("https://www.acmicpc.net/problem/1000")
//                .
//        ).build();
//        levelProblem.setProblem(problems);
//        levelProblem.setLevel(LevelProblemType.BRONZE);
//        levelProblemRepository.save(levelProblem);
//        return levelProblem.getId();
//    }

    /**
     * level 문제 조회 서비스
     */
    public List<Problem> findLevelProblemByLevel(LevelProblemType level){
        return levelProblemRepository.findLevelProblemByLevel(level);
    }
}
