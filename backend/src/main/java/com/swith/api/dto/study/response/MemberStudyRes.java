package com.swith.api.dto.study.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberStudyRes {

    private Long studyId;
    private String studyName;
    private String studyCode;
    private String studyGoal;
    private String studyImgURL;

    @QueryProjection
    public MemberStudyRes(Long studyId, String studyName, String studyCode, String studyGoal, String studyImgURL) {
        this.studyId = studyId;
        this.studyName = studyName;
        this.studyCode = studyCode;
        this.studyGoal = studyGoal;
        this.studyImgURL = studyImgURL;
    }
}
