package com.swith.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.swith.api.dto.study.response.MemberStudyRes;
import com.swith.api.dto.study.response.QMemberStudyRes;
import com.swith.db.entity.Member;
import com.swith.db.entity.QMemberStudy;
import com.swith.db.entity.QStudy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class StudyRepositorySupport {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QMemberStudy qMemberStudy = QMemberStudy.memberStudy;
    QStudy qStudy = QStudy.study;

    public Optional<List<MemberStudyRes>> getStudyList(Member member) {
        List<MemberStudyRes> list = jpaQueryFactory.select(new QMemberStudyRes(qStudy.studyId, qStudy.name, qStudy.code, qStudy.goal, qStudy.imgUrl))
                .from(qMemberStudy)
                .leftJoin(qStudy).on(qMemberStudy.study.eq(qStudy))
                .where(qMemberStudy.member.eq(member))
                .orderBy(qMemberStudy.memberStudyId.asc())
                .fetch();

        return Optional.ofNullable(list);
    }

}
