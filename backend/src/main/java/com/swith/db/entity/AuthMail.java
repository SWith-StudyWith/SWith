package com.swith.db.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class AuthMail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authMailId;
    private String email;
    private String authNum;

    @Builder
    public AuthMail(String email, String authNum) {
        this.email = email;
        this.authNum = authNum;
    }
}
