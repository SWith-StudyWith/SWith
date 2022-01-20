package com.swith.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberReq {
    private String email;
    private String password;
}
