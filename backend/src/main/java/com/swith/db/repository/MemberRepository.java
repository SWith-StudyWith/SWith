package com.swith.db.repository;

import com.swith.db.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);
    Member findByKakaoId(String kakaoId);
    Member findByGoogleId(String googleId);
    Member findByNickname(String nickname);
}
