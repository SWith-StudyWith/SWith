package com.swith.db.repository;

import com.swith.db.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select m from Member m where m.isDeleted='N' and m.email=:email and m.kakaoId is null and m.googleId is null")
    Optional<Member> findByEmail(@Param("email") String email);
    @Query("select m from Member m where m.isDeleted='N' and m.kakaoId=:kakaoId")
    Optional<Member> findByKakaoId(@Param("kakaoId") String kakaoId);
    @Query("select m from Member m where m.isDeleted='N' and m.googleId=:googleId")
    Optional<Member> findByGoogleId(@Param("googleId") String googleId);
    Optional<Member> findByNickname(String nickname);
}
