package com.swith.db.repository;

import com.swith.db.entity.AuthMail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthMailRepository extends JpaRepository<AuthMail, Long> {
}
