package com.ghasix.datas.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Commutes {

    @Id @GeneratedValue // 고유 ID
    private long id; 

    // 소유 유저 ID
    private long own_user_id; 

    @Column(length = 64, nullable = false) // 출퇴근 회사 이름
    private String commute_companay_name; 

    @Column(nullable = false) // 출근시간
    private long check_in_time;

    @Column // 퇴근시간
    private long check_out_time;

    @Column(length = 256) // 메모
    private String memo;

    @Builder
    public Commutes(String commute_companay_name, long check_in_time, long check_out_time, String memo) {
        this.commute_companay_name = commute_companay_name;
        this.check_in_time = check_in_time;
        this.check_out_time = check_out_time;
        this.memo = memo;
    }
}