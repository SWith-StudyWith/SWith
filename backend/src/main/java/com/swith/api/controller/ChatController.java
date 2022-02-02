package com.swith.api.controller;

import com.swith.api.dto.study.ChatDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/receive")

    @SendTo("/send")

    public ChatDto SocketHandler(ChatDto chatDto) {
        String studyId = chatDto.getStudyId();
        String memberId = chatDto.getMemberId();
        String imgUrl = chatDto.getImgUrl();
        String nickname = chatDto.getNickname();
        String content = chatDto.getContent();

        ChatDto result = new ChatDto(studyId, memberId, imgUrl, nickname, content);
        return result;
    }
}
