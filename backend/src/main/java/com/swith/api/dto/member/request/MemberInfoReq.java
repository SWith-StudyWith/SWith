package com.swith.api.dto.member.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class MemberInfoReq {
    private String nickname;
    private String goal;
    private MultipartFile profileImg;
    private boolean updated;
}
