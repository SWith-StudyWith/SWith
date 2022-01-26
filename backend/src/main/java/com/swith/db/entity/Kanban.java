package com.swith.db.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Kanban {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long kanbanId;

    @ManyToOne(targetEntity = Study.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    @OneToOne(targetEntity = Task.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    private String content;

    @Builder
    public Kanban(Study study, Task task, String content) {
        this.study = study;
        this.task = task;
        this.content = content;
    }
}
