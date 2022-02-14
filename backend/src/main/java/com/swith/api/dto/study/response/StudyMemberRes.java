package com.swith.api.dto.study.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudyMemberRes {

    private Long memberId;
    private String imgUrl;
    private String nickname;
    private String email;
    private String goal;

    @QueryProjection
    public StudyMemberRes(Long memberId, String imgUrl, String nickname, String email, String goal) {
        this.memberId = memberId;
        this.imgUrl = imgUrl;
        this.nickname = nickname;
        this.email = email;
        this.goal = goal;
    }
}
