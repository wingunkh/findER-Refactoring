package com.finder.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account {
    @Id
    private String phoneNumber; // 휴대전화 번호

    private String rrn; // 암호화된 주민등록번호

    private String salt; // 솔트값

    private String serialNumber; // 문진표 공유를 위한 일련번호
}
