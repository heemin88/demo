package com.backend.back.api.controller;


import com.backend.back.api.dto.board.*;
import com.backend.back.api.dto.comment.CommentResponse;
import com.backend.back.domain.board.Board;
import com.backend.back.domain.comment.Comment;
import com.backend.back.domain.member.Member;
import com.backend.back.model.response.CommonResult;
import com.backend.back.model.response.ListResult;
import com.backend.back.model.response.SingleResult;
import com.backend.back.service.BoardService;
import com.backend.back.service.CommentService;
import com.backend.back.service.ResponseService;
import com.backend.back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/board")
public class BoardApiController {

    private final UserService userService;
    private final BoardService boardService;
    private final CommentService commentService;

    private final ResponseService responseService;

    /**
     * 게시물 등록
     */
    
    @PostMapping
    public CommonResult registerBoard(@Validated @RequestBody BoardRequest request,
                                                    BindingResult bindingResult) {
//
        if(bindingResult.hasErrors()) {
            return responseService.getFailResult();
        }

        String token = request.getToken();
        Member one = userService.findOne(token);

        Board board = request.to_Entity();
        boardService.register_Board(one,board);

        return responseService.getSuccessResult();
    }

    /**
     *
     * 게시물 전체 조회
     */
    @GetMapping
    public ListResult<BoardResponse> getBoardList() {
        List<Board> all = boardService.findAll();
        List<BoardResponse> boardResponseList = all.stream().map(BoardResponse::toDto).collect(Collectors.toList());
        return responseService.getListResult(boardResponseList);
    }

    /**
     *
     * 게시물 단건 조회
     */

    @GetMapping("/{id}")
    public SingleResult<BoardCommentResponse>getBoard(@PathVariable("id") Long id) {
        Board board_byId = boardService.findBoard_byId(id);

        boardService.updateViewCnt(board_byId);
        List<Comment> boardComment = commentService.find_boardComment(board_byId);
        List<CommentResponse> commentResponses=boardComment.stream().map(CommentResponse::toDto).collect(Collectors.toList());

        return responseService.getSingleResult(new BoardCommentResponse(board_byId,commentResponses));
    }

    /**
     *
     * 게시물 수정
     */
    @PutMapping("/{id}")
    public CommonResult modifyBoard(@PathVariable("id") Long id,
                                                   @Validated @RequestBody BoardModifyRequest request,
                                                   BindingResult bindingResult) throws IOException {

        if(bindingResult.hasErrors()) {
            return responseService.getFailResult();
        }

        boardService.updateBoard(id,request);
        return responseService.getSuccessResult();
    }

    /**
     *
     * 게시물 삭제
     */
    @PostMapping("/{id}")
    public CommonResult deleteBoard(@PathVariable("id") Long id,
                                                   @RequestBody BoardDeleteRequest request) {
        Board board_byId = boardService.findBoard_byId(id);
        boardService.delete_Board(board_byId,request);

        return responseService.getSuccessResult();
    }


}
