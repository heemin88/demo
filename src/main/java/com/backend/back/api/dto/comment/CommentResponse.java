package com.backend.back.api.dto.comment;

import com.backend.back.domain.comment.Comment;
import lombok.Data;

@Data
public class CommentResponse {

    private String userId;
    private String description;

    public CommentResponse(Comment comment) {
        this.userId = comment.getUser().getMail();
        this.description = comment.getDescription();
    }

    public static CommentResponse toDto(Comment comment) {
        return new CommentResponse(comment);
    }
}
