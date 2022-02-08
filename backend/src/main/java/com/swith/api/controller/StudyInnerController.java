package com.swith.api.controller;

import com.swith.api.dto.study.request.KanbanUpdateReq;
import com.swith.api.dto.study.response.KanbanIsUsedRes;
import com.swith.api.dto.study.response.StudyMemberRes;
import com.swith.api.service.*;
import com.swith.common.response.BaseDataResponse;
import com.swith.common.response.BaseResponse;
import com.swith.db.entity.Member;
import com.swith.db.entity.Study;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/studies")
public class StudyInnerController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private StudyService studyService;

    @Autowired
    private KanbanService kanbanService;

    @Autowired
    private MemberStudyService memberStudyService;

    @Autowired
    private FileService fileService;

    @GetMapping("/{studyId}/files")
    public ResponseEntity<BaseResponse> getStudyFileList(@PathVariable long studyId) {

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{studyId}/files")
    public ResponseEntity<BaseResponse> uploadStudyFiles(@PathVariable long studyId, @RequestParam("files") List<MultipartFile> files) {
        log.debug("uploadStudyFiles - studyId: {}", studyId);
        Member member = memberService.getMemberByAuthentication();
        Study study = studyService.getStudyById(studyId);
        // 다중 파일 업로드, file data 등록
        try {
            fileService.uploadStudyFiles(member, study, files);
        } catch (IOException e) {
            return ResponseEntity.status(200).body(new BaseResponse(false, 400, "스터디 파일 업로드 실패"));
        }
        File tempFile = new File("temp");
        if (tempFile.exists() && !tempFile.delete()) log.debug("updateMember - tempFile.delete() failed");
        // 다중 파일 업로드 성공
        return ResponseEntity.status(200).body(new BaseResponse(true, 200, "스터디 파일 업로드 성공"));
    }

    @GetMapping("/{studyId}/files/{fileId}")
    public ResponseEntity<BaseResponse> downloadStudyFile(@PathVariable long studyId, @PathVariable long fileId) {

        return null;
    }

    @GetMapping("/{studyId}/kanbans")
    public ResponseEntity<BaseDataResponse> getStudyIsUsed(@PathVariable long studyId) {

        //칸반보드가 수정중인지 확인
        Study study = studyService.getStudyById(studyId);
        Member member = memberService.getMemberByAuthentication();

        if (study.getIsUsed().equals("N")) {
            studyService.updateStudyIsUsed(study, "Y", member);
            return ResponseEntity.status(200).body(new BaseDataResponse(true, 200, "칸반보드 수정 가능", null));
        } else {
            LocalDateTime lockDateTime = study.getLockCreatedAt();
            LocalDateTime now = LocalDateTime.now();
            Long time = ChronoUnit.MINUTES.between(lockDateTime, now);

            if (time >= 10) {
                studyService.updateStudyIsUsed(study, "Y", member);
                return ResponseEntity.status(200).body(new BaseDataResponse(true, 200, "칸반보드 수정 가능", null));
            } else {
                KanbanIsUsedRes result = new KanbanIsUsedRes(study.getLockUseMember().getNickname());
                return ResponseEntity.status(200).body(new BaseDataResponse(true, 400, "이미 칸반보드 수정중", result));
            }
        }
    }

    @PutMapping("/{studyId}/kanbans")
    public ResponseEntity<BaseResponse> updateKanban(@PathVariable long studyId, @RequestBody List<KanbanUpdateReq> kanbanUpdateReqList) {

        Study study = studyService.getStudyById(studyId);

        //칸반보드 모두 삭제
        kanbanService.deleteKanban(study);

        //칸반보드 등록
        kanbanService.insertKanban(kanbanUpdateReqList);

        //isUsed 변경
        studyService.updateStudyIsUsed(study, "N", null);

        return ResponseEntity.status(200).body(new BaseResponse(true, 200, "칸반보드 수정 성공"));
    }

    @GetMapping("/{studyId}/members")
    public ResponseEntity<BaseDataResponse<List<StudyMemberRes>>> getStudyMemberList(@PathVariable long studyId) {

        Study study = studyService.getStudyById(studyId);
        List<StudyMemberRes> list = memberStudyService.getStudyMemberList(study);

        return ResponseEntity.status(200).body(new BaseDataResponse<>(true, 200, "스터디 회원 목록 조회 성공", list));
    }
}
