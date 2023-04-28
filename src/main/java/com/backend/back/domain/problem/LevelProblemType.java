package com.backend.back.domain.problem;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum LevelProblemType {
    DIAMOND,PLATINUM,GOLD,SILVER,BRONZE;

    @JsonCreator
    public static LevelProblemType from(String s) {
        return LevelProblemType.valueOf(s.toUpperCase());
    }
}