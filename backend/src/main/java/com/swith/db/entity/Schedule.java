package com.swith.db.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long scheduleId;
    private String content;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToOne(targetEntity = Study.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    @Builder
    public Schedule(long scheduleId, String content, LocalDateTime startDate, LocalDateTime endDate, Study study) {
        this.scheduleId = scheduleId;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.study = study;
    }

    public void addSchedule(Study study) {
        if (this.study != null) {
            this.study.getScheduleList().remove(this);
        }
        this.study = study;
        study.getScheduleList().add(this);
    }
}
