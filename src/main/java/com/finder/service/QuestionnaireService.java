package com.finder.service;

import com.finder.domain.Questionnaire;
import com.finder.dto.QuestionnaireDto;
import com.finder.repository.QuestionnaireRepository;
import com.finder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionnaireService {
    private final QuestionnaireRepository questionnaireRepository;

    private final UserRepository userRepository;

    public String writeQuestionnaire(QuestionnaireDto questionnaireDto, String email) {
        Questionnaire questionnaire = Questionnaire.builder()
                .id(questionnaireDto.getId())
                .user(userRepository.findByEmail(email).get())
                .age(questionnaireDto.getAge())
                .familyRelations(questionnaireDto.getFamilyRelations())
                .phoneNum(questionnaireDto.getPhoneNum())
                .address(questionnaireDto.getAddress())
                .gender(questionnaireDto.getGender())
                .bloodType(questionnaireDto.getBloodType())
                .allergy(questionnaireDto.getAllergy())
                .medicine(questionnaireDto.getMedicine())
                .smokingCycle(questionnaireDto.getSmokingCycle())
                .drinkingCycle(questionnaireDto.getDrinkingCycle())
                .etc(questionnaireDto.getEtc())
                .build();

        questionnaireRepository.save(questionnaire);

        return "문진표 작성 완료";
    }
}