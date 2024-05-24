package com.finder.domain;

import lombok.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Bed {
    @Id
    private String hpID; // 기관 코드

    private String time; // 시간 (yy/MM/dd HH:mm)

    private Integer count; // 병상 수
}
