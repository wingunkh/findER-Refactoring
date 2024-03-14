package com.finder.service;

import com.finder.domain.Account;
import com.finder.domain.Link;
import com.finder.dto.AccountRequestDto;
import com.finder.dto.LinkRequestDto;
import com.finder.dto.UnlinkRequestDto;
import com.finder.repository.AccountRepository;
import com.finder.repository.LinkRepository;
import com.finder.util.SHAUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    public final AccountRepository accountRepository;

    public final LinkRepository linkRepository;

    @Transactional
    public ResponseEntity<Object> signup(AccountRequestDto accountRequestDto) {
        Optional<Account> optionalAccount = accountRepository.findById(accountRequestDto.phoneNumber);

        if (optionalAccount.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 가입된 회원이 존재합니다.");
        } else {
            String salt = SHAUtil.getSalt();
            String encryptedRrn = SHAUtil.encryptWithSalt(accountRequestDto.rrn, salt);
            String serialNumber = generateSerialNumber();

            accountRepository.save(new Account(accountRequestDto.phoneNumber, encryptedRrn, salt, serialNumber));

            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입에 성공하였습니다.");
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> login(AccountRequestDto accountRequestDto) {
        Optional<Account> optionalAccount = accountRepository.findById(accountRequestDto.phoneNumber);

        if (optionalAccount.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인에 실패하였습니다.");
        }

        String salt = optionalAccount.get().getSalt();
        String encryptedRrn = SHAUtil.encryptWithSalt(accountRequestDto.rrn, salt);

        if (encryptedRrn.equals(optionalAccount.get().getRrn())) {
            return ResponseEntity.status(HttpStatus.OK).body("로그인에 성공하였습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인에 실패하였습니다.");
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> findSerialNumber(String phoneNumber) {
        Optional<Account> optionalAccount = accountRepository.findById(phoneNumber);

        return optionalAccount.<ResponseEntity<Object>>map(account -> ResponseEntity.status(HttpStatus.OK).body(account.getSerialNumber()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다."));
    }

    @Transactional
    public ResponseEntity<Object> link(LinkRequestDto linkRequestDto) {
        Optional<Account> optionalAccount1 = accountRepository.findById(linkRequestDto.getPhoneNumber());
        Account account2 = accountRepository.findBySerialNumber(linkRequestDto.getLinkedSerialNumber());

        if (optionalAccount1.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
        }

        if (Objects.isNull(account2)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("존재하지 않는 일련번호입니다.");
        }

        Link existingLink = linkRepository.findByAccount1PhoneNumberAndAccount2PhoneNumber(optionalAccount1.get().getPhoneNumber(), account2.getPhoneNumber());

        if (existingLink != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 연동된 상태입니다.");
        }

        Link link1 = Link.builder()
                .account1(optionalAccount1.get())
                .account2(account2)
                .build();

        Link link2 = Link.builder()
                .account1(account2)
                .account2(optionalAccount1.get())
                .build();

        linkRepository.save(link1);
        linkRepository.save(link2);

        return ResponseEntity.status(HttpStatus.OK).body("연동에 성공하였습니다.");
    }

    @Transactional
    public ResponseEntity<Object> unlink(UnlinkRequestDto unlinkRequestDto) {
        Optional<Account> optionalAccount1 = accountRepository.findById(unlinkRequestDto.getPhoneNumber1());
        Optional<Account> optionalAccount2 = accountRepository.findById(unlinkRequestDto.getPhoneNumber2());

        if (optionalAccount1.isEmpty() || optionalAccount2.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
        }

        linkRepository.deleteByAccount1PhoneNumberAndAccount2PhoneNumber(optionalAccount1.get().getPhoneNumber(), optionalAccount2.get().getPhoneNumber());
        linkRepository.deleteByAccount1PhoneNumberAndAccount2PhoneNumber(optionalAccount2.get().getPhoneNumber(), optionalAccount1.get().getPhoneNumber());

        return ResponseEntity.status(HttpStatus.OK).body("연동 취소에 성공하였습니다.");
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
