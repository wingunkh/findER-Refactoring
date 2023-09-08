package com.finder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HospitalDetailDto {
    private String name;

    private String address;

    private String simpleAddress;

    private String representativeContact;

    private String emergencyContact;

    private Boolean ambulance;

    private Boolean ct;

    private Boolean mri;

    private Integer hvec;

    private Double distance;

    private String arrivalTime;

    private Double lat;

    private Double lon;

    private BedDataDto bedDataDto;
}