package com.swith.api.controller;

import com.swith.api.dto.study.request.KanbanUpdateReq;
import com.swith.api.dto.study.response.FileRes;
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
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<BaseDataResponse<List<FileRes>>> getStudyFileList(@PathVariable long studyId) {
        List<com.swith.db.entity.File> fileList = fileService.getStudyFileList(studyService.getStudyById(studyId));
        List<FileRes> fileResList = fileList.stream().map(file -> new FileRes(file.getFileId(), file.getOriginName(),
                file.getFileUrl(), file.getFileSize())).collect(Collectors.toList());
        log.debug("getStudyFileList - {}", fileResList);
        return ResponseEntity.status(200).body(new BaseDataResponse<>(true, 200, "스터디 파일 목록 조회 성공", fileResList));
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
    public ResponseEntity<BaseResponse> getStudyIsUsed(@PathVariable long studyId) {

        //칸반보드가 수정중인지 확인
        Study study = studyService.getStudyById(studyId);

        if (study.getIsUsed().equals("N")) {
            studyService.updateStudyIsUsed(study, "Y");
            return ResponseEntity.status(200).body(new BaseResponse(true, 200, "칸반보드 수정 가능"));
        } else {
            return ResponseEntity.status(200).body(new BaseResponse(true, 400, "이미 칸반보드 수정중"));
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
        studyService.updateStudyIsUsed(study, "N");

        return ResponseEntity.status(200).body(new BaseResponse(true, 200, "칸반보드 수정 성공"));
    }

    @GetMapping("/{studyId}/members")
    public ResponseEntity<BaseDataResponse<List<StudyMemberRes>>> getStudyMemberList(@PathVariable long studyId) {

        Study study = studyService.getStudyById(studyId);
        List<StudyMemberRes> list = memberStudyService.getStudyMemberList(study);

        return ResponseEntity.status(200).body(new BaseDataResponse<>(true, 200, "스터디 회원 목록 조회 성공", list));
    }
}
