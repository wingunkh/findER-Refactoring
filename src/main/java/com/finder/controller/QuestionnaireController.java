package com.finder.controller;

import com.finder.dto.QuestionnaireDto;
import com.finder.service.QuestionnaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        try {
            questionnaireService.writeQuestionnaire(questionnaireDto);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // 접근 가능한 문진표 조회
    @GetMapping("/{phoneNumber}")
    public ResponseEntity<Object> findAccessibleQuestionnaires(@PathVariable String phoneNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(questionnaireService.findAccessibleQuestionnaires(phoneNumber));
    }

    // 문진표 수정
    @PatchMapping
    public ResponseEntity<Object> updateQuestionnaire(@RequestBody QuestionnaireDto questionnaireDto) {
        try {
            questionnaireService.updateQuestionnaire(questionnaireDto);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // 문진표 삭제
    @DeleteMapping("/{phoneNumber}")
    public ResponseEntity<Object> deleteQuestionnaire(@PathVariable String phoneNumber) {
        try {
            questionnaireService.deleteQuestionnaire(phoneNumber);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
