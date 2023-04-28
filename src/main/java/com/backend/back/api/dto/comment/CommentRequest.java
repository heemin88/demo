package com.backend.back.api.dto.comment;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentRequest {

    private String token;
    private Long boardId;

    @NotBlank(message="댓글을 작성하세요.")
    private String description;
}
