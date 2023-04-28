package com.backend.back.api.dto.comment;

import lombok.Data;

@Data
public class CommentDeleteRequest {

    private Long commentId;
    private String token;
    private Long boardId;
}
