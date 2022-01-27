package com.swith.api.dto.study;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KanbanDto {
    private Long kanbanId;
    private String content;
    private Long taskId;
    private String taskName;

    @QueryProjection
    public KanbanDto(Long kanbanId, String content, Long taskId, String taskName) {
        this.kanbanId = kanbanId;
        this.content = content;
        this.taskId = taskId;
        this.taskName = taskName;
    }
}
