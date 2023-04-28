package com.backend.back.api.dto.board;

import com.backend.back.api.dto.comment.CommentResponse;
import com.backend.back.api.dto.user.UserResponse;
import com.backend.back.domain.board.Board;
import lombok.Data;

import java.util.List;

@Data
public class BoardCommentResponse {

    private String title;

    private String description;

    private Integer view_count;

    private UserResponse userResponse;

    private List<CommentResponse> commentList;

    public BoardCommentResponse(Board board,List<CommentResponse> commentList) {
        this.title = board.getTitle();
        this.description = board.getDescription();
        this.view_count = board.getView_count();
        this.userResponse = UserResponse.toDto(board.getUser());
        this.commentList=commentList;
    }

}
