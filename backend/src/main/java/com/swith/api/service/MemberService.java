package com.swith.api.service;

import com.swith.api.dto.member.request.MemberInfoReq;
import com.swith.db.entity.Member;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MemberService {
    Member insertMember(Member member);
    Member getMemberByMemberId(Long memberId);
    Member getMemberByEmail(String email);
    Member getMemberByKakaoId(String kakaoId);
    Member getMemberByGoogleId(String googleId);
    Member getMemberByNickname(String nickname);
    void findPassword(String email);
    Member getMemberByAuthentication();
    Member updateMember(Member member, MemberInfoReq memberInfoReq, MultipartFile multipartFile) throws IOException;
    Member updateMemberPassword(Member member, String password);
    void deleteMember(Member member);
}
