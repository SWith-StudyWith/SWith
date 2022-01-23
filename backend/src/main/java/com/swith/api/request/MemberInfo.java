package com.swith.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberInfo {
    private String email;
    private String nickname;
    private String gole;
}
