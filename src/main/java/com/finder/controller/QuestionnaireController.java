package com.finder.controller;

import com.finder.dto.QuestionnaireDto;
import com.finder.service.QuestionnaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questionnaire")
public class QuestionnaireController {
    private final QuestionnaireService questionnaireService;

    @PostMapping
    public ResponseEntity writeQuestionnaire(@RequestBody QuestionnaireDto questionnaireDto, @AuthenticationPrincipal UserDetails userDetail) {
        return ResponseEntity.ok(questionnaireService.writeQuestionnaire(questionnaireDto, userDetail.getUsername()));
    }
}