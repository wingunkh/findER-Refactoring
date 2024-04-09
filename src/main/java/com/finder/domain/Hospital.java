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
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "HOSPITAL_SEQUENCE_GENERATOR")
    @SequenceGenerator(name = "HOSPITAL_SEQUENCE_GENERATOR", sequenceName = "HOSPITAL_SQ", initialValue = 1, allocationSize = 1)
    private Long id; // 기본키

    private String hpid; // 기관 번호

    private String name; // 응급실 이름

    private String address; // 주소

    private String mapAddress; // 약도 상 주소

    private String tel; // 병원 연락처

    private String ERTel; // 응급실 연락처

    private String ambulance; // 구급차 가용 여부

    private String CT; // CT 가용 여부

    private String MRI; // MRI 가용 여부

    private Double latitude; // 위도

    private Double longitude; // 경도
}
