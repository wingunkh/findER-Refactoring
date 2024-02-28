package com.finder.controller;

import com.finder.dto.AccountRequestDto;
import com.finder.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountController {
    private final AccountService accountService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody AccountRequestDto accountRequestDto) {
        return ResponseEntity.ok(accountService.signup(accountRequestDto));
    }

    // 로그인
    @GetMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AccountRequestDto accountRequestDto) {
        return ResponseEntity.ok(accountService.login(accountRequestDto));
    }
}
