package com.backend.back.api.dto.board;

import com.backend.back.api.dto.user.UserResponse;
import com.backend.back.domain.board.Board;
import com.backend.back.domain.board.BoardType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BoardResponse {

    private String title;

    private String description;

    private Integer view_count;

    private UserResponse userResponse;

    private LocalDate created_data;
    private LocalDate modified_date;
    private BoardType status;

    public BoardResponse(Board board) {
        this.title = board.getTitle();
        this.description = board.getDescription();
        this.view_count = board.getView_count();
        this.userResponse=UserResponse.toDto(board.getUser());
        this.created_data=board.getCreated_date();
        this.modified_date=board.getModified_date();
        this.status=board.getStatus();
    }


    public static BoardResponse toDto(Board board) {
        return new BoardResponse(board);
    }
}
