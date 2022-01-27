package com.swith.api.dto.study.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KanbanRes {

    Long kanbanId;
    String content;

    public KanbanRes() {
    }

    public KanbanRes(Long kanbanId, String content) {
        this.kanbanId = kanbanId;
        this.content = content;
    }
}
