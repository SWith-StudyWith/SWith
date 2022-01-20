package com.swith.db.repository;

import com.swith.db.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByKakaoId(String kakaoId);
    Optional<Member> findByGoogleId(String googleId);
    Optional<Member> findByNickname(String nickname);
}
