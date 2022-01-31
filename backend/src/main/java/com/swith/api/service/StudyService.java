package com.swith.api.service;

import com.swith.api.dto.study.response.MemberStudyRes;
import com.swith.api.dto.study.response.StudyInfoRes;
import com.swith.db.entity.Member;
import com.swith.db.entity.Study;

import java.util.List;

public interface StudyService {

    Study getStudyByCode(String studyCode);
    List<MemberStudyRes> getStudyList(Member member);
    StudyInfoRes getStudyDetail(Long studyId);
    Study getStudyById(Long studyId);
    void deleteMemberStudy(Member member, Study study);
    void updateStudyIsUsed(Study study);
    void deleteStudy(Long studyId);
}
