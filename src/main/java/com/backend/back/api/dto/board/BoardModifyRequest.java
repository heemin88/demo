package com.backend.back.api.dto.board;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BoardModifyRequest {

    private String token;

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String description;
}
