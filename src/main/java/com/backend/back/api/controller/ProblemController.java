package com.backend.back.api.controller;

import com.backend.back.api.dto.problem.ProblemResponse;
import com.backend.back.domain.problem.Problem;
import com.backend.back.domain.user.User;
import com.backend.back.model.response.CommonResult;
import com.backend.back.model.response.ListResult;
import com.backend.back.service.LevelProblemService;
import com.backend.back.service.ProblemService;
import com.backend.back.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController // 결과값을 JSON으로 출력함
@RequestMapping("/api")
public class ProblemController {
    private final ProblemService problemService;
    private final LevelProblemService levelProblemService;
    private final ResponseService responseService;
    /**
     * 회원 문제 등록
     */
    @PostMapping(value = "/problem")
    public CommonResult NewProblem_save(User user){
        List<Problem> problems = levelProblemService.findLevelProblemByLevel(user.getLevel()); //유저가 선정한 level의 문제가져오기

        int current_problem = user.getProblem_current(); // 현재 받은 문제 번호
        for(int i =1 ; i <= user.getProblem_count();i++){ //problem의 리스트에서 현재 받은 문제 번호 +1 해서 새로운 문제 등록
            Problem problem = problems.get(current_problem + i);
            problemService.register_userProblem(problem,user);
        }
        user.setProblem_current(current_problem+user.getProblem_count()); // 받은 문제 수 만큼 더해주기
        return responseService.getSuccessResult(); // 성공결과정보 리턴
    }
    /**
     * 유저로 문제 조회
     */
    @GetMapping(value = "/problems/{msrl}")
    public ListResult<ProblemResponse> findAllProblem(@PathVariable long msrl){
        List<Problem> problemList = problemService.findProblemByUser(msrl);
        List<ProblemResponse> problemResponses = problemList.stream().map(ProblemResponse::toDto).collect(Collectors.toList());
        return responseService.getListResult(problemResponses);
    }
    /**
     * 받은 날짜 기준 문자조회 ( 현재 날짜 생성해서 인자로 전달 )
     */
    @GetMapping(value = "/problems/date")
    public ListResult<ProblemResponse> findDateProblem(){
        LocalDate now_date = LocalDate.now();
        List<Problem> problemByDate = problemService.findProblemByDate(now_date);
        List<ProblemResponse> problemResponses = problemByDate.stream().map(ProblemResponse::toDto).collect(Collectors.toList());
        return responseService.getListResult(problemResponses);
    }
}
