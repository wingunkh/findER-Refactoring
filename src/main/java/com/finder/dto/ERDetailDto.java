package com.finder.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ERDetailDto {
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

    private Integer bedCount; // 병상 수

    private Double distance; // 거리

    private String arrivalTime; // 도착 예정 시간
}
