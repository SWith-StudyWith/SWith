package com.swith.api.service;

import com.swith.api.dto.study.ChatDto;
import com.swith.api.dto.study.response.ChatRes;
import com.swith.db.entity.Chat;
import com.swith.db.entity.Study;
import com.swith.db.repository.ChatRepository;
import com.swith.db.repository.ChatRepositorySupport;
import com.swith.db.repository.MemberRepository;
import com.swith.db.repository.StudyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private ChatRepositorySupport chatRepositorySupport;

    @Override
    public void insertChat(ChatDto chatDto) {
        chatRepository.save(Chat.builder()
                .member(memberRepository.findById(Long.parseLong(chatDto.getMemberId())).orElse(null))
                .study(studyRepository.findById(Long.parseLong(chatDto.getStudyId())).orElse(null))
                .content(chatDto.getContent())
                .build());
    }

    @Override
    public List<ChatRes> getChatList(Study study, Long index) {
        return chatRepositorySupport.getStudyChatList(study, index).orElse(null);
    }
}
