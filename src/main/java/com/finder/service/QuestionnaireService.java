package com.finder.service;

import com.finder.domain.Account;
import com.finder.domain.Link;
import com.finder.domain.Questionnaire;
import com.finder.dto.QuestionnaireDto;
import com.finder.repository.AccountRepository;
import com.finder.repository.LinkRepository;
import com.finder.repository.QuestionnaireRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionnaireService {
    private final QuestionnaireRepository questionnaireRepository;
    private final AccountRepository accountRepository;
    private final LinkRepository linkRepository;

    @Transactional
    public void writeQuestionnaire(QuestionnaireDto questionnaireDto) {
        Account account = accountRepository.findById(questionnaireDto.getPhoneNumber())
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));

        Questionnaire questionnaire = Questionnaire.builder()
                .phoneNumber(questionnaireDto.getPhoneNumber())
                .account(account)
                .name(questionnaireDto.getName())
                .age(questionnaireDto.getAge())
                .gender(questionnaireDto.getGender())
                .bloodType(questionnaireDto.getBloodType())
                .address(questionnaireDto.getAddress())
                .allergy(questionnaireDto.getAllergy())
                .disease(questionnaireDto.getDisease())
                .medicine(questionnaireDto.getMedicine())
                .surgery(questionnaireDto.getSurgery())
                .drink(questionnaireDto.getDrink())
                .smoke(questionnaireDto.getSmoke())
                .etc(questionnaireDto.getEtc())
                .build();

        questionnaireRepository.save(questionnaire);
    }

    @Transactional(readOnly = true)
    public List<QuestionnaireDto> findAccessibleQuestionnaires(String phoneNumber) {
        // 접근 가능 문진표 리스트 = 자신의 문진표 + 연동된 상대방의 문진표
        List<QuestionnaireDto> questionnaireDtoList = new ArrayList<>();

        // 자신의 문진표 추가
        questionnaireRepository.findById(phoneNumber)
                .ifPresent(myQuestionnaire -> questionnaireDtoList.add(QuestionnaireDto.convertToQuestionnaireDto(myQuestionnaire, Boolean.FALSE)));

        // 연동된 상대방의 문진표 추가
        List<Link> linkList = linkRepository.findAllByAccount1PhoneNumber(phoneNumber);

        for (Link link : linkList) {
            questionnaireRepository.findById(link.getAccount2().getPhoneNumber())
                    .ifPresent(linkedQuestionnaire -> questionnaireDtoList.add(QuestionnaireDto.convertToQuestionnaireDto(linkedQuestionnaire, Boolean.TRUE)));
        }

        return questionnaireDtoList;
    }

    @Transactional
    public void updateQuestionnaire(QuestionnaireDto questionnaireDto) {
        Questionnaire questionnaire = questionnaireRepository.findById(questionnaireDto.getPhoneNumber())
                .orElseThrow(() -> new EntityNotFoundException("문진표가 존재하지 않습니다."));

        questionnaire.setName(questionnaireDto.getName());
        questionnaire.setAge(questionnaireDto.getAge());
        questionnaire.setGender(questionnaireDto.getGender());
        questionnaire.setBloodType(questionnaireDto.getBloodType());
        questionnaire.setAddress(questionnaireDto.getAddress());
        questionnaire.setAllergy(questionnaireDto.getAllergy());
        questionnaire.setDisease(questionnaireDto.getDisease());
        questionnaire.setMedicine(questionnaireDto.getMedicine());
        questionnaire.setSurgery(questionnaireDto.getSurgery());
        questionnaire.setDrink(questionnaireDto.getDrink());
        questionnaire.setSmoke(questionnaireDto.getSmoke());
        questionnaire.setEtc(questionnaireDto.getEtc());

        // questionnaireRepository.save(questionnaire);
        // 영속성 컨텍스트의 Dirty Checking 기능을 통해 변경된 엔티티의 상태가 감지된다.
        // 그 후 트랜잭션 커밋 시 해당 변경 사항이 데이터베이스에 반영되어 업데이트가 자동으로 수행된다.
    }

    @Transactional
    public void deleteQuestionnaire(String phoneNumber) {
        Questionnaire questionnaire = questionnaireRepository.findById(phoneNumber)
                .orElseThrow(() -> new EntityNotFoundException("문진표가 존재하지 않습니다."));

        questionnaireRepository.delete(questionnaire);
    }
}
