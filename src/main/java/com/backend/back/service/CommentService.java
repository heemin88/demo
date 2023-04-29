package com.backend.back.service;

import com.backend.back.api.dto.comment.CommentLikeRequest;
import com.backend.back.api.dto.comment.CommentModifyRequest;
import com.backend.back.domain.board.Board;
import com.backend.back.domain.comment.Comment;
import com.backend.back.domain.member.Member;
import com.backend.back.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;

    /**
     *
     *
     * 댓글 작성 Service
     */
    public void write_Comment(Member member, Board board, Comment comment) {

        comment.setMember(member);
        comment.setBoard(board);

        List<Comment> commentList = board.getCommentList();
        commentList.add(comment);

        List<Comment> comments = member.getComments();
        comments.add(comment);

        commentRepository.save(comment);
    }

    /**
     *
     *
     * 댓글 삭제 Service
     */
    public void delete_Comment(Member member, Board board, Comment comment) throws IOException {

        if(member.getToken().equals(comment.getMember().getToken())) {
            List<Comment> comments = member.getComments();
            comments.remove(comment);

            List<Comment> commentList = board.getCommentList();
            commentList.remove(comment);

            commentRepository.delete(comment);
        }
    }

    /**
     *
     * 댓글 수정 Service
     */
    public void modify_Comment(Long id, CommentModifyRequest request) throws IOException {

        Comment commentById = commentRepository.findCommentById(id);

        if(commentById.getMember().getToken().equals(request.getToken())) {
            commentById.modify(request.getDescription());
        }
    }

    /**
     *
     * 댓글 좋아요 Service
     */
    public void update_likeCount(CommentLikeRequest commentLikeRequest) {
        String token = commentLikeRequest.getToken();
    }
    /**
     * 공통 Service
     */

    public List<Comment> find_boardComment(Board board) {
        return commentRepository.findCommentsByBoard(board);
    }

    public List<Comment> find_userComment(Member member) {
        return commentRepository.findCommentsByMember(member);
    }

    public Comment findOne(Long id) {
        return commentRepository.findCommentById(id);
    }



}
