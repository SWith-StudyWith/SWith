package com.swith.api.dto.study.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StudyRes {
    private String studyName;
    private String studyGoal;
    private String studyImgURL;
}
