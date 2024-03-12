package com.finder.service;

import com.finder.domain.Account;
import com.finder.dto.AccountRequestDto;
import com.finder.repository.AccountRepository;
import com.finder.util.SHAUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    public final AccountRepository accountRepository;

    public ResponseEntity<Object> signup(AccountRequestDto accountRequestDto) {
        Optional<Account> optionalMember = accountRepository.findById(accountRequestDto.phoneNumber);

        if (optionalMember.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 가입된 회원이 존재합니다.");
        } else {
            String salt = SHAUtil.getSalt();
            String encryptedRrn = SHAUtil.encryptWithSalt(accountRequestDto.rrn, salt);
            String serialNumber = generateSerialNumber();

            accountRepository.save(new Account(accountRequestDto.phoneNumber, encryptedRrn, salt, serialNumber));

            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입에 성공하였습니다.");
        }
    }

    public ResponseEntity<Object> login(AccountRequestDto accountRequestDto) {
        Optional<Account> optionalMember = accountRepository.findById(accountRequestDto.phoneNumber);

        if (optionalMember.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인에 실패하였습니다.");
        }

        Account account = optionalMember.get();
        String salt = account.getSalt();
        String encryptedRrn = SHAUtil.encryptWithSalt(accountRequestDto.rrn, salt);

        if (encryptedRrn.equals(account.getRrn())) {
            return ResponseEntity.status(HttpStatus.OK).body("로그인에 성공하였습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인에 실패하였습니다.");
        }
    }

    public String generateSerialNumber() {
        StringBuilder sb = new StringBuilder();
        SecureRandom random = new SecureRandom();
        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for (int i = 0; i < 16; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }

        return sb.toString();
    }
}
