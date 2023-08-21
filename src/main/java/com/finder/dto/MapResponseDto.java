package com.finder.dto;

import lombok.Data;

@Data
public class MapResponseDto {
  private Long hospitalId;
  private Double lat;
  private Double lon;

    public MapResponseDto(Long hospitalId, Double lat, Double lon) {
        this.hospitalId = hospitalId;
        this.lat = lat;
        this.lon = lon;
    }
}
