package com.finder.dto;

import lombok.Data;

@Data
public class MapResponseDto {
  private Long id;
  private Double lat;
  private Double lon;

    public MapResponseDto(Long id, Double lat, Double lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }
}
