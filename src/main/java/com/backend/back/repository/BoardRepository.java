package com.backend.back.repository;

import com.backend.back.domain.board.Board;
import com.backend.back.domain.board.BoardType;
import com.backend.back.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {
    Board findBoardById(Long id);
    Page<Board> findBoardsByMember(Member member, Pageable pageable);

    Page<Board> findBoardsByStatus(BoardType status,Pageable pageable);


    Page<Board> findByTitleContaining(String title,Pageable pageable);

}
