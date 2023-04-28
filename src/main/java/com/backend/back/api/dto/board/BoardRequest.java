package com.backend.back.api.dto.board;

import com.backend.back.domain.board.Board;
import com.backend.back.domain.board.BoardType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BoardRequest {

    private String token;
    @NotBlank(message="게시글의 제목을 입력하세요.")
    private String title;

    @NotBlank(message=" 내용을 작성하세요 ")
    private String description;

    @NotBlank(message="게시물의 카테고리를 선택해주세요")
    private String status;

    public Board to_Entity() {
        Board board=new Board();
        board.setTitle(title);
        board.setDescription(description);
        BoardType boardType = BoardType.from(status);
        board.setStatus(boardType);
        board.setCreated_date(LocalDate.now());
        board.setView_count(0);
        return board;
    }
}
