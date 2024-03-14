package com.finder.controller;

import com.finder.dto.AccountRequestDto;
import com.finder.dto.UnlinkRequestDto;
import com.finder.dto.LinkRequestDto;
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
        return accountService.signup(accountRequestDto);
    }

    // 로그인
    @GetMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AccountRequestDto accountRequestDto) {
        return accountService.login(accountRequestDto);
    }

    // 시리얼 번호 조회
    @GetMapping("/{phoneNumber}")
    public ResponseEntity<Object> findSerialNumber(@PathVariable String phoneNumber) {
        return accountService.findSerialNumber(phoneNumber);
    }

    // 연동
    @PostMapping("/link")
    public ResponseEntity<Object> link(@RequestBody LinkRequestDto linkRequestDto) {
        return accountService.link(linkRequestDto);
    }

    // 연동 취소
    @PostMapping("/unlink")
    public ResponseEntity<Object> unlink(@RequestBody UnlinkRequestDto unlinkRequestDto) {
        return accountService.unlink(unlinkRequestDto);
    }
}
