package com.swith.api.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberReadRes {
    private String email;
    private String nickname;
    private String goal;
}
