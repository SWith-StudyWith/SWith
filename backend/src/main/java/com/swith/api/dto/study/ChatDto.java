package com.swith.api.dto.study;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChatDto {
    private String studyId;
    private String memberId;
    private String imgUrl;
    private String nickname;
    private String content;
    private String createdAt;
}
