package com.finder.controller;

import com.finder.dto.QuestionnaireDto;
import com.finder.service.QuestionnaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questionnaire")
public class QuestionnaireController {
    private final QuestionnaireService questionnaireService;

    // 문진표 작성
    @PostMapping
    public ResponseEntity<Object> writeQuestionnaire(@RequestBody QuestionnaireDto questionnaireDto) {
        return questionnaireService.writeQuestionnaire(questionnaireDto);
    }

    // 접근 가능한 문진표 조회
    @GetMapping("/{phoneNumber}")
    public ResponseEntity<Object> findAccessibleQuestionnaires(@PathVariable String phoneNumber) {
        return questionnaireService.findAccessibleQuestionnaires(phoneNumber);
    }

    // 문진표 수정
    @PatchMapping
    public ResponseEntity<Object> updateQuestionnaire(@RequestBody QuestionnaireDto questionnaireDto) {
        return questionnaireService.updateQuestionnaire(questionnaireDto);
    }

    // 문진표 삭제
    @DeleteMapping("/{phoneNumber}")
    public ResponseEntity<Object> deleteQuestionnaire(@PathVariable String phoneNumber) {
        return questionnaireService.deleteQuestionnaire(phoneNumber);
    }
}
