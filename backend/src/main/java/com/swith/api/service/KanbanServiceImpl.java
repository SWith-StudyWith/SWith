package com.swith.api.service;

import com.swith.api.dto.study.request.KanbanUpdateReq;
import com.swith.db.entity.Kanban;
import com.swith.db.entity.Study;
import com.swith.db.repository.KanbanRepository;
import com.swith.db.repository.StudyRepository;
import com.swith.db.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class KanbanServiceImpl implements KanbanService {

    @Autowired
    private KanbanRepository kanbanRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void deleteKanban(Study study) {
        kanbanRepository.deleteByStudy(study);
    }

    @Override
    public void insertKanban(List<KanbanUpdateReq> kanbanUpdateReqList) {
        for (int i = 0; i < kanbanUpdateReqList.size(); i++) {
            KanbanUpdateReq temp = kanbanUpdateReqList.get(i);
            kanbanRepository.save(Kanban.builder()
                    .study(studyRepository.findById(temp.getStudyId()).orElse(null))
                    .task(taskRepository.findById(temp.getTaskId()).orElse(null))
                    .content(temp.getContent())
                    .build());
        }
    }
}
