package com.swith.api.dto.study.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class KanbanBoardRes {
    Long taskId;
    String taskName;
    List<KanbanRes> kanban;

    public KanbanBoardRes() {
        kanban = new ArrayList<>();
    }
}
