package com.swith.api.service;

import com.swith.db.entity.Study;

public interface StudyService {

    Study getStudyByCode(String studyCode);
}
