package com.swith.db.repository;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.swith.api.dto.study.response.ChatRes;
import com.swith.api.dto.study.response.QChatRes;
import com.swith.db.entity.QChat;
import com.swith.db.entity.QMember;
import com.swith.db.entity.Study;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ChatRepositorySupport  {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QChat qChat = QChat.chat;
    QMember qMember = QMember.member;

    public Optional<List<ChatRes>> getStudyChatList(Study study, Long index) {

        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                , qChat.createdAt
                , ConstantImpl.create("%y/%m/%d %I:%i %p"));

        List<ChatRes> list = jpaQueryFactory.select(new QChatRes(qMember.memberId, qMember.imgUrl, qMember.nickname, qChat.content, formattedDate))
                .from(qChat)
                .leftJoin(qMember).on(qChat.member.eq(qMember))
                .where(qChat.study.eq(study))
                .orderBy(qChat.chatId.desc())
                .offset(index)
                .limit(15)
                .fetch();

        return Optional.ofNullable(list);
    }
}
