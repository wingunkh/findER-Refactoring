package com.finder.dto;

import lombok.Data;

@Data
public class MapRequestDto {
    private Double southWestLat;
    private Double southWestLon;
    private Double northEastLat;
    private Double northEastLon;
}
