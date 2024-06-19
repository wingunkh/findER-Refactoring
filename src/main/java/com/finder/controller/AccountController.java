package com.finder.controller;

import com.finder.dto.AccountRequestDto;
import com.finder.dto.UnlinkRequestDto;
import com.finder.dto.LinkRequestDto;
import com.finder.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService accountService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody AccountRequestDto accountRequestDto) {
        accountService.signup(accountRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AccountRequestDto accountRequestDto) {
        accountService.login(accountRequestDto);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 시리얼 번호 조회
    @GetMapping("/serialNumber/{phoneNumber}")
    public ResponseEntity<Object> findSerialNumber(@PathVariable String phoneNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.findSerialNumber(phoneNumber));
    }

    // 연동
    @PostMapping("/link")
    public ResponseEntity<Object> link(@RequestBody LinkRequestDto linkRequestDto) {
        accountService.link(linkRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 연동 취소
    @PostMapping("/unlink")
    public ResponseEntity<Object> unlink(@RequestBody UnlinkRequestDto unlinkRequestDto) {
        accountService.unlink(unlinkRequestDto);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
