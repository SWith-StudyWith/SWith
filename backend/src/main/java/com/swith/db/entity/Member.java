package com.swith.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberId;
    private String kakaoId;
    private String googleId;
    private String email;
    private String nickname;
    private String imgUrl;
    private Role role;
    private String goal;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    enum Role{
        MEMBER
    }

    @Builder
    public Member(long memberId, String kakaoId, String googleId, String email, String nickname, String imgUrl,
                  Role role, String goal, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                  String password) {
        this.memberId = memberId;
        this.kakaoId = kakaoId;
        this.googleId = googleId;
        this.email = email;
        this.nickname = nickname;
        this.imgUrl = imgUrl;
        this.role = role;
        this.goal = goal;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.password = password;
    }

    @OneToMany(
            mappedBy = "member",
            cascade = CascadeType.ALL
    )
    List<MemberStudy> memberStudyList = new ArrayList<>();
}
