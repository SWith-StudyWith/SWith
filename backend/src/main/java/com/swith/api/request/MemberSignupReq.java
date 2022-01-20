package com.swith.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSignupReq extends MemberReq{
    private String kakaoId;
    private String googleId;
    private String nickname;
    private String goal;
}
