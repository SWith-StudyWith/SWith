package com.swith.db.repository;

import com.swith.db.entity.Kanban;
import com.swith.db.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KanbanRepository extends JpaRepository<Kanban, Long> {

    void deleteByStudy(Study study);
}
