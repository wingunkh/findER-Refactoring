package com.finder.dto;

import com.finder.domain.Questionnaire;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionnaireDto {
    private String phoneNumber; // 연락처

    private String name; // 이름

    private String age; // 나이

    private String gender; // 성별

    private String bloodType; // 혈액형

    private String address; // 주소

    private String allergy; // 알러지 정보

    private String disease; // 앓고있는 질환

    private String medicine; // 복용 중인 약

    private String surgery; // 수술 이력

    private String drink; // 음주 정보

    private String smoke; // 흡연 정보

    private String etc; // 기타 특이사항

    private Boolean isLinked; // 연결 여부

    public static QuestionnaireDto convertToQuestionnaireDto(Questionnaire questionnaire, Boolean aBoolean) {
        return QuestionnaireDto.builder()
                .phoneNumber(questionnaire.getAccount().getPhoneNumber())
                .name(questionnaire.getName())
                .age(questionnaire.getAge())
                .gender(questionnaire.getGender())
                .bloodType(questionnaire.getBloodType())
                .address(questionnaire.getAddress())
                .allergy(questionnaire.getAllergy())
                .disease(questionnaire.getDisease())
                .medicine(questionnaire.getMedicine())
                .surgery(questionnaire.getSurgery())
                .drink(questionnaire.getDrink())
                .smoke(questionnaire.getSmoke())
                .etc(questionnaire.getEtc())
                .isLinked(aBoolean)
                .build();
    }
}
