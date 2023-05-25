package com.backend.back.api.dto.user;

import com.backend.back.domain.problem.LevelProblemType;
import com.backend.back.domain.member.Member;
import lombok.Data;

@Data
public class UserResponse {

    private Long id;
    private String mail;
    private LevelProblemType level;
    private int problem_count;
    private int problem_current;

    public UserResponse(Member member) {
        this.id=member.getId();
        this.mail= member.getMail();
        this.level= member.getLevel();
        this.problem_count= member.getProblem_count();
        this.problem_current= member.getProblem_current();
    }

    public static UserResponse toDto(Member member) {
        return new UserResponse(member);
    }
}
