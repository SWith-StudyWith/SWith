package com.swith.db.repository;

import com.swith.db.entity.Member;
import com.swith.db.entity.Memo;
import com.swith.db.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    Optional<List<Memo>> findByStudyAndMember(Study study, Member member);
    void deleteByStudyAndMember(Study study, Member member);
}
