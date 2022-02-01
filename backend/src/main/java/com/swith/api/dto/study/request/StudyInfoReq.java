package com.swith.api.dto.study.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StudyInfoReq {
    private String studyName;
    private String studyGoal;
    private boolean updated;
}
