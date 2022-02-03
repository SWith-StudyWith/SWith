package com.swith.api.controller;

import com.swith.api.dto.member.request.MemberInfoReq;
import com.swith.api.dto.member.request.MemberReq;
import com.swith.api.dto.member.request.MemberSignupReq;
import com.swith.api.dto.member.request.PasswordFindReq;
import com.swith.api.dto.member.response.AccessTokenRes;
import com.swith.api.dto.member.response.MemberInfoRes;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

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

    @PostMapping
    public ResponseEntity<BaseResponse> signupMember(@RequestBody MemberSignupReq memberSignupReq) {
        log.debug("signupMember - {}", memberSignupReq.toString());
        // 해당 email의 회원이 이미 존재
        Member member = memberService.getMemberByEmail(memberSignupReq.getEmail());
        if (member != null && member.getIsDeleted().equals("N")) {
            return ResponseEntity.status(200).body(new BaseResponse(false, 400, "이미 존재하는 회원"));
        }
        // 회원가입
        member = memberService.insertMember(Member.builder().kakaoId(memberSignupReq.getKakaoId())
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
    public ResponseEntity<BaseDataResponse<AccessTokenRes>> loginMember(@RequestBody MemberReq memberReq) {
        log.debug("loginMember - {}", memberReq.toString());
        Member member = memberService.getMemberByEmail(memberReq.getEmail());
        if (member == null) {
            return ResponseEntity.status(200).body(new BaseDataResponse<>(false, 404,
                    "존재하지 않는 회원", null));
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(member.getMemberId(), memberReq.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);

//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, jwt);
//        return ResponseEntity.status(200).headers(httpHeaders)
//                .body(new BaseResponse(true, 200, "로그인 성공"));
        BaseDataResponse<AccessTokenRes> token = new BaseDataResponse<>(true, 200,
                "로그인 성공", new AccessTokenRes('O', jwt));
        return ResponseEntity.status(200).body(token);
    }

    @PostMapping("/auth/email/password")
    public ResponseEntity<BaseResponse> findPassword(@RequestBody PasswordFindReq passwordFindReq) {
        if (memberService.getMemberByEmail(passwordFindReq.getEmail()) == null) {
            return ResponseEntity.status(200).body(new BaseResponse(false, 400, "가입된 이메일 내역 없음"));
        }

        memberService.findPassword(passwordFindReq.getEmail());
        return ResponseEntity.status(200).body(new BaseResponse(true, 200, "임시 비밀번호 전송 성공"));
    }

    @GetMapping
    public ResponseEntity<BaseDataResponse<MemberInfoRes>> getMember() {
        log.debug("getMember");
        Member member = memberService.getMemberByAuthentication();
        BaseDataResponse<MemberInfoRes> memberInfo;
        // 회원정보 조회 실패
        if (member == null) {
            memberInfo = new BaseDataResponse<>(false, 404, "회원정보 조회 실패", null);
            return ResponseEntity.status(200).body(memberInfo);
        }
        // 회원정보 조회 성공
        memberInfo = new BaseDataResponse<>(true, 200, "회원정보 조회 성공",
                new MemberInfoRes(member.getEmail(), member.getNickname(), member.getGoal(), member.getImgUrl()));
        return ResponseEntity.status(200).body(memberInfo);
    }

    @PutMapping
    public ResponseEntity<BaseDataResponse<MemberInfoRes>> updateMember(
            MemberInfoReq memberInfoReq, @RequestPart(value = "profileImg", required = false) MultipartFile multipartFile) {
        log.debug("updateMember - {}", memberInfoReq);
//        log.debug("updateMember - file name: {}, file size: {}, content type: {}", multipartFile.getOriginalFilename(),
//                multipartFile.getSize(), multipartFile.getContentType());
        Member member = memberService.getMemberByAuthentication();
        // 회원인증 실패
        if (member == null) {
            return ResponseEntity.status(200).body(new BaseDataResponse<>(false,
                    400, "회원인증 실패", null));
        }

        BaseDataResponse<MemberInfoRes> memberInfo = null;
        try {
            member = memberService.updateMember(member, memberInfoReq, multipartFile);
        } catch (IOException e) {
            memberInfo = new BaseDataResponse<>(false, 408, "파일 업로드 실패",
                    new MemberInfoRes(member.getEmail(), member.getNickname(), member.getGoal(), member.getImgUrl()));
        }
        File tempFile = new File("temp");
        if (tempFile.exists() && !tempFile.delete()) log.debug("updateMember - tempFile.delete() failed");

        // 회원정보 수정 실패
        if (member == null) {
            return ResponseEntity.status(200).body(new BaseDataResponse<>(false,
                    404, "회원정보 수정 실패", null));
        }
        // 파일 업로드 실패
        if (memberInfo != null) return ResponseEntity.status(200).body(memberInfo);
        // 회원정보 수정 성공
        memberInfo = new BaseDataResponse<>(true, 200, "회원정보 수정 성공",
                new MemberInfoRes(member.getEmail(), member.getNickname(), member.getGoal(), member.getImgUrl()));
        return ResponseEntity.status(200).body(memberInfo);
    }

    @PostMapping("/password")
    public ResponseEntity<BaseResponse> checkMemberPassword(@RequestBody MemberReq memberReq) {
        log.debug("checkMemberPassword - {}", memberReq.getPassword());
        Member member = memberService.getMemberByAuthentication();
        // 회원인증 실패
        if (member == null) {
            return ResponseEntity.status(200).body(new BaseResponse(false, 400, "회원인증 실패"));
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(member.getMemberId(), memberReq.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 비밀번호 확인 성공
        return ResponseEntity.status(200).body(new BaseResponse(true, 200, "비밀번호 확인 성공"));
    }

    @PatchMapping("/password")
    public ResponseEntity<BaseResponse> updateMemberPassword(@RequestBody MemberReq memberReq) {
        log.debug("updateMemberPassword - {}", memberReq.getPassword());
        Member member = memberService.getMemberByAuthentication();
        // 회원인증 실패
        if (member == null) {
            return ResponseEntity.status(200).body(new BaseResponse(false, 400, "회원인증 실패"));
        }
        member = memberService.updateMemberPassword(member, memberReq.getPassword());
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
