package com.swith.api.dto.member.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberInfoRes {
    private String email;
    private String nickname;
    private String goal;
}
