package com.swith.api.service;

import com.swith.api.request.MemberInfoReq;
import com.swith.common.util.SecurityUtil;
import com.swith.db.entity.Member;
import com.swith.db.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Member insertMember(Member member) {
        // member password encoding
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        return memberRepository.save(member);
    }

    @Override
    public Member getMemberByMemberId(Long memberId) {
        return memberRepository.findById(memberId).orElse(null);
    }

    @Override
    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElse(null);
    }

    @Override
    public Member getMemberByKakaoId(String kakaoId) {
        return memberRepository.findByKakaoId(kakaoId).orElse(null);
    }

    @Override
    public Member getMemberByGoogleId(String googleId) {
        return memberRepository.findByGoogleId(googleId).orElse(null);
    }

    @Override
    public Member getMemberByNickname(String nickname) {
        return memberRepository.findByNickname(nickname).orElse(null);
    }

    @Override
    public Member getMemberByAuthentication() {
        return memberRepository.findByEmail(SecurityUtil.getCurrentUsername().orElse(null)).orElse(null);
    }

    @Override
    public Member updateMember(Member member, MemberInfoReq memberInfoReq) {
        member.setNickname(memberInfoReq.getNickname());
        member.setGoal(memberInfoReq.getGoal());
        return memberRepository.save(member);
    }

    @Override
    public Member updateMemberPassword(Member member, String password) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        return memberRepository.save(member);
    }

    @Override
    public void deleteMember(Member member) {
        memberRepository.delete(member);
    }

}
