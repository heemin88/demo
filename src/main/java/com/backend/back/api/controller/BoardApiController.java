package com.backend.back.api.controller;


import com.backend.back.api.dto.board.*;
import com.backend.back.api.dto.comment.CommentResponse;
import com.backend.back.domain.board.Board;
import com.backend.back.domain.board.BoardType;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Key;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api")
public class BoardApiController {

    private final UserService userService;
    private final BoardService boardService;
    private final CommentService commentService;

    private final ResponseService responseService;

    /**
     * 게시물 등록
     */
    
    @PostMapping("/board")
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
     *
     * 게시물 카테고리별,전체 조회 !!
     */

    @GetMapping("/boards")
    public ListResult<BoardResponse> getQuestionList(@RequestParam(required = false,defaultValue = "all",value="category") String status,
                                                     @RequestParam(required = false,defaultValue = "0",value = "page") int PageNum,
                                                     @RequestParam(required = false,defaultValue = "id",value="orderby") String orderCriteria,
                                                     @RequestParam String keyword,
                                                     @PageableDefault(size = 5,direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Board> question=null;
        if(keyword.equals(null)) {
            question = boardService.findBoardAll(pageable,status,PageNum,orderCriteria);
        }
        else {
            question = boardService.findTitle(keyword,pageable);
        }
        List<BoardResponse> boardResponseList=question.stream().map(BoardResponse::toDto).collect(Collectors.toList());
        return responseService.getListResult(boardResponseList);
    }


    /**
     *
     * 게시물 단건 조회
     */

    @GetMapping("/board/{id}")
    public SingleResult<BoardCommentResponse>getBoard(@PathVariable("id") Long id) {
        Board board_byId = boardService.findBoard_byId(id);

        boardService.updateViewCnt(board_byId);
        List<Comment> boardComment = commentService.find_boardComment(board_byId);
        List<CommentResponse> commentResponses=boardComment.stream().map(CommentResponse::toDto).collect(Collectors.toList());

        return responseService.getSingleResult(new BoardCommentResponse(board_byId,commentResponses));
    }
    /*

     */

    /**
     *
     * 게시물 수정
     */
    @PutMapping("/board/{id}")
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
    @DeleteMapping("board/{id}")
    public CommonResult deleteBoard(@PathVariable("id") Long id,
                                                   @RequestBody BoardDeleteRequest request) {
        Board board_byId = boardService.findBoard_byId(id);
        boardService.delete_Board(board_byId,request);

        return responseService.getSuccessResult();
    }


}
