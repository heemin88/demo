package com.example.demo;

import com.backend.back.BackApplication;
import com.backend.back.domain.problem.LevelProblem;
import com.backend.back.domain.problem.LevelProblemType;
import com.backend.back.domain.problem.Problem;
import com.backend.back.repository.ProblemLevelRepository;
import com.backend.back.repository.ProblemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes= BackApplication.class)
class DemoApplicationTests {
    @Autowired
    ProblemLevelRepository problemLevelRepository;
    @Test
    void contextLoads() {
    }
    @Test
    public void test1(){

        List<Problem> problem_bronze = new ArrayList<>();
        List<Problem> problem_gold = new ArrayList<>();
        List<Problem> problem_silver = new ArrayList<>();
        List<Problem> problem_platinum = new ArrayList<>();

        Problem problem = new Problem("https://www.acmicpc.net/problem/1330",LevelProblemType.BRONZE,1);
        problem_bronze.add(problem);
        LevelProblem levelProblem_b = new LevelProblem(LevelProblemType.BRONZE,problem_bronze);
        problemLevelRepository.save(levelProblem_b);

        problem = new Problem("https://www.acmicpc.net/problem/1010",LevelProblemType.SILVER,1);
        problem_silver.add(problem);
        LevelProblem levelProblem_s = new LevelProblem(LevelProblemType.SILVER,problem_silver);
        problemLevelRepository.save(levelProblem_s);

        problem = new Problem("https://www.acmicpc.net/problem/1011",LevelProblemType.GOLD,1);
        problem_gold.add(problem);
        LevelProblem levelProblem_g = new LevelProblem(LevelProblemType.GOLD,problem_gold);
        problemLevelRepository.save(levelProblem_g);


        problem = new Problem("https://www.acmicpc.net/problem/1028",LevelProblemType.PLATINUM,1);
        problem_platinum.add(problem);
        LevelProblem levelProblem_p = new LevelProblem(LevelProblemType.PLATINUM,problem_platinum);
        problemLevelRepository.save(levelProblem_p);

    }
}
