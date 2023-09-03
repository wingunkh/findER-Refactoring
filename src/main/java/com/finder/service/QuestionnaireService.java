package com.finder.service;

import com.finder.domain.Link;
import com.finder.domain.Questionnaire;
import com.finder.domain.Users;
import com.finder.dto.LinkDto;
import com.finder.dto.QuestionnaireDto;
import com.finder.repository.LinkRepository;
import com.finder.repository.QuestionnaireRepository;
import com.finder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionnaireService {
    private final QuestionnaireRepository questionnaireRepository;

    private final UserRepository userRepository;

    private final LinkRepository linkRepository;

    @Transactional
    public String writeQuestionnaire(QuestionnaireDto questionnaireDto, String email) {
        List<Questionnaire> questionnaireList = questionnaireRepository.findAllByUser(userRepository.findByEmail(email).get()).get();

        for (Questionnaire questionnaire : questionnaireList) {
            if ((Objects.equals(questionnaire.getFamilyRelations(), questionnaireDto.getFamilyRelations()))
                    && (!Objects.equals(questionnaire.getFamilyRelations(), "자녀")) && !Objects.equals(questionnaire.getFamilyRelations(), "기타")) {
                return "해당 문진표가 이미 존재합니다.";
            }
        }

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
    public String linkRequest(String userEmail, LinkDto linkDto) {
        Optional<Users> user = userRepository.findByEmail(userEmail);
        Optional<Users> linkedUser = userRepository.findByEmail(linkDto.getLinkedUserEmail());

        if (user.isPresent() && linkedUser.isPresent()) {
            Link link = Link.builder()
                    .user(user.get())
                    .linkedUserId(linkedUser.get().getId())
                    .familyRelations(linkDto.getFamilyRelations())
                    .build();

            linkRepository.save(link);

            return "문진표 연동 요청 완료";
        } else {
            return "사용자를 찾을 수 없습니다.";
        }
    }

    @Transactional
    public String waitLinkResponse(String userEmail, String linkedUserEmail) {
        Optional<Users> user = userRepository.findByEmail(userEmail);
        Optional<Users> linkedUser = userRepository.findByEmail(linkedUserEmail);

        if (user.isPresent() && linkedUser.isPresent()){
            Long myId = user.get().getId();
            Long otherId = linkedUser.get().getId();

            long startTime = System.currentTimeMillis();
            long endTime = startTime + 3 * 60 * 1000;

            while (System.currentTimeMillis() < endTime) {
                if (linkRepository.findByAllId(myId, otherId).isEmpty()) {
                    return "문진표 연동 요청 취소 완료";
                }

                if (linkRepository.findByAllId(otherId, myId).isPresent()) {
                    return "문진표 연동 완료";
                } try {
                    Thread.sleep(3000); // 3초 대기
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();

                    return "문진표 연동 실패";
                }
            }

            linkRepository.deleteByAllId(myId, otherId);
        }

        return "문진표 연동 실패";
    }

    public List<QuestionnaireDto> getAllQuestionnaires(String email) {
        // 전체 문진표 리스트 = 자신의 문진표 리스트 + 연동 문진표 리스트
        List<QuestionnaireDto> questionnaireDtoList = new ArrayList<>();

        // 자신의 문진표 리스트
        List<Questionnaire> myQuestionnaireList = questionnaireRepository.findAllByUser(userRepository.findByEmail(email).get()).get();

        for (Questionnaire questionnaire : myQuestionnaireList) {
            questionnaireDtoList.add(QuestionnaireDto.convertToQuestionnaireDto(questionnaire, Boolean.FALSE));
        }

        // 연동 정보 리스트
        List<Link> myLinkList = linkRepository.findAllByUser(userRepository.findByEmail(email).get()).get();

        // 연동 문진표 리스트
        List<Questionnaire> linkedQuestionnaireList = new ArrayList<>();

        for (Link link1 : myLinkList) {
            List<Link> otherLinkList = linkRepository.findAllByUser(userRepository.findById(link1.getLinkedUserId()).get()).get();

            for (Link link2 : otherLinkList) {
                if (Objects.equals(link1.getUser().getId(), link2.getLinkedUserId())){
                    Optional<Questionnaire> optionalQuestionnaire = questionnaireRepository.findLinkedQuestionnaire(link2.getUser().getId());

                    if (optionalQuestionnaire.isPresent()) {
                        Questionnaire questionnaire = optionalQuestionnaire.get();
                        questionnaire.setFamilyRelations(link1.getFamilyRelations());
                        linkedQuestionnaireList.add(questionnaire);
                    }
                }
            }
        }

        for (Questionnaire questionnaire : linkedQuestionnaireList) {
            questionnaireDtoList.add(QuestionnaireDto.convertToQuestionnaireDto(questionnaire, Boolean.TRUE));
        }

        return questionnaireDtoList;
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
        Questionnaire questionnaire = questionnaireRepository.findById(id).get();

        questionnaireRepository.delete(questionnaire);

        if (Objects.equals(questionnaire.getFamilyRelations(), "본인"))
            linkRepository.deleteByUserIdOrLinkedUserId(id);

        return "문진표 삭제 완료";
    }

    @Transactional
    public String unlinkQuestionnaire(String userEmail, String linkedUserEmail) {
        Optional<Users> user = userRepository.findByEmail(userEmail);
        Optional<Users> linkedUser = userRepository.findByEmail(linkedUserEmail);

        if (user.isPresent() && linkedUser.isPresent()) {
            Long myId = userRepository.findByEmail(userEmail).get().getId();
            Long otherId = userRepository.findByEmail(linkedUserEmail).get().getId();

            linkRepository.deleteByAllId(myId, otherId);
            linkRepository.deleteByAllId(otherId, myId);

            return "문진표 연동 취소 완료";
        } else {
            return "문진표 연동 취소 실패";
        }
    }
}