package com.swith.api.service;

import com.swith.api.dto.study.KanbanDto;
import com.swith.api.dto.study.response.KanbanBoardRes;
import com.swith.api.dto.study.response.KanbanRes;
import com.swith.api.dto.study.response.MemberStudyRes;
import com.swith.api.dto.study.response.StudyInfoRes;
import com.swith.db.entity.Member;
import com.swith.db.entity.Study;
import com.swith.db.entity.Task;
import com.swith.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
}
