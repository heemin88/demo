package com.backend.back.repository;

import com.backend.back.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByMail(String mail);
    Optional<User> findById(Long id);

    User findByToken(String token);
}
