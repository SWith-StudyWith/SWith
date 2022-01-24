package com.swith.db.repository;

import com.swith.db.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select m from Member m where m.isDeleted='N'")
    Optional<Member> findByEmail(String email);
    Optional<Member> findByKakaoId(String kakaoId);
    Optional<Member> findByGoogleId(String googleId);
    Optional<Member> findByNickname(String nickname);
}
