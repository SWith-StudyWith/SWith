package com.swith.api.dto.member.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthMailReq {
    private String email;
    private String authNum;
}
