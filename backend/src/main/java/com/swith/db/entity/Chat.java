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
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long chatId;
    private String content;
    private LocalDateTime createdAt;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(targetEntity = Study.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    @Builder
    public Chat(long chatId, String content, LocalDateTime createdAt, Member member, Study study) {
        this.chatId = chatId;
        this.content = content;
        this.createdAt = createdAt;
        this.member = member;
        this.study = study;
    }

    public void addChat(Study study) {
        if (this.study != null) {
            this.study.getFileList().remove(this);
        }
        this.study = study;
        study.getChatList().add(this);
    }
}
