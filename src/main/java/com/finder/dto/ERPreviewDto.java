package com.finder.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ERPreviewDto {
    private String hpID; // 기관 코드

    private String name; // 응급실 이름

    private String address; // 주소

    private String tel; // 병원 연락처

    private String ERTel; // 응급실 연락처

    private Integer bedCount; // 병상 수

    private String bedTime; // 병상 수 갱신 시간 (yy/MM/dd HH:mm)

    private Double distance; // 거리

    private String ETA; // 도착 예정 시간
}
