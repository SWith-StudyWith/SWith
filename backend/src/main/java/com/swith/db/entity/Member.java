package com.swith.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    private String kakaoId;
    private String googleId;
    private String email;
    private String nickname;
    private String imgUrl;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String goal;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    private String isDeleted;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public enum Role {
        MEMBER
    }

    @Builder
    public Member(String kakaoId, String googleId, String email, String nickname, String imgUrl,
                  Role role, String goal, LocalDateTime createdAt, LocalDateTime updatedAt, String isDeleted,
                  String password) {
        this.kakaoId = kakaoId;
        this.googleId = googleId;
        this.email = email;
        this.nickname = nickname;
        this.imgUrl = imgUrl;
        this.role = role;
        this.goal = goal;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
        this.password = password;
    }

    @OneToMany(
            mappedBy = "member",
            cascade = CascadeType.ALL
    )
    List<MemberStudy> memberStudyList = new ArrayList<>();
}
