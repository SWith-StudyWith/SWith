package com.swith.db.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;
    private String path;
    private String originName;
    private String saveName;
    private LocalDateTime createdAt;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(targetEntity = Study.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    @Builder
    public File(Long fileId, String path, String originName, String saveName, LocalDateTime createdAt, Member member,
                Study study) {
        this.fileId = fileId;
        this.path = path;
        this.originName = originName;
        this.saveName = saveName;
        this.createdAt = createdAt;
        this.member = member;
        this.study = study;
    }

//    public void addFile(Study study) {
//        if (this.study != null) {
//            this.study.getFileList().remove(this);
//        }
//        this.study = study;
//        study.getFileList().add(this);
//    }
}
