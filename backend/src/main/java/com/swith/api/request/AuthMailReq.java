package com.swith.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthMailReq {
    String email;
    String authNum;
}
