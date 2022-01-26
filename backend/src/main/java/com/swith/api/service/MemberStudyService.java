package com.swith.api.service;

import com.swith.db.entity.Member;
import com.swith.db.entity.MemberStudy;
import com.swith.db.entity.Study;

public interface MemberStudyService {
    MemberStudy getMemberStudyCheck(Member member, Study study);
    void joinStudy(Member member, Study study);
}
