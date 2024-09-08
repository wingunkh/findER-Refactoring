package com.finder.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LinkRequestDto {
    private String phoneNumber; // 휴대전화 번호

    private String linkedSerialNumber; // 연결된 계정의 일련번호
}
