package com.swith.api.controller;

import com.swith.api.service.StudyService;
import com.swith.common.response.BaseResponse;
import com.swith.db.entity.Study;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/studies")
public class StudyInnerController {

    @Autowired
    private StudyService studyService;

    @GetMapping("/{studyId}/kanbans")
    public ResponseEntity<BaseResponse> getStudyIsUsed(@PathVariable long studyId) {

        //칸반보드가 수정중인지 확인
        Study study = studyService.getStudyById(studyId);

        if (study.getIsUsed().equals("N")) {
            studyService.updateStudyIsUsed(study);
            return ResponseEntity.status(200).body(new BaseResponse(true, 200, "칸반보드 수정 가능"));
        } else {
            return ResponseEntity.status(200).body(new BaseResponse(true, 400, "이미 칸반보드 수정중"));
        }
    }
}
