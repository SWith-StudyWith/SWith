package com.swith.api.service;

import com.swith.api.dto.study.response.StudyMemberRes;
import com.swith.db.entity.Member;
import com.swith.db.entity.MemberStudy;
import com.swith.db.entity.Study;

import java.util.List;

public interface MemberStudyService {
    MemberStudy getMemberStudyCheck(Member member, Study study);
    void joinStudy(Member member, Study study);
    List<MemberStudy> getMemberStudyByStudy(Study study);
    List<StudyMemberRes> getStudyMemberList(Study study);
}
