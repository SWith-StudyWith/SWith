package com.swith.api.service;

import com.swith.api.dto.study.response.StudyMemberRes;
import com.swith.db.entity.Member;
import com.swith.db.entity.MemberStudy;
import com.swith.db.entity.Study;
import com.swith.db.repository.MemberStudyRepository;
import com.swith.db.repository.MemberStudyRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class MemberStudyServiceImpl implements MemberStudyService {

    @Autowired
    MemberStudyRepository memberStudyRepository;

    @Autowired
    MemberStudyRepositorySupport memberStudyRepositorySupport;

    @Override
    public MemberStudy getMemberStudyCheck(Member member, Study study) {
        return memberStudyRepository.findByMemberAndStudy(member, study).orElse(null);
    }

    @Override
    public void joinStudy(Member member, Study study) {
        MemberStudy memberStudy = MemberStudy.builder().member(member).study(study).build();
        memberStudyRepository.save(memberStudy);
    }

    @Override
    public List<MemberStudy> getMemberStudyByStudy(Study study) {
        return memberStudyRepository.findByStudy(study);
    }

    @Override
    public List<StudyMemberRes> getStudyMemberList(Study study) {
        return memberStudyRepositorySupport.getStudyMemberList(study).orElse(null);
    }
}
