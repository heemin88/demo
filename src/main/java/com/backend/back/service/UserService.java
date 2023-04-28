package com.backend.back.service;

import com.backend.back.domain.board.Board;
import com.backend.back.domain.comment.Comment;
import com.backend.back.domain.user.User;
import com.backend.back.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Transactional
@RequiredArgsConstructor
@Service
public class UserService{
    private final UserRepository userRepository;

    /**
     * User 회원가입 Service
     */
    @Transactional //변경
    public User join(User user) {
        validateDuplicateMember(user); //중복회원검증
        userRepository.save(user);
        return user;
    }

    /**
     * 회원가입시 중복검사
     */
    private void validateDuplicateMember(User user) {
        // Exception 발생
        Optional<User> findMembers = userRepository.findByMail(user.getMail());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
    /**
     * 회원탈퇴 Service
     */
    public void delete_User(User user) {
        List<Comment> comments = user.getComments();
        comments.clear();

        List<Board> posts = user.getPosts();
        posts.clear();


        userRepository.delete(user);
    }

    /**
     * 공통 Service
     */


    public Optional<User> findOne(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findOne(String token) {
        return userRepository.findByToken(token);
    }
}

