package com.swith.api.service;

import com.swith.api.dto.study.response.MemberStudyRes;
import com.swith.db.entity.Member;
import com.swith.db.entity.Study;
import com.swith.db.repository.StudyRepository;
import com.swith.db.repository.StudyRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class StudyServiceImpl implements StudyService {

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private StudyRepositorySupport studyRepositorySupport;

    @Override
    public Study getStudyByCode(String code) {
        return studyRepository.findByCode(code).orElse(null);
    }

    @Override
    public List<MemberStudyRes> getStudyList(Member member) {
        return studyRepositorySupport.getStudyList(member).orElse(null);
    }
}
