package com.backend.back.service;

import com.backend.back.api.dto.board.BoardDeleteRequest;
import com.backend.back.api.dto.board.BoardModifyRequest;
import com.backend.back.domain.board.Board;
import com.backend.back.domain.board.BoardType;
import com.backend.back.domain.comment.Comment;
import com.backend.back.domain.member.Member;
import com.backend.back.repository.BoardRepository;
import com.backend.back.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;

    private final MemberRepository memberRepository;

    /**
     *
     *
     * 게시물 등록 Service
     */
    @Transactional
    public  Long register_Board(Member member, Board board) {
        board.setMember(member);
        List<Board> posts = member.getPosts();
        posts.add(board);

        boardRepository.save(board);

        return board.getId();
    }


    /**
     *
     * 게시물 삭제 Service
     */
    public void delete_Board(Board board, BoardDeleteRequest request) {

        if (board.getMember().getToken().equals(request.getToken())) {

            List<Comment> commentList = board.getCommentList();
            commentList.clear();

            Member member = board.getMember();
            List<Board> posts = member.getPosts();
            posts.remove(board);

            boardRepository.delete(board);
        }
    }


    /**
     * 게시물 수정 Service
     */

    public void updateBoard(Long id,BoardModifyRequest request) throws IOException {

        Board boardById = boardRepository.findBoardById(id);

        if(request.getToken().equals(boardById.getMember().getToken())) {
            boardById.modify(request.getTitle(), request.getDescription());
        }

    }

    /**
     *
     * 게시물 조회수 Service
     */

    public void updateViewCnt(Board board) {
        Integer view_count = board.getViewCount();
        board.setViewCount(view_count+1);
    }

    /**
     *
     * 게시물 조회수 많은순 으로 조회
     */


    /**
     *
     *
     * 공통 Service
     */

    public Board findBoard_byId(Long id) {
        return boardRepository.findBoardById(id);
    }

    public Page<Board> findQuestion(BoardType status,Pageable pageable) {
        return boardRepository.findBoardsByStatus(status,pageable);
    }

    public Page<Board> findBoardAll(Pageable pageable,String category,int pageNum,String orderCriteria) {
        pageable = PageRequest.of(pageNum, 5, Sort.by(Sort.Direction.DESC, orderCriteria));

        if(category.equals("question")) {
            return boardRepository.findBoardsByStatus(BoardType.QUESTION, pageable);
        }
        else if(category.equals("discuss")) {
            return boardRepository.findBoardsByStatus(BoardType.DISCUSS, pageable);
        }
        else if(category.equals("popularity")) {
            return boardRepository.findAll(pageable);
        }
        else {
            return boardRepository.findAll(pageable);
        }
    }

    public Page<Board> findTitle(String keyword,Pageable pageable) {
        return boardRepository.findByTitleContaining(keyword,pageable);
    }


}
