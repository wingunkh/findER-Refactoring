package com.finder.service;

import com.finder.domain.Questionnaire;
import com.finder.dto.QuestionnaireDto;
import com.finder.repository.QuestionnaireRepository;
import com.finder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionnaireService {
    private final QuestionnaireRepository questionnaireRepository;

    private final UserRepository userRepository;

    @Transactional
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

    public List<QuestionnaireDto> getAllQuestionnaires(String email) {
        List<QuestionnaireDto> questionnaireDtoList = new ArrayList<>();
        List<Questionnaire> questionnaireList = questionnaireRepository.findAllByUser(userRepository.findByEmail(email).get()).get();

        for (Questionnaire questionnaire : questionnaireList) {
            QuestionnaireDto questionnaireDto = QuestionnaireDto.builder()
                    .id(questionnaire.getId())
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
                    .build();

            questionnaireDtoList.add(questionnaireDto);
        }

        return questionnaireDtoList;
    }
}