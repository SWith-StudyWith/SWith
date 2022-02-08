package com.swith.api.service;

import com.swith.api.dto.study.KanbanDto;
import com.swith.api.dto.study.request.StudyInfoReq;
import com.swith.api.dto.study.response.KanbanBoardRes;
import com.swith.api.dto.study.response.KanbanRes;
import com.swith.api.dto.study.response.MemberStudyRes;
import com.swith.api.dto.study.response.StudyInfoRes;
import com.swith.common.util.FirebaseUtil;
import com.swith.config.FirebaseConfig;
import com.swith.db.entity.Member;
import com.swith.db.entity.Study;
import com.swith.db.entity.Task;
import com.swith.db.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Transactional
@Service
public class StudyServiceImpl implements StudyService {

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private StudyRepositorySupport studyRepositorySupport;

    @Autowired
    private KanbanRepository kanbanRepository;

    @Autowired
    private KanbanRepositorySupport kanbanRepositorySupport;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private MemberStudyRepository memberStudyRepository;

    @Autowired
    private FileServiceImpl fileService;

    @Autowired
    private FirebaseConfig firebaseConfig;

    public HashSet<String> codeSet;

    @PostConstruct
    public void init() {
        codeSet = new HashSet<>();
        List<Study> studyList = studyRepository.findAll();
        for (Study study: studyList) codeSet.add(study.getCode());
    }

    @Override
    public Study insertStudy(Study study, StudyInfoReq studyInfoReq, MultipartFile multipartFile) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            Tika tika = new Tika();
            String mimeType = tika.detect(multipartFile.getInputStream());
            // MIME type이 image인지 확인, file size가 2MB이내인지 확인
            if (mimeType.startsWith("image") && multipartFile.getSize() < 2 * Math.pow(10, 6)) {
                study.setImgUrl(fileService.upload(multipartFile, firebaseConfig.getStudy_storage_path(),
                        study.getImgUrl(), "media"));
            }else throw new IOException();
        }
        study.setCode(generateCode());
        studyRepository.save(study);
        codeSet.add(study.getCode());
        return study;
    }

    public String generateCode(){
        String generatedString = RandomStringUtils.randomAlphanumeric(32);
        while(codeSet.contains(generatedString))
            generatedString = RandomStringUtils.randomAlphanumeric(32);
        log.debug("generateCode - {}", generatedString);
        return generatedString;
    }

    @Override
    public Study getStudyByCode(String code) {
        return studyRepository.findByCode(code).orElse(null);
    }

    @Override
    public List<MemberStudyRes> getStudyList(Member member) {
        return studyRepositorySupport.getStudyList(member).orElse(null);
    }

    @Override
    public StudyInfoRes getStudyDetail(Long studyId) {
        //스터디 정보 가져오기
        Study study = studyRepository.findById(studyId).orElse(null);

        StudyInfoRes studyInfoRes = new StudyInfoRes();
        studyInfoRes.setStudyId(study.getStudyId());
        studyInfoRes.setStudyCode(study.getCode());
        studyInfoRes.setStudyGoal(study.getGoal());
        studyInfoRes.setStudyName(study.getName());
        studyInfoRes.setStudyImgUrl(study.getImgUrl());

        //태스크 목록 가져오기
        List<Task> taskList = taskRepository.findAll();
        for (int i = 0; i < taskList.size(); i++) {
            KanbanBoardRes kanbanBoardRes = new KanbanBoardRes();
            kanbanBoardRes.setTaskId(taskList.get(i).getTaskId());
            kanbanBoardRes.setTaskName(taskList.get(i).getName());
            studyInfoRes.getKanbanBoard().add(kanbanBoardRes);
        }

        //칸반 보드 목록 가져오기
        List<KanbanDto> kanbanList = kanbanRepositorySupport.getKanban(study).orElse(null);
        for (int i = 0; i < kanbanList.size(); i++) {
            KanbanDto kanbanDto = kanbanList.get(i);
            studyInfoRes.getKanbanBoard().get((int)(kanbanDto.getTaskId() - 1)).getKanban().add(new KanbanRes(kanbanDto.getKanbanId(), kanbanDto.getContent()));
        }

        return studyInfoRes;
    }

    @Override
    public Study getStudyById(Long studyId) {
        return studyRepository.findById(studyId).orElse(null);
    }

    @Override
    public Study updateStudy(Study study, StudyInfoReq studyInfoReq, MultipartFile multipartFile) throws IOException {
        study.setName(studyInfoReq.getStudyName());
        study.setGoal(studyInfoReq.getStudyGoal());
        // upload할 image가 존재하는 경우
        if (studyInfoReq.isUpdated()) {
            if (multipartFile != null && !multipartFile.isEmpty()) {
                Tika tika = new Tika();
                String mimeType = tika.detect(multipartFile.getInputStream());
                // MIME type이 image인지 확인, file size가 2MB이내인지 확인
                if (mimeType.startsWith("image") && multipartFile.getSize() < 2 * Math.pow(10, 6)) {
                    study.setImgUrl(fileService.upload(multipartFile, firebaseConfig.getStudy_storage_path(),
                            study.getImgUrl(), "media"));
                }else throw new IOException();
            } else if (study.getImgUrl() != null) {
                log.debug("updateStudy - file path: {}", FirebaseUtil.convertUrlToFilePath(study.getImgUrl()));
                fileService.deleteFile(FirebaseUtil.convertUrlToFilePath(study.getImgUrl()));
                study.setImgUrl(null);
            }
        }
        return study;
    }

    @Override
    public void deleteMemberStudy(Member member, Study study) {
        memberStudyRepository.deleteByMemberAndStudy(member, study);
    }

    @Override
    public void updateStudyIsUsed(Study study, String isUsed, Member member) {
        study.setIsUsed(isUsed);
        if (member != null) {
            study.setLockUseMember(member);
            study.setLockCreatedAt(LocalDateTime.now());
        }
    }

    @Override
    public void deleteStudy(Long studyId) {
        Study study = studyRepository.findById(studyId).orElse(null);
        if (study != null && study.getImgUrl() != null) {
            try {
                fileService.deleteFile(FirebaseUtil.convertUrlToFilePath(study.getImgUrl()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        studyRepository.deleteById(studyId);
    }
}
