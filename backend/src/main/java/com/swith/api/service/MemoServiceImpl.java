package com.swith.api.service;

import com.swith.api.dto.study.request.MemoUpdateReq;
import com.swith.db.entity.Member;
import com.swith.db.entity.Memo;
import com.swith.db.entity.Study;
import com.swith.db.repository.MemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class MemoServiceImpl implements MemoService {

    @Autowired
    private MemoRepository memoRepository;

    @Override
    public List<Memo> getMemoList(Study study, Member member) {
        return memoRepository.findByStudyAndMember(study, member).orElse(null);
    }

    @Override
    public void deleteMemo(Study study, Member member) {
        memoRepository.deleteByStudyAndMember(study, member);
    }

    @Override
    public void insertMemo(Study study, Member member, List<MemoUpdateReq> memoList) {
        for (int i = 0; i < memoList.size(); i++) {
            MemoUpdateReq memo = memoList.get(i);
            memoRepository.save(Memo.builder().study(study).member(member)
                    .content(memo.getContent()).color(memo.getColor()).transform(memo.getTransform()).build());
        }
    }
}
