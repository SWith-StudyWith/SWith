package com.swith.api.dto.member.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberReq {
    private String email;
    private String password;
}
