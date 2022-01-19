package com.swith.api.service;

import com.swith.api.request.AuthMailReq;
import com.swith.common.util.MailUtil;
import com.swith.db.entity.AuthMail;
import com.swith.db.repository.AuthMailRepository;
import com.swith.db.repository.AuthMailRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class AuthMailServiceImpl implements AuthMailService {

    @Autowired
    MailUtil mailUtil;

    @Autowired
    AuthMailRepository authMailRepository;

    @Autowired
    AuthMailRepositorySupport authMailRepositorySupport;

    @Override
    public void sendAuthMail(AuthMailReq authMailReq) {
        //인증번호 만들기
        String authNum = getAuthNum();

        //인증번호 DB에 넣기
        authMailRepository.save(new AuthMail(authMailReq.getEmail(), authNum));

        //인증번호 이메일 전송
        mailUtil.sendAuthNum(authMailReq.getEmail(), authNum);
    }

    @Override
    public AuthMail getAuthNum(AuthMailReq authMailReq) {
        AuthMail authMail = authMailRepositorySupport.getAuthNum(authMailReq.getEmail()).orElse(null);
        return authMail;
    }

    private String getAuthNum(){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        String str = "";

        int idx = 0;
        for (int i = 0; i < 6; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }
}
