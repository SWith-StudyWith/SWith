package com.swith.db.repository;

import com.swith.db.entity.File;
import com.swith.db.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findByStudy(Study study);
}
