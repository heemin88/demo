package com.backend.back.repository;

import com.backend.back.domain.problem.LevelProblem;
import com.backend.back.domain.problem.LevelProblemType;
import com.backend.back.domain.problem.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemLevelRepository extends JpaRepository<LevelProblem,Long> {

    LevelProblem findLevelProblemById(Long id);
    List<Problem> findLevelProblemByLevel(LevelProblemType level);

}
