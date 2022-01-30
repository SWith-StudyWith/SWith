package com.swith.api.service;

import com.swith.api.dto.study.request.KanbanUpdateReq;
import com.swith.api.dto.study.response.KanbanBoardRes;
import com.swith.db.entity.Study;

import java.util.List;

public interface KanbanService {

    void deleteKanban(Study study);
    void insertKanban(List<KanbanUpdateReq> kanbanUpdateReqList);
}
