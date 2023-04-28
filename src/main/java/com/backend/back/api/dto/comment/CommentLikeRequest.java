package com.backend.back.api.dto.comment;

import lombok.Data;

@Data
public class CommentLikeRequest {
    private String token;
    private Long commentId;
}
