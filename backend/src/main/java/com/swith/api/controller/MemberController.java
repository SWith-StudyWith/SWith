package com.swith.api.controller;

import com.swith.api.request.MemberInfoReq;
import com.swith.api.request.MemberReq;
import com.swith.api.request.MemberSignupReq;
import com.swith.api.service.MemberService;
import com.swith.common.jwt.TokenProvider;
import com.swith.common.response.AccessToken;
import com.swith.common.response.BaseDataResponse;
import com.swith.common.response.BaseResponse;
import com.swith.common.response.MemberInfo;
import com.swith.db.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/members")
public class MemberController {
    @Autowired
    MemberService memberService;

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping()
    public ResponseEntity<BaseResponse> signupMember(@RequestBody MemberSignupReq memberSignupReq) {
        log.debug("signupMember - {}", memberSignupReq.toString());
        // 해당 email의 회원이 이미 존재
        if (memberService.getMemberByEmail(memberSignupReq.getEmail()) != null) {
            return ResponseEntity.status(200).body(new BaseResponse(false, 400, "이미 존재하는 회원"));
        }
        // 회원가입
        Member member = memberService.insertMember(Member.builder().kakaoId(memberSignupReq.getKakaoId())
                .googleId(memberSignupReq.getGoogleId()).email(memberSignupReq.getEmail()).password(memberSignupReq.getPassword())
                .nickname(memberSignupReq.getNickname()).role(Member.Role.MEMBER).isDeleted("N").build());
        // 회원가입 실패
        if (member == null) {
            return ResponseEntity.status(200).body(new BaseResponse(false, 404, "회원가입 실패"));
        }
        // 회원가입 완료
        return ResponseEntity.status(200).body(new BaseResponse(true, 200, "회원가입 성공"));
    }

    @PostMapping("/login")
    public ResponseEntity<BaseDataResponse<AccessToken>> loginMember(@RequestBody MemberReq memberReq) {
        log.debug("loginMember - {}", memberReq.toString());
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(memberReq.getEmail(), memberReq.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);

//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, jwt);
//        return ResponseEntity.status(200).headers(httpHeaders)
//                .body(new BaseResponse(true, 200, "로그인 성공"));
        BaseDataResponse<AccessToken> token = new BaseDataResponse<AccessToken>(true, 200,
                "로그인 성공", new AccessToken(jwt));
        return ResponseEntity.status(200).body(token);
    }

    @GetMapping
    public ResponseEntity<BaseDataResponse<MemberInfo>> getMember() {
        log.debug("getMember");
        Member member = memberService.getMemberByAuthentication();
        BaseDataResponse<MemberInfo> memberInfo;
        // 회원정보 조회 실패
        if (member == null) {
            memberInfo = new BaseDataResponse<MemberInfo>(false, 404, "회원정보 조회 실패", null);
            return ResponseEntity.status(200).body(memberInfo);
        }
        // 회원정보 조회 성공
        memberInfo = new BaseDataResponse<MemberInfo>(true, 200, "회원정보 조회 성공",
                new MemberInfo(member.getEmail(), member.getNickname(), member.getGoal()));
        return ResponseEntity.status(200).body(memberInfo);
    }

    @PutMapping
    public ResponseEntity<BaseDataResponse<MemberInfo>> updateMember(@RequestBody MemberInfoReq memberInfoReq) {
        log.debug("updateMember - {}", memberInfoReq);
        Member member = memberService.getMemberByAuthentication();
        BaseDataResponse<MemberInfo> memberInfo;
        // 회원인증 실패
        if (member == null) {
            memberInfo = new BaseDataResponse<MemberInfo>(false, 400, "회원인증 실패", null);
            return ResponseEntity.status(200).body(memberInfo);
        }
        member = memberService.updateMember(member, memberInfoReq);
        // 회원정보 수정 실패
        if (member == null) {
            memberInfo = new BaseDataResponse<MemberInfo>(false, 404, "회원정보 수정 실패", null);
            return ResponseEntity.status(200).body(memberInfo);
        }
        // 회원정보 수정 성공
        memberInfo = new BaseDataResponse<MemberInfo>(true, 200, "회원정보 수정 성공",
                new MemberInfo(member.getEmail(), member.getNickname(), member.getGoal()));
        return ResponseEntity.status(200).body(memberInfo);
    }

    @PatchMapping
    public ResponseEntity<BaseResponse> updateMemberPassword(String password) {
        log.debug("updateMemberPassword - {}", password);
        Member member = memberService.getMemberByAuthentication();
        // 회원인증 실패
        if (member == null) {
            return ResponseEntity.status(200).body(new BaseResponse(false, 400, "회원인증 실패"));
        }
        member = memberService.updateMemberPassword(member, password);
        // 비밀번호 수정 실패
        if (member == null) {
            return ResponseEntity.status(200).body(new BaseResponse(false, 404, "비밀번호 수정 실패"));
        }
        // 비밀번호 수정 성공
        return ResponseEntity.status(200).body(new BaseResponse(true, 200, "비밀번호 수정 성공"));
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse> deleteMember() {
        log.debug("deleteMember");
        Member member = memberService.getMemberByAuthentication();
        // 회원탈퇴 실패
        if (member == null) {
            return ResponseEntity.status(200).body(new BaseResponse(false, 404, "회원탈퇴 실패"));
        }
        // 회원탈퇴 성공
        memberService.deleteMember(member);
        return ResponseEntity.status(200).body(new BaseResponse(true, 200, "회원탈퇴 성공"));
    }

}
