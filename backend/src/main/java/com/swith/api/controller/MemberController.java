package com.swith.api.controller;

import com.swith.api.request.MemberReq;
import com.swith.api.request.MemberSignupReq;
import com.swith.api.service.MemberService;
import com.swith.common.jwt.TokenProvider;
import com.swith.common.response.AccessToken;
import com.swith.common.response.BaseDataResponse;
import com.swith.common.response.BaseResponse;
import com.swith.db.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberController {
    @Autowired
    MemberService memberService;

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping()
    public ResponseEntity<BaseResponse> signupMember(@RequestBody MemberSignupReq memberInfo) {
        // 해당 email의 회원이 이미 존재
        if (memberService.getMemberByEmail(memberInfo.getEmail()) != null) {
            return ResponseEntity.status(200).body(new BaseResponse(false, 400, "이미 존재하는 회원"));
        }
        // 회원가입
        Member member = memberService.insertMember(Member.builder().kakaoId(memberInfo.getKakaoId())
                .googleId(memberInfo.getGoogleId()).email(memberInfo.getEmail()).password(memberInfo.getPassword())
                .nickname(memberInfo.getNickname()).role(Member.Role.MEMBER).goal(memberInfo.getGoal())
                .isDeleted("N").build());
        // 회원가입 실패
        if (member == null) {
            return ResponseEntity.status(200).body(new BaseResponse(false, 404, "회원가입 실패"));
        }
        // 회원가입 완료
        return ResponseEntity.status(200).body(new BaseResponse(true, 200, "회원가입 성공"));
    }

    @PostMapping("/login")
    public ResponseEntity<BaseDataResponse<AccessToken>> loginMember(@RequestBody MemberReq memberInfo) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(memberInfo.getEmail(), memberInfo.getPassword());
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


}
