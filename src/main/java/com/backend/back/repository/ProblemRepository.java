package com.backend.back.repository;

import com.backend.back.domain.problem.LevelProblem;
import com.backend.back.domain.problem.Problem;
import com.backend.back.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProblemRepository extends JpaRepository<Problem,Long> {

    Problem findProblemById(Long id);
    List<Problem> findProblemByUser(User user);
    List<Problem> findProblemByLevelProblem(LevelProblem levelProblem);
    List<Problem> findProblemByDate(LocalDate localDate);
}

