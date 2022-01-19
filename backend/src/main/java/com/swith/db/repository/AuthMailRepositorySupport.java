package com.swith.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.swith.db.entity.AuthMail;
import com.swith.db.entity.QAuthMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AuthMailRepositorySupport {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QAuthMail qAuthMail = QAuthMail.authMail;

    public Optional<AuthMail> getAuthNum(String email) {
        AuthMail authMail = jpaQueryFactory.select(qAuthMail).from(qAuthMail)
                .where(qAuthMail.email.eq(email))
                .orderBy(qAuthMail.authMailId.desc())
                .limit(1)
                .fetchOne();
        if (authMail == null) return Optional.empty();
        return Optional.ofNullable(authMail);
    }
}
