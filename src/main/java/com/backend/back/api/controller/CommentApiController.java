package com.backend.back.api.controller;


import com.backend.back.api.dto.comment.CommentDeleteRequest;
import com.backend.back.api.dto.comment.CommentModifyRequest;
import com.backend.back.api.dto.comment.CommentRequest;
import com.backend.back.domain.board.Board;
import com.backend.back.domain.comment.Comment;
import com.backend.back.domain.member.Member;
import com.backend.back.model.response.CommonResult;
import com.backend.back.service.BoardService;
import com.backend.back.service.CommentService;
import com.backend.back.service.ResponseService;
import com.backend.back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/comment")
public class CommentApiController {

    private final CommentService commentService;

    private final UserService userService;
    private final BoardService boardService;
    private final ResponseService responseService;


    /**
     * 댓글 작성
     */

    @PostMapping
    public CommonResult registerComment(@Validated @RequestBody CommentRequest request,
                                        BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return responseService.getFailResult();
        }


        Member one = userService.findOne(request.getToken());
        Board board_byId = boardService.findBoard_byId(request.getBoardId());
        Comment comment=new Comment(request.getDescription(), LocalDate.now());

        commentService.write_Comment(one,board_byId,comment);

        return responseService.getSuccessResult();
    }

    /**
     *
     * 댓글 수정
     */
    @PutMapping
    public CommonResult modifyComment(@Validated @RequestBody CommentModifyRequest request,
                                                     BindingResult bindingResult) throws IOException {
        if(bindingResult.hasErrors()) {
            return responseService.getFailResult();
        }

        commentService.modify_Comment(request.getCommentId(),request);

        return responseService.getSuccessResult();
    }

    /**
     *
     * 댓글 삭제
     */
    @DeleteMapping
    public CommonResult deleteBoard(@RequestBody CommentDeleteRequest request) throws IOException {

        Comment comment = commentService.findOne(request.getCommentId());
        Member member = userService.findOne(request.getToken());
        Board board_byId = boardService.findBoard_byId(request.getBoardId());

        commentService.delete_Comment(member,board_byId,comment);

        return responseService.getSuccessResult();
    }


}
