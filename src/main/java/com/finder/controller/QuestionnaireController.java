package com.finder.controller;

import com.finder.dto.LinkDto;
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

    // 문진표 연동 요청
    @PostMapping("/link")
    public ResponseEntity linkRequest(@RequestBody LinkDto linkDto, @AuthenticationPrincipal UserDetails userDetail) {
        return ResponseEntity.ok(questionnaireService.linkRequest(userDetail.getUsername(), linkDto));
    }

    // 문진표 연동 응답 대기
    @GetMapping("/link")
    public ResponseEntity waitLinkResponse(@RequestParam String userEmail, @RequestParam String linkedUserEmail) {
        return ResponseEntity.ok(questionnaireService.waitLinkResponse(userEmail, linkedUserEmail));
    }

    // 전체 문진표 리스트 조회
    @GetMapping()
    public ResponseEntity<List<QuestionnaireDto>> getAllQuestionnaires(@AuthenticationPrincipal UserDetails userDetail) {
        return ResponseEntity.ok(questionnaireService.getAllQuestionnaires(userDetail.getUsername()));
    }

    // 문진표 수정
    @PatchMapping("/{id}")
    public ResponseEntity updateQuestionnaire(@PathVariable Long id, @RequestBody QuestionnaireDto updatedQuestionnaireDto) {
        return ResponseEntity.ok(questionnaireService.updateQuestionnaire(id, updatedQuestionnaireDto));
    }

    // 문진표 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity deleteQuestionnaire(@PathVariable Long id) {
        return ResponseEntity.ok(questionnaireService.deleteQuestionnaire(id));
    }

    // 문진표 연동 취소
    @DeleteMapping("/unlink")
    public ResponseEntity unlinkQuestionnaire(@RequestParam Long linkedUserId, @AuthenticationPrincipal UserDetails userDetail) {
        return ResponseEntity.ok(questionnaireService.unlinkQuestionnaire(userDetail.getUsername(), linkedUserId));
    }
}