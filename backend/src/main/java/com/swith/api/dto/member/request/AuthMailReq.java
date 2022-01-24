package com.swith.api.dto.member.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthMailReq {
    String email;
    String authNum;
}
