package com.swith.db.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberStudy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberStudyId;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(targetEntity = Study.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    @Builder
    public MemberStudy(long memberStudyId, Member member, Study study) {
        this.memberStudyId = memberStudyId;
        this.member = member;
        this.study = study;
    }

    public void addMember(Member member) {
        if (this.member != null) {
            this.member.getMemberStudyList().remove(this);
        }
        this.member = member;
        member.getMemberStudyList().add(this);
    }
}
