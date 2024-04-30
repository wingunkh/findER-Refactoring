package com.finder.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MarkerResponseDto {
  private String hpID; // 기관 코드

  private Double lat; // 위도

  private Double lon; // 경도
}
