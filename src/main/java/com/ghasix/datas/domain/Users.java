package com.ghasix.datas.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Users {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", length = 128, nullable = false, unique = true)
    private String email;

    @Column(name = "name", length = 64, nullable = false)
    private String name;

    @Column(name = "access_level", nullable = false)
    private Integer accessLevel = 1;

    @Column(name = "status", length = 8, nullable = false)
    private String status;

    @Column(name = "join_time", nullable = false)
    private Long joinTime;

    @Column(name = "last_login_time")
    private Long lastLoginTime;

    @Column(name = "accessible_time")
    private Long accessibleTime;

    @Builder
    public Users(String email, String name, Integer accessLevel, String status,
                    Long joinTime, Long lastLoginTime, Long accessibleTime) {
        this.email = email;
        this.name = name;
        this.accessLevel = accessLevel;
        this.status = status;
        this.joinTime = joinTime;
        this.lastLoginTime = lastLoginTime;
        this.accessibleTime = accessibleTime;
    }
}
