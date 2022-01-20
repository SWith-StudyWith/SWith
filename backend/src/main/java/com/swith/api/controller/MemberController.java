package com.swith.api.controller;

import com.swith.api.request.MemberReq;
import com.swith.api.request.MemberSignupReq;
import com.swith.api.service.MemberService;
import com.swith.common.response.BaseResponse;
import com.swith.db.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping()
    public ResponseEntity<BaseResponse> signupMember(@RequestBody MemberSignupReq memberInfo) {
        // 해당 email의 회원이 이미 존재
        if (memberService.getMemberByEmail(memberInfo.getEmail()) != null) {
            return ResponseEntity.status(200).body(new BaseResponse(false, 400, "이미 존재하는 회원"));
        }
        // 회원가입
        Member member = memberService.insertMember(Member.builder().kakaoId(memberInfo.getKakaoId())
                .googleId(memberInfo.getGoogleId()).email(memberInfo.getEmail()).password(memberInfo.getPassword())
                .nickname(memberInfo.getNickname()).role(Member.Role.MEMBER).goal(memberInfo.getGoal()).isDeleted("N")
                .build());
        // 회원가입 실패
        if (member == null) {
            return ResponseEntity.status(200).body(new BaseResponse(false, 404, "회원가입 실패"));
        }
        // 회원가입 완료
        return ResponseEntity.status(200).body(new BaseResponse(true, 200, "회원가입 성공"));
    }

//    @PostMapping("/login")
//    public ResponseEntity<BaseResponse> loginMember(@RequestBody MemberReq memberInfo) {
//        Member member = memberService.getMemberByEmail(memberInfo.getEmail());
//        // 해당 email의 회원이 존재하지 않음
//        if (member == null) {
//            return ResponseEntity.status(200).body(new BaseResponse(false, 404, "존재하지 않는 회원"));
//        }
//        if (passwordEncoder.matches(memberInfo.getPassword(), member.getPassword())) {
//            return ResponseEntity.status(200).body(new BaseResponse(true, 200, "로그인 성공"));
//        }
//        return ResponseEntity.status(200).body(new BaseResponse(false, 401, "로그인 실패"));
//    }


}
