package com.finder.service;

import com.finder.domain.Account;
import com.finder.domain.Link;
import com.finder.domain.Questionnaire;
import com.finder.dto.QuestionnaireDto;
import com.finder.repository.AccountRepository;
import com.finder.repository.LinkRepository;
import com.finder.repository.QuestionnaireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionnaireService {
    private final QuestionnaireRepository questionnaireRepository;

    private final AccountRepository accountRepository;

    private final LinkRepository linkRepository;

    @Transactional
    public ResponseEntity<Object> writeQuestionnaire(QuestionnaireDto questionnaireDto) {
        Optional<Account> optionalAccount = accountRepository.findById(questionnaireDto.getPhoneNumber());

        if (optionalAccount.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
        }

        Questionnaire questionnaire = Questionnaire.builder()
                .phoneNumber(questionnaireDto.getPhoneNumber())
                .account(optionalAccount.get())
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

        return ResponseEntity.status(HttpStatus.CREATED).body("문진표 작성에 성공하였습니다.");
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> findAccessibleQuestionnaires(String phoneNumber) {
        // 접근 가능 문진표 리스트 = 자신의 문진표 + 연동된 상대방의 문진표
        List<QuestionnaireDto> questionnaireDtoList = new ArrayList<>();

        // 자신의 문진표
        Optional<Questionnaire> optionalMyQuestionnaire = questionnaireRepository.findById(phoneNumber);
        optionalMyQuestionnaire.ifPresent(questionnaire -> questionnaireDtoList.add(QuestionnaireDto.convertToQuestionnaireDto(questionnaire)));

        // 연동 정보 리스트
        List<Link> linkList = linkRepository.findAllByAccount1PhoneNumber(phoneNumber);

        for (Link link : linkList) {
            // 연동된 상대방의 문진표
            Optional<Questionnaire> optionalLinkedQuestionnaire = questionnaireRepository.findById(link.getAccount2().getPhoneNumber());
            optionalLinkedQuestionnaire.ifPresent(questionnaire -> questionnaireDtoList.add(QuestionnaireDto.convertToQuestionnaireDto(questionnaire)));
        }

        return ResponseEntity.status(HttpStatus.OK).body(questionnaireDtoList);
    }

    @Transactional
    public ResponseEntity<Object> updateQuestionnaire(QuestionnaireDto questionnaireDto) {
        Optional<Questionnaire> optionalQuestionnaire = questionnaireRepository.findById(questionnaireDto.getPhoneNumber());

        if (optionalQuestionnaire.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
        }

        Questionnaire questionnaire = optionalQuestionnaire.get();
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

        return ResponseEntity.status(HttpStatus.OK).body("문진표 수정에 성공하였습니다.");
    }

    @Transactional
    public ResponseEntity<Object> deleteQuestionnaire(String phoneNumber) {
        Optional<Questionnaire> optionalQuestionnaire = questionnaireRepository.findById(phoneNumber);

        if (optionalQuestionnaire.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
        }

        questionnaireRepository.delete(optionalQuestionnaire.get());

        return ResponseEntity.status(HttpStatus.OK).body("문진표 삭제에 성공하였습니다.");
    }
}