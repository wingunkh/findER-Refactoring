package com.finder.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UnlinkRequestDto {
    private String phoneNumber1; // 연결 취소 요청자의 휴대전화 번호

    private String phoneNumber2; // 연결 취소 대상자의 휴대전화 번호
}
