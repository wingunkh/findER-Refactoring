package com.finder.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionnaireDto {
    private Long id;

    private Long userId;

    private String name;

    private Integer age;

    private String familyRelations;

    private String phoneNum;

    private String address;

    private String gender;

    private String bloodType;

    private String allergy;

    private String medicine;

    private String smokingCycle;

    private String drinkingCycle;

    private String etc;
}