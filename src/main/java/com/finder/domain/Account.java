package com.finder.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Id;

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

    private String serialNumber; // 연동을 위한 일련번호
}
