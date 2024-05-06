package com.finder.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ER {
    @Id
    private String hpID; // 기관 코드

    private String name; // 응급실 이름

    private String address; // 주소

    private String mapAddress; // 약도 주소

    private String tel; // 병원 연락처

    private String ERTel; // 응급실 연락처

    private String ambulance; // 구급차 가용 여부

    private String CT; // CT 가용 여부

    private String MRI; // MRI 가용 여부

    private Double latitude; // 위도

    private Double longitude; // 경도

    private String subject; // 진료 과목
}
