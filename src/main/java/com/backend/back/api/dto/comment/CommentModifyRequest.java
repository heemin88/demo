package com.backend.back.api.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentModifyRequest {

    private Long commentId;
    private String token;

    @NotBlank(message="댓글을 작성해주세요.")
    private String description;
}
