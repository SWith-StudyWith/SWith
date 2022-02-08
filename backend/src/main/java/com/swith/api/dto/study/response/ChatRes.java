package com.swith.api.dto.study.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChatRes {

    private Long memberId;
    private String imgUrl;
    private String nickname;
    private String content;
    private String createdAt;

    @QueryProjection
    public ChatRes(Long memberId, String imgUrl, String nickname, String content, String createdAt) {
        this.memberId = memberId;
        this.imgUrl = imgUrl;
        this.nickname = nickname;
        this.content = content;
        this.createdAt = createdAt;
    }
}
