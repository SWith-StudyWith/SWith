package com.swith.api.service;

import com.swith.db.entity.Member;

public interface AuthService {

    Member loginByKakao(String authCode);
//    Member loginByGoogle(String authCode);
}
