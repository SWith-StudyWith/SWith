package com.swith.api.service;

import com.swith.db.entity.Member;

public interface MemberService {
    Member insertMember(Member member);
    Member getMemberByMemberId(Long memberId);
    Member getMemberByEmail(String email);
    Member getMemberByKakaoId(String kakaoId);
    Member getMemberByGoogleId(String googleId);
    Member getMemberByNickname(String nickname);
    void findPassword(String email);
}
