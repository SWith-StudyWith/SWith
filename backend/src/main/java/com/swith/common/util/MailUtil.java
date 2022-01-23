package com.swith.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MailUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void sendAuthNum(String email, String authNum) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("[Swith] 이메일 인증번호 발송");
        message.setText("안녕하세요. Swith 회원가입 이메일 인증번호 안내 이메일 입니다.\n"
                + "인증번호는 " + authNum + " 입니다.");

        javaMailSender.send(message);
    }

    @Async
    public void sendTempPassword(String email, String tempPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("[Swith] 임시 비밀번호 발송");
        message.setText("안녕하세요. Swith 임시 비밀번호 안내 이메일 입니다.\n"
                + "임시 비밀번호는 " + tempPassword + " 입니다.\n"
                + "로그인 후 비밀번호를 변경해 주시길 바랍니다.");

        javaMailSender.send(message);
    }
}
