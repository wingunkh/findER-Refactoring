package com.finder.service;

import com.finder.domain.Link;
import com.finder.domain.Questionnaire;
import com.finder.dto.QuestionnaireDto;
import com.finder.repository.LinkRepository;
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

    private final LinkRepository linkRepository;

    @Transactional
    public String writeQuestionnaire(QuestionnaireDto questionnaireDto, String email) {
        Questionnaire questionnaire = Questionnaire.builder()
                .user(userRepository.findByEmail(email).get())
                .name(questionnaireDto.getName())
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

    @Transactional
    public String linkQuestionnaire(String userEmail, String linkedUserEmail) {
        Link link = Link.builder()
                .user(userRepository.findByEmail(userEmail).get())
                .linkedUserId(userRepository.findByEmail(linkedUserEmail).get().getId())
                .build();

        linkRepository.save(link);

        return "문진표 연동 완료";
    }

    public List<QuestionnaireDto> getAllQuestionnaires(String email) {
        // 전체 문진표 리스트 = 자신의 문진표 리스트 + 연동 문진표 리스트
        List<QuestionnaireDto> questionnaireDtoList = new ArrayList<>();

        // 자신의 문진표 리스트
        List<Questionnaire> myQuestionnaireList = questionnaireRepository.findAllByUser(userRepository.findByEmail(email).get()).get();

        for (Questionnaire questionnaire : myQuestionnaireList) {
            QuestionnaireDto questionnaireDto = QuestionnaireDto.builder()
                    .id(questionnaire.getId())
                    .userId(questionnaire.getUser().getId())
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
                    .isLinked(Boolean.FALSE)
                    .build();

            questionnaireDtoList.add(questionnaireDto);
        }

        // 연동 정보 리스트
        List<Link> linkList = linkRepository.findAllByUser(userRepository.findByEmail(email).get()).get();

        // 연동 문진표 리스트
        List<Questionnaire> linkedQuestionnaireList = new ArrayList<>();

        for (Link link : linkList) {
            linkedQuestionnaireList.add(questionnaireRepository.findMyQuestionnaire(link.getLinkedUserId()).get());
        }

        for (Questionnaire questionnaire : linkedQuestionnaireList) {
            QuestionnaireDto questionnaireDto = QuestionnaireDto.builder()
                    .id(questionnaire.getId())
                    .userId(questionnaire.getUser().getId())
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
                    .isLinked(Boolean.TRUE)
                    .build();

            questionnaireDtoList.add(questionnaireDto);
        }

        return questionnaireDtoList;
    }

    public QuestionnaireDto getQuestionnaire(Long id) {
        Questionnaire questionnaire = questionnaireRepository.findById(id).get();
        QuestionnaireDto questionnaireDto = QuestionnaireDto.builder()
                .id(questionnaire.getId())
                .userId(questionnaire.getUser().getId())
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
                .build();

        return questionnaireDto;
    }

    @Transactional
    public String updateQuestionnaire(Long id, QuestionnaireDto updatedQuestionnaireDto) {
        Questionnaire questionnaire = questionnaireRepository.findById(id).get();

        questionnaire.setName(updatedQuestionnaireDto.getName());

        questionnaire.setAge(updatedQuestionnaireDto.getAge());

        questionnaire.setFamilyRelations(updatedQuestionnaireDto.getFamilyRelations());

        questionnaire.setPhoneNum(updatedQuestionnaireDto.getPhoneNum());

        questionnaire.setAddress(updatedQuestionnaireDto.getAddress());

        questionnaire.setGender(updatedQuestionnaireDto.getGender());

        questionnaire.setBloodType(updatedQuestionnaireDto.getBloodType());

        questionnaire.setAllergy(updatedQuestionnaireDto.getAllergy());

        questionnaire.setMedicine(updatedQuestionnaireDto.getMedicine());

        questionnaire.setSmokingCycle(updatedQuestionnaireDto.getSmokingCycle());

        questionnaire.setDrinkingCycle(updatedQuestionnaireDto.getDrinkingCycle());

        questionnaire.setEtc(updatedQuestionnaireDto.getEtc());

        // questionnaireRepository.save(questionnaire);
        // 영속성 컨텍스트의 Dirty Checking 기능을 통해 변경된 엔티티의 상태가 감지된다.
        // 그 후 트랜잭션 커밋 시 해당 변경 사항이 데이터베이스에 반영되어 업데이트가 자동으로 수행된다.

        return "문진표 수정 완료";
    }

    @Transactional
    public String deleteQuestionnaire(Long id) {
        questionnaireRepository.delete(questionnaireRepository.findById(id).get());

        return "문진표 삭제 완료";
    }
}