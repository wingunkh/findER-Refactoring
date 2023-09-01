package com.finder.dto;

import com.finder.domain.Questionnaire;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionnaireDto {
    private Long id;

    private String email;

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

    private Boolean isLinked;

    public static QuestionnaireDto convertToQuestionnaireDto(Questionnaire questionnaire, Boolean isLinked) {
        return QuestionnaireDto.builder()
                .id(questionnaire.getId())
                .email(questionnaire.getUser().getEmail())
                .name(questionnaire.getName())
                .age(questionnaire.getAge())
                .familyRelations(questionnaire.getFamilyRelations())
                .phoneNum(questionnaire.getPhoneNum())
                .address(questionnaire.getAddress())
                .gender(questionnaire.getGender())
                .bloodType(questionnaire.getBloodType())
                .allergy(questionnaire.getAllergy())
                .medicine(questionnaire.getMedicine())
                .smokingCycle(questionnaire.getSmokingCycle())
                .drinkingCycle(questionnaire.getDrinkingCycle())
                .etc(questionnaire.getEtc())
                .isLinked(isLinked)
                .build();
    }
}