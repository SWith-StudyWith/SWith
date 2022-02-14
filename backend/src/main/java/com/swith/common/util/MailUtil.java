package com.swith.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Component
public class MailUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void sendAuthNum(String email, String authNum) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            message.setSubject("[Swith] 이메일 인증번호 발송", "UTF-8");
            String htmlContent = "<div class=\"container\" style=\"text-align: center; max-width: 500px; margin-top: 50px; font-family: 'Noto Sans KR';\n" +
                    "    font-size: 14px; font-weight: 500;\">\n" +
                    "      <hr style=\"background-color: #334666; width: 90%; height: 3px; border: none;\">\n" +
                    "      <img src=\"https://ifh.cc/g/eZaKWs.png\" alt=\"\" \n" +
                    "      style=\"width:100px; margin-top: 40px; margin-bottom: 20px;\">\n" +
                    "      <p>SWith 회원가입을 환영합니다. \uD83D\uDC99</p>\n" +
                    "      <p\">아래의 인증번호를 입력한 후, 회원가입 절차를 진행해주세요!</p>\n" +
                    "      <div style=\"background-color: #dce1e9; width: 50%; display: inline-block; border-radius: 10px; margin: 20px;\">\n" +
                    "        <p style=\"font-size: 16px; font-weight: 700;\">" + authNum + "</p>\n" +
                    "      </div>\n" +
                    "      <p>온라인 스터디 서비스 SWith을 이용해주셔서 감사합니다.</p>\n" +
                    "      <hr style=\"background-color: #334666; height: 3px; border: none; margin-top: 40px;\">\n" +
                    "    </div>";
            message.setText(htmlContent, "UTF-8", "html");
            message.setFrom(new InternetAddress("official.swith@gmail.com"));
            message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email));
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            return;
        } catch (MailException e) {
            e.printStackTrace();
            return;
        }
    }

    @Async
    public void sendTempPassword(String email, String tempPassword) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            message.setSubject("[Swith] 임시 비밀번호 발송", "UTF-8");
            String htmlContent = "<div class=\"container\" style=\"text-align: center; width: 100%; max-width: 500px; margin-top: 50px; font-family: 'Noto Sans KR';\n" +
                    "    font-size: 14px; font-weight: 500;\">\n" +
                    "      <hr style=\"background-color: #334666;  height: 3px; border: none;\">\n" +
                    "      <img src=\"https://ifh.cc/g/eZaKWs.png\" alt=\"\" \n" +
                    "      style=\"width:100px; margin-top: 40px; margin-bottom: 20px;\">\n" +
                    "      <p>안녕하세요. SWith 임시 비밀번호 안내 이메일입니다. \uD83D\uDC99</p>\n" +
                    "      <p\">아래의 임시 비밀번호로 로그인 후, 비밀번호를 변경해 주시길 바랍니다.</p>\n" +
                    "      <div style=\"background-color: #dce1e9; width: 50%; display: inline-block; border-radius: 10px; margin: 20px;\">\n" +
                    "        <p style=\"font-size: 16px; font-weight: 700;\">" + tempPassword + "</p>\n" +
                    "      </div>\n" +
                    "      <p>온라인 스터디 서비스 SWith을 이용해주셔서 감사합니다.</p>\n" +
                    "      <hr style=\"background-color: #334666; height: 3px; border: none; margin-top: 40px;\">\n" +
                    "    </div>";
            message.setText(htmlContent, "UTF-8", "html");
            message.setFrom(new InternetAddress("official.swith@gmail.com"));
            message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email));
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            return;
        } catch (MailException e) {
            e.printStackTrace();
            return;
        }
    }
}
