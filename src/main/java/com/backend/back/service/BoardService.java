package com.backend.back.service;

import com.backend.back.api.dto.board.BoardDeleteRequest;
import com.backend.back.api.dto.board.BoardModifyRequest;
import com.backend.back.domain.board.Board;
import com.backend.back.domain.comment.Comment;
import com.backend.back.domain.user.User;
import com.backend.back.repository.BoardRepository;
import com.backend.back.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;

    private final UserRepository userRepository;

    /**
     *
     *
     * 게시물 등록 Service
     */
    @Transactional
    public  Long register_Board(User user,Board board) {
        board.setUser(user);
        List<Board> posts = user.getPosts();
        posts.add(board);

        boardRepository.save(board);

        return board.getId();
    }


    /**
     *
     * 게시물 삭제 Service
     */
    public void delete_Board(Board board, BoardDeleteRequest request) {

        if (board.getUser().getToken().equals(request.getToken())) {

            List<Comment> commentList = board.getCommentList();
            commentList.clear();

            User user = board.getUser();
            List<Board> posts = user.getPosts();
            posts.remove(board);

            boardRepository.delete(board);
        }
    }


    /**
     * 게시물 수정 Service
     */

    public void updateBoard(Long id,BoardModifyRequest request) throws IOException {

        Board boardById = boardRepository.findBoardById(id);

        if(request.getToken().equals(boardById.getUser().getToken())) {
            boardById.modify(request.getTitle(), request.getDescription());
        }

    }

    /**
     *
     * 게시물 조회수 Service
     */

    public void updateViewCnt(Board board) {
        Integer view_count = board.getView_count();
        board.setView_count(view_count+1);
    }

    /**
     *
     *
     * 공통 Service
     */

    public List<Board> findBoard_byUser(User user) {
        return boardRepository.findBoardsByUser(user);
    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public Board findBoard_byId(Long id) {
        return boardRepository.findBoardById(id);
    }


}
