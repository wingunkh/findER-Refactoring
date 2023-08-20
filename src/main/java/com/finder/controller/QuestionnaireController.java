package com.finder.controller;

import com.finder.dto.QuestionnaireDto;
import com.finder.service.QuestionnaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questionnaire")
public class QuestionnaireController {
    private final QuestionnaireService questionnaireService;

    // 문진표 작성
    @PostMapping()
    public ResponseEntity writeQuestionnaire(@RequestBody QuestionnaireDto questionnaireDto, @AuthenticationPrincipal UserDetails userDetail) {
        return ResponseEntity.ok(questionnaireService.writeQuestionnaire(questionnaireDto, userDetail.getUsername()));
    }

    // 전체 문진표 리스트 조회
    @GetMapping()
    public ResponseEntity<List<QuestionnaireDto>> getAllQuestionnaires(@AuthenticationPrincipal UserDetails userDetail) {
        return ResponseEntity.ok(questionnaireService.getAllQuestionnaires(userDetail.getUsername()));
    }
}