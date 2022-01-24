package com.swith.api.service;

import com.swith.api.dto.member.request.AuthMailReq;
import com.swith.db.entity.AuthMail;

public interface AuthMailService {

    void sendAuthMail(AuthMailReq authMailReq);
    AuthMail getAuthNum(AuthMailReq authMailReq);
}
