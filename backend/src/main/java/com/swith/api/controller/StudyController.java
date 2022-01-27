package com.swith.api.controller;

import com.swith.api.dto.study.request.StudyCodeReq;
import com.swith.api.dto.study.response.MemberStudyRes;
import com.swith.api.dto.study.response.StudyInfoRes;
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

    @PostMapping("/join")
    public ResponseEntity<BaseResponse> findPassword(@RequestBody StudyCodeReq studyCodeReq) {

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
    public ResponseEntity<BaseDataResponse<StudyInfoRes>> getStudyDetail(@PathVariable String studyId) {

        StudyInfoRes studyInfoRes = studyService.getStudyDetail(Long.parseLong(studyId));

        return ResponseEntity.status(200).body(new BaseDataResponse<>(true, 200, "스터디 정보 조회 성공", studyInfoRes));
    }
}
