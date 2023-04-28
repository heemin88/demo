package com.backend.back.api.dto.user;

import com.backend.back.domain.problem.LevelProblemType;
import com.backend.back.domain.user.User;
import lombok.Data;

@Data
public class UserResponse {

    private String mail;
    private LevelProblemType level;
    private int problem_count;
    private int problem_current;

    public UserResponse(User user) {
        this.mail=user.getMail();
        this.level=user.getLevel();
        this.problem_count=user.getProblem_count();
        this.problem_current=user.getProblem_current();
    }

    public static UserResponse toDto(User user) {
        return new UserResponse(user);
    }
}
