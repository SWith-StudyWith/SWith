package com.swith.api.dto.study.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class StudyInfoRes {

    private Long studyId;
    private String studyName;
    private String studyGoal;
    private String studyCode;
    private List<KanbanBoardRes> kanbanBoard;

    public StudyInfoRes() {
        kanbanBoard = new ArrayList<>();
    }
}
