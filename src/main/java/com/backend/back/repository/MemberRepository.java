package com.backend.back.repository;

import com.backend.back.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByMail(String mail);
    Optional<Member> findById(Long id);
    Member findByToken(String token);
}
