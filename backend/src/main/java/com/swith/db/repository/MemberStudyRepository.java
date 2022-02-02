package com.swith.db.repository;

import com.swith.db.entity.Member;
import com.swith.db.entity.MemberStudy;
import com.swith.db.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberStudyRepository extends JpaRepository<MemberStudy, Long> {
    Optional<MemberStudy> findByMemberAndStudy(Member member, Study study);
    void deleteByMemberAndStudy(Member member, Study study);
    List<MemberStudy> findByStudy(Study study);
    List<MemberStudy> findByMember(Member member);
}
