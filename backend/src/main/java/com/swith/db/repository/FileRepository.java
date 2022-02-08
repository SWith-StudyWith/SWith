package com.swith.db.repository;

import com.swith.db.entity.File;
import com.swith.db.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findByStudy(Study study);
    Optional<File> findByStudyAndFileId(Study study, long fileId);
}
