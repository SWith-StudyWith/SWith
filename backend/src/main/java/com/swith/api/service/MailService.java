package com.swith.api.service;

import com.swith.api.request.AuthMailReq;

public interface MailService {
    public void sendMail(AuthMailReq authMailReq);
}
