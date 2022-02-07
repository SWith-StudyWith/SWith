package com.swith.api.controller;

import com.swith.api.dto.study.ChatDto;
import com.swith.api.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    @Autowired
    private ChatService chatService;

    private final SimpMessagingTemplate template;

//    @SendTo("/send")

    @MessageMapping("/receive")
    public void SocketHandler(ChatDto chatDto) {
        String studyId = chatDto.getStudyId();
        String memberId = chatDto.getMemberId();
        String imgUrl = chatDto.getImgUrl();
        String nickname = chatDto.getNickname();
        String content = chatDto.getContent();

        LocalDateTime now = LocalDateTime.now();
        String createdAt = now.format(DateTimeFormatter.ofPattern("yy/MM/dd a hh시 mm분"));

        ChatDto result = new ChatDto(studyId, memberId, imgUrl, nickname, content, createdAt);
        chatService.insertChat(result);
        template.convertAndSend("/send/" + studyId, result);
    }
}
