package com.swith.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.swith.api.dto.study.response.QStudyMemberRes;
import com.swith.api.dto.study.response.StudyMemberRes;
import com.swith.db.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberStudyRepositorySupport {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QMemberStudy qMemberStudy = QMemberStudy.memberStudy;
    QMember qMember = QMember.member;

    public Optional<List<StudyMemberRes>> getStudyMemberList(Study study) {
        List<StudyMemberRes> list = jpaQueryFactory.select(new QStudyMemberRes(qMember.memberId, qMember.imgUrl, qMember.nickname, qMember.email))
                .from(qMemberStudy)
                .leftJoin(qMember).on(qMemberStudy.member.eq(qMember))
                .where(qMemberStudy.study.eq(study).and(qMember.isDeleted.eq("N")))
                .orderBy(qMemberStudy.memberStudyId.asc())
                .fetch();

        return Optional.ofNullable(list);
    }

    public Long getCountByStudyId(Long studyId) {
        return jpaQueryFactory.select(qMemberStudy.count())
                .from(qMemberStudy)
                .where(qMemberStudy.study.studyId.eq(studyId))
                .fetchOne();
    }
}
