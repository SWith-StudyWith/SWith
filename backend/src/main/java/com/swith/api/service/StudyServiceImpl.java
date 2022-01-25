package com.swith.api.service;

import com.swith.db.entity.Study;
import com.swith.db.repository.StudyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class StudyServiceImpl implements StudyService {

    @Autowired
    private StudyRepository studyRepository;

    @Override
    public Study getStudyByCode(String code) {
        return studyRepository.findByCode(code).orElse(null);
    }
}
