package com.swith.api.controller;

import com.swith.api.dto.member.request.MemberReq;
import com.swith.api.dto.member.response.AccessTokenRes;
import com.swith.api.service.AuthMailService;
import com.swith.api.service.AuthService;
import com.swith.api.service.MemberService;
import com.swith.common.jwt.TokenProvider;
import com.swith.common.response.BaseDataResponse;
import com.swith.common.response.BaseResponse;
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
@RequestMapping("/api/members/login")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/kakao")
    public ResponseEntity<BaseDataResponse<AccessTokenRes>> loginByKakao(@RequestParam(value = "code") String code) {
        Member member = authService.loginByKakao(code);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(member.getMemberId(), member.getKakaoId());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);

        BaseDataResponse<AccessTokenRes> token = new BaseDataResponse<AccessTokenRes>(true, 200, "카카오 로그인 성공", new AccessTokenRes(jwt));
        return ResponseEntity.status(200).body(token);
    }

    @PostMapping("/google")
    public ResponseEntity<BaseDataResponse<AccessTokenRes>> loginByGoogle(@RequestParam(value = "code") String code) {
        Member member = authService.loginByGoogle(code);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(member.getMemberId(), member.getGoogleId());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);

        BaseDataResponse<AccessTokenRes> token = new BaseDataResponse<AccessTokenRes>(true, 200, "구글 로그인 성공", new AccessTokenRes(jwt));
        return ResponseEntity.status(200).body(token);
    }
}
