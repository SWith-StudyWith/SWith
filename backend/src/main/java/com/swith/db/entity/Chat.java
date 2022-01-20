package com.swith.db.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;
    private String content;
    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(targetEntity = Study.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    @Builder
    public Chat(String content, LocalDateTime createdAt, Member member, Study study) {
        this.content = content;
        this.createdAt = createdAt;
        this.member = member;
        this.study = study;
    }

//    public void addChat(Study study) {
//        if (this.study != null) {
//            this.study.getChatList().remove(this);
//        }
//        this.study = study;
//        study.getChatList().add(this);
//    }
}
