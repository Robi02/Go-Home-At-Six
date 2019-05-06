package com.ghasix.datas.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Users {

    @Id @GeneratedValue
    long id;

    @Column(length = 128, nullable = false, unique = true)
    String email;

    @Column(length = 64, nullable = false)
    String name;

    @Column(nullable = false)
    int access_level = 1;

    @Column
    long join_time;

    @Column
    long last_login_time;

    @Column
    long accessible_time;
}
