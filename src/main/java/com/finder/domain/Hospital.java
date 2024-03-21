package com.finder.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "HOSPITAL_SEQUENCE_GENERATOR")
    @SequenceGenerator(name = "HOSPITAL_SEQUENCE_GENERATOR", sequenceName = "HOSPITAL_SQ", initialValue = 1, allocationSize = 1)
    private Long id; // 기본키

    private String name; // 병원명

    private String address; // 병원 주소

    private String mapAddress; // 약도 상 주소

    private String tel; // 병원 전화번호

    private String ERTel; // 응급실 전화번호

    private String ambulance; // 구급차 가용 여부

    private String CT; // CT 가용 여부

    private String MRI; // MRI 가용 여부

    private Double latitude; // 병원 위도

    private Double longitude; // 병원 경도
}
