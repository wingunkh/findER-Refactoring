package com.finder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HospitalPreviewDto {
    private String name;
    private String address;
    private String representativeContact;
    private String emergencyContact;
    private Integer hvec;
    private Double distance;
    private String arrivalTime;
}
