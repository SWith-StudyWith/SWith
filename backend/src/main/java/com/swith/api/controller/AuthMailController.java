package com.swith.api.controller;

import com.swith.api.request.AuthMailReq;
import com.swith.api.service.AuthMailService;
import com.swith.api.service.MemberService;
import com.swith.common.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members/auth")
public class AuthMailController {

    @Autowired
    AuthMailService authMailService;

    @Autowired
    MemberService memberService;

    @PostMapping("/email")
    public ResponseEntity<BaseResponse> sendAuthMail(@RequestBody AuthMailReq authMailReq) {
        if (memberService.getMemberByEmail(authMailReq.getEmail()) != null) {
            return ResponseEntity.status(200).body(new BaseResponse(false, 400, "이미 가입된 이메일"));
        }

        authMailService.sendAuthMail(authMailReq);
        return ResponseEntity.status(200).body(new BaseResponse(true, 200, "이메일 인증번호 전송 성공"));
    }

    @PostMapping("/email/check")
    public ResponseEntity<BaseResponse> getAuthNum(@RequestBody AuthMailReq authMailReq) {
        if (authMailService.getAuthNum(authMailReq) == null) {
            return ResponseEntity.status(200).body(new BaseResponse(false, 404, "이메일 인증번호 전송 내역 없음"));
        } else if (!authMailService.getAuthNum(authMailReq).getAuthNum().equals(authMailReq.getAuthNum())) {
            return ResponseEntity.status(200).body(new BaseResponse(false, 409, "이메일 인증 확인 실패"));
        } else {
            return ResponseEntity.status(200).body(new BaseResponse(true, 200, "이메일 인증 확인 성공"));
        }
    }
}
