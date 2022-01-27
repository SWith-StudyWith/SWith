package com.swith.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.swith.api.dto.study.KanbanDto;
import com.swith.api.dto.study.QKanbanDto;
import com.swith.db.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class KanbanRepositorySupport {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QKanban qKanban = QKanban.kanban;
    QTask qTask = QTask.task;

    public Optional<List<KanbanDto>> getKanban(Study study) {
        List<KanbanDto> list = jpaQueryFactory.select(new QKanbanDto(qKanban.kanbanId, qKanban.content, qTask.taskId, qTask.name))
                .from(qKanban)
                .leftJoin(qTask).on(qKanban.task.eq(qTask))
                .where(qKanban.study.eq(study))
                .orderBy(qKanban.kanbanId.asc())
                .fetch();

        return Optional.ofNullable(list);
    }
}
