package com.swith.db.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Memo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memoId;

    @ManyToOne(targetEntity = Study.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String content;
    private Long color;
    private String transform;

    @Builder
    public Memo(Study study, Member member, String content, Long color, String transform) {
        this.study = study;
        this.member = member;
        this.content = content;
        this.color = color;
        this.transform = transform;
    }
}
