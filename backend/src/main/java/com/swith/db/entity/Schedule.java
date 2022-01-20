package com.swith.db.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;
    private String content;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToOne(targetEntity = Study.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    @Builder
    public Schedule(String content, LocalDateTime startDate, LocalDateTime endDate, Study study) {
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.study = study;
    }

//    public void addSchedule(Study study) {
//        if (this.study != null) {
//            this.study.getScheduleList().remove(this);
//        }
//        this.study = study;
//        study.getScheduleList().add(this);
//    }
}
