package com.finder.dto;

import com.finder.domain.User;
import lombok.Data;

@Data
public class QuestionnaireDto {
    private Long id;

    private User user;

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