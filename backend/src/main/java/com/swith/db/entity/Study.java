package com.swith.db.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Study {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studyId;
    private String name;
    private String code;
    private String goal;
    private String imgUrl;
    private String isUsed;

    @Builder
    public Study(String name, String code, String goal, String imgUrl, String isUsed) {
        this.name = name;
        this.code = code;
        this.goal = goal;
        this.imgUrl = imgUrl;
        this.isUsed = isUsed;
    }

    @OneToMany(
            mappedBy = "study",
            cascade = CascadeType.ALL
    )
    List<MemberStudy> memberStudyList = new ArrayList<>();

    @OneToMany(
            mappedBy = "study",
            cascade = CascadeType.ALL
    )
    List<File> fileList = new ArrayList<>();

    @OneToMany(
            mappedBy = "study",
            cascade = CascadeType.ALL
    )
    List<Chat> chatList = new ArrayList<>();

    @OneToMany(
            mappedBy = "study",
            cascade = CascadeType.ALL
    )
    List<Schedule> scheduleList = new ArrayList<>();
}
