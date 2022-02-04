package com.swith.api.controller;

import com.swith.api.dto.study.request.StudyCodeReq;
import com.swith.api.dto.study.request.StudyInfoReq;
import com.swith.api.dto.study.response.MemberStudyRes;
import com.swith.api.dto.study.response.StudyInfoRes;
import com.swith.api.dto.study.response.StudyRes;
import com.swith.api.service.MemberService;
import com.swith.api.service.MemberStudyService;
import com.swith.api.service.StudyService;
import com.swith.common.response.BaseDataResponse;
import com.swith.common.response.BaseResponse;
import com.swith.db.entity.Member;
import com.swith.db.entity.MemberStudy;
import com.swith.db.entity.Study;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/studies")
public class StudyController {

    @Autowired
    private StudyService studyService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberStudyService memberStudyService;

    @PostMapping
    public ResponseEntity<BaseResponse> createStudy(StudyInfoReq studyInfoReq,
                                                    @RequestPart(value = "studyImage", required = false) MultipartFile multipartFile) {
        log.debug("createStudy - {}", studyInfoReq);
        Member member = memberService.getMemberByAuthentication();
        // 스터디 생성
        Study study = Study.builder().name(studyInfoReq.getStudyName()).goal(studyInfoReq.getStudyGoal())
                .isUsed("N").build();
        try {
            study = studyService.insertStudy(study, studyInfoReq, multipartFile);
        } catch (IOException e) {
            return ResponseEntity.status(200).body(new BaseResponse(false, 400, "스터디 생성 실패"));
        }
        File tempFile = new File("temp");
        if (tempFile.exists() && !tempFile.delete()) log.debug("createStudy - tempFile.delete() failed");
        // 생성된 스터디에 회원을 가입
        memberStudyService.joinStudy(member, study);
        return ResponseEntity.status(200).body(new BaseResponse(true, 200, "스터디 생성 성공"));
    }
    
    @PostMapping("/join")
    public ResponseEntity<BaseResponse> joinStudy(@RequestBody StudyCodeReq studyCodeReq) {

        // 참여 코드에 해당하는 스터디 불러오기
        Study study = studyService.getStudyByCode(studyCodeReq.getCode());
        if (study == null) {
            return ResponseEntity.status(200).body(new BaseResponse(false, 400, "참여 코드에 해당하는 스터디 내역 없음"));
        }

        // 이미 가입되었는지 확인
        Member member = memberService.getMemberByAuthentication();
        MemberStudy memberStudy = memberStudyService.getMemberStudyCheck(member, study);
        if (memberStudy != null) {
            return ResponseEntity.status(200).body(new BaseResponse(false, 409, "해당 스터디에 이미 가입된 상태"));
        }

        // 스터디 가입
        memberStudyService.joinStudy(member, study);
        return ResponseEntity.status(200).body(new BaseResponse(true, 200, "스터디 가입 성공"));
    }

    @GetMapping
    public ResponseEntity<BaseDataResponse<List<MemberStudyRes>>> getStudyList() {

        Member member = memberService.getMemberByAuthentication();
        List<MemberStudyRes> studyList = studyService.getStudyList(member);

        return ResponseEntity.status(200).body(new BaseDataResponse<>(true, 200, "스터디 목록 조회 성공", studyList));
    }

    @GetMapping("/{studyId}")
    public ResponseEntity<BaseDataResponse<StudyInfoRes>> getStudyDetail(@PathVariable Long studyId) {

        Member member = memberService.getMemberByAuthentication();
        Study study = studyService.getStudyById(studyId);
        MemberStudy memberStudy = memberStudyService.getMemberStudyCheck(member, study);
        if (memberStudy == null) {
            return ResponseEntity.status(200).body(new BaseDataResponse<>(true, 401, "스터디 정보 조회 권한 없음", null));
        }
        
        StudyInfoRes studyInfoRes = studyService.getStudyDetail(studyId);
        return ResponseEntity.status(200).body(new BaseDataResponse<>(true, 200, "스터디 정보 조회 성공", studyInfoRes));
    }

    @PutMapping("/{studyId}")
    public ResponseEntity<BaseDataResponse<StudyRes>> updateStudy(@PathVariable String studyId,
                                                                  StudyInfoReq studyInfoReq, @RequestPart(value = "studyImage", required = false) MultipartFile multipartFile) {
        log.debug("updateStudy - {}: {}", studyId, studyInfoReq);
//        log.debug("updateStudy - file name: {}, file size: {}, content type: {}", multipartFile.getOriginalFilename(),
//                multipartFile.getSize(), multipartFile.getContentType());
        Study study = studyService.getStudyById(Long.parseLong(studyId));
        // 존재하지 않는 스터디
        if (study == null) {
            return ResponseEntity.status(200).body(new BaseDataResponse<>(false,
                    400, "존재하지 않는 스터디", null));
        }

        BaseDataResponse<StudyRes> studyInfo = null;
        try {
            study = studyService.updateStudy(study, studyInfoReq, multipartFile);
        } catch (IOException e) {
            studyInfo = new BaseDataResponse<>(false, 408, "파일 업로드 실패",
                    new StudyRes(study.getName(), study.getGoal(), study.getImgUrl()));
        }
        File tempFile = new File("temp");
        if (tempFile.exists() && !tempFile.delete()) log.debug("updateStudy - tempFile.delete() failed");

        // 스터디 정보 수정 실패
        if (study == null) {
            return ResponseEntity.status(200).body(new BaseDataResponse<>(false,
                    404, "스터디 정보 수정 실패", null));
        }
        // 파일 업로드 실패
        if (studyInfo != null) return ResponseEntity.status(200).body(studyInfo);
        // 스터디 정보 수정 성공
        studyInfo = new BaseDataResponse<>(true, 200, "스터디 정보 수정 성공",
                new StudyRes(study.getName(), study.getGoal(), study.getImgUrl()));
        return ResponseEntity.status(200).body(studyInfo);
    }

    @DeleteMapping("/{studyId}")
    public ResponseEntity<BaseResponse> deleteMemberStudy(@PathVariable long studyId) {

        Member member = memberService.getMemberByAuthentication();
        Study study = studyService.getStudyById(studyId);
        studyService.deleteMemberStudy(member, study);

        //해당 스터디에 스터디원 존재 여부 확인
        List<MemberStudy> memberStudyList = memberStudyService.getMemberStudyByStudy(study);
        if (memberStudyList.size() == 0) {
            studyService.deleteStudy(studyId);
        }

        return ResponseEntity.status(200).body(new BaseResponse(true, 200, "스터디 탈퇴 성공"));
    }
}
