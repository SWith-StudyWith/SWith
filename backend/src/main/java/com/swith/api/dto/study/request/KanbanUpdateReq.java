package com.swith.api.dto.study.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KanbanUpdateReq {
    private Long studyId;
    private Long taskId;
    private String content;
}
