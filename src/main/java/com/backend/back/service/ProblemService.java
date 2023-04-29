package com.backend.back.service;

import com.backend.back.domain.problem.Problem;
import com.backend.back.domain.member.Member;
import com.backend.back.repository.ProblemRepository;
import com.backend.back.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProblemService {
    final private ProblemRepository problemRepository;
    final private MemberRepository memberRepository;

    /**
     * 유저에 문제 등록 Service
     */
    public  Long register_userProblem(Problem problem, Member member) {
        validateDuplicateProblem(problem);
        problem.setMember(member);
        List<Problem> problems = member.getProblems();
        problems.add(problem);
        return problem.getId();
    }
    /**
     * 유저에 문제등록시 중복검사
     */
    private void validateDuplicateProblem(Problem problem) {
        Optional<Problem> findProblems = problemRepository.findById(problem.getId());
        // Exception 발생
        if (!findProblems.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 문제입니다.");
        }
    }
    /**
     * 받은날짜 기준 문제 찾기 service
     */
    public List<Problem> findProblemByDate(LocalDate dateTime){
        return problemRepository.findProblemByDate(dateTime);
    }
//    /**
//     * level별 문제 기준 문제 찾기 service
//     */
//    public List<Problem> findProblemByLevelProblem(LevelProblem levelProblem){
//        return problemRepository.findProblemByLevelProblem(levelProblem);
//    }
    /**
     * 공통 Service
     */

    public Problem findProblemById(Long id) {
        return problemRepository.findProblemById(id);
    }

    public List<Problem> findProblemByUser(Long uid) {

        Member member = memberRepository.findById(uid).orElse(null);
        return problemRepository.findProblemByMember(member);
    }
}
