package com.swith.api.service;

import com.swith.api.dto.study.request.MemoUpdateReq;
import com.swith.db.entity.Member;
import com.swith.db.entity.Memo;
import com.swith.db.entity.Study;

import java.util.List;

public interface MemoService {

    List<Memo> getMemoList(Study study, Member member);
    void deleteMemo(Study study, Member member);
    void insertMemo(Study study, Member member, List<MemoUpdateReq> memoList);
}
