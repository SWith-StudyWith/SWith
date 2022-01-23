package com.swith.api.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberInfoReq {
    private String nickname;
    private String goal;
}
