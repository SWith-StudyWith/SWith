package com.swith.api.controller;

import com.swith.api.dto.study.response.ChatRes;
import com.swith.api.service.ChatService;
import com.swith.api.service.StudyService;
import com.swith.common.response.BaseDataResponse;
import com.swith.db.entity.Study;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/studies")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private StudyService studyService;

    @GetMapping("/{studyId}/chat")
    public ResponseEntity<BaseDataResponse> getStudyIsUsed(@PathVariable long studyId, @RequestParam long index) {

        Study study = studyService.getStudyById(studyId);
        List<ChatRes> result = chatService.getChatList(study, index);

        return ResponseEntity.status(200).body(new BaseDataResponse(true, 200, "채팅 내역 조회 성공", result));
    }
}
