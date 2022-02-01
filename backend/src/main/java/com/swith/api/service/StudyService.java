package com.swith.api.service;

import com.swith.api.dto.study.request.StudyInfoReq;
import com.swith.api.dto.study.response.MemberStudyRes;
import com.swith.api.dto.study.response.StudyInfoRes;
import com.swith.db.entity.Member;
import com.swith.db.entity.Study;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StudyService {

    Study insertStudy(Study study, StudyInfoReq studyInfoReq, MultipartFile multipartFile) throws IOException;
    Study getStudyByCode(String studyCode);
    List<MemberStudyRes> getStudyList(Member member);
    StudyInfoRes getStudyDetail(Long studyId);
    Study getStudyById(Long studyId);
    Study updateStudy(Study study, StudyInfoReq studyInfoReq, MultipartFile multipartFile) throws IOException;
    void deleteMemberStudy(Member member, Study study);
    void updateStudyIsUsed(Study study);
}
