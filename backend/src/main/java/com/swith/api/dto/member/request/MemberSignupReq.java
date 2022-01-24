package com.swith.api.dto.member.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberSignupReq extends MemberReq {
    private String kakaoId;
    private String googleId;
    private String nickname;
}
