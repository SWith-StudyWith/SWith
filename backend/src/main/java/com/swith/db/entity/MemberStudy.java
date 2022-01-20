package com.swith.db.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class MemberStudy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberStudyId;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(targetEntity = Study.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    @Builder
    public MemberStudy(Member member, Study study) {
        this.member = member;
        this.study = study;
    }

//    public void addMember(Member member) {
//        if (this.member != null) {
//            this.member.getMemberStudyList().remove(this);
//        }
//        this.member = member;
//        member.getMemberStudyList().add(this);
//    }
}
