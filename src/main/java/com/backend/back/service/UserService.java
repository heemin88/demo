package com.backend.back.service;

import com.backend.back.domain.board.Board;
import com.backend.back.domain.comment.Comment;
import com.backend.back.domain.member.Member;
import com.backend.back.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Transactional
@RequiredArgsConstructor
@Service
public class UserService{
    private final MemberRepository memberRepository;

    /**
     * User 회원가입 Service
     */
    @Transactional //변경
    public Member join(Member member) {
        validateDuplicateMember(member); //중복회원검증
        memberRepository.save(member);
        return member;
    }

    /**
     * 회원가입시 중복검사
     */
    private void validateDuplicateMember(Member member) {
        // Exception 발생
        Optional<Member> findMembers = memberRepository.findByMail(member.getMail());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
    /**
     * 회원탈퇴 Service
     */
    public void delete_User(Member member) {
        List<Comment> comments = member.getComments();
        comments.clear();

        List<Board> posts = member.getPosts();
        posts.clear();


        memberRepository.delete(member);
    }

    /**
     * 공통 Service
     */


    public Optional<Member> findOne(Long id) {
        return memberRepository.findById(id);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Member findOne(String token) {
        return memberRepository.findByToken(token);
    }
}

