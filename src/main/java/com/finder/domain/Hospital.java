package com.finder.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Hospital extends BaseEntity {
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "HOSPITAL_SEQUENCE_GENERATOR")
    @SequenceGenerator(name = "HOSPITAL_SEQUENCE_GENERATOR", sequenceName = "HOSPITAL_SQ", initialValue = 1, allocationSize = 1)
    private Long id;

    private String name;

    private String address;

    private String simpleAddress;

    private String representativeContact;

    private String emergencyContact;

    private String ambulance;

    private String ct;

    private String mri;

    private Double latitude;

    private Double longitude;
}