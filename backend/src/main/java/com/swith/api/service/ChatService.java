package com.swith.api.service;

import com.swith.api.dto.study.ChatDto;
import com.swith.api.dto.study.response.ChatRes;
import com.swith.db.entity.Study;

import java.util.List;

public interface ChatService {
    void insertChat(ChatDto chatDto);
    List<ChatRes> getChatList(Study study, Long index);
}
