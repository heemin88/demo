package com.backend.back.domain.board;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum BoardType {
    QUESTION,ASK,DAILY;

    @JsonCreator
    public static BoardType from(String s) {
        return BoardType.valueOf(s.toUpperCase());
    }
}
