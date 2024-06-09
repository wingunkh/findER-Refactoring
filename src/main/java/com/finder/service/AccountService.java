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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.security.SecureRandom;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountService {
    public final AccountRepository accountRepository;
    public final LinkRepository linkRepository;

    @Transactional
    public void signup(AccountRequestDto accountRequestDto) {
        if (accountRepository.findById(accountRequestDto.phoneNumber).isPresent()) {
            throw new DataIntegrityViolationException("이미 회원입니다.");
        }

        String salt = SHAUtil.getSalt();
        String encryptedRrn = SHAUtil.encryptWithSalt(accountRequestDto.rrn, salt);
        String serialNumber = generateSerialNumber();

        accountRepository.save(new Account(accountRequestDto.phoneNumber, encryptedRrn, salt, serialNumber));
    }

    @Transactional(readOnly = true)
    public void login(AccountRequestDto accountRequestDto) {
        Account account = accountRepository.findById(accountRequestDto.phoneNumber)
                .orElseThrow(() -> new IllegalArgumentException("로그인에 실패하였습니다."));

        String salt = account.getSalt();
        String encryptedRrn = SHAUtil.encryptWithSalt(accountRequestDto.rrn, salt);

        if (!encryptedRrn.equals(account.getRrn())) {
            throw new IllegalArgumentException("로그인에 실패하였습니다.");
        }
    }

    @Transactional(readOnly = true)
    public String findSerialNumber(String phoneNumber) {
        Account account = accountRepository.findById(phoneNumber)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        return account.getSerialNumber();
    }

    @Transactional
    public void link(LinkRequestDto linkRequestDto) {
        Account account1 = accountRepository.findById(linkRequestDto.getPhoneNumber())
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        Account account2 = accountRepository.findBySerialNumber(linkRequestDto.getLinkedSerialNumber());

        if (Objects.isNull(account2)) {
            throw new IllegalArgumentException("유효하지 않은 일련번호입니다.");
        }

        Link existingLink = linkRepository.findByAccount1PhoneNumberAndAccount2PhoneNumber(account1.getPhoneNumber(), account2.getPhoneNumber());

        if (existingLink != null) {
            throw new DataIntegrityViolationException("이미 연동 상태입니다.");
        }

        Link link1 = Link.builder()
                .account1(account1)
                .account2(account2)
                .build();

        Link link2 = Link.builder()
                .account1(account2)
                .account2(account1)
                .build();

        linkRepository.save(link1);
        linkRepository.save(link2);
    }

    @Transactional
    public void unlink(UnlinkRequestDto unlinkRequestDto) {
        Account account1 = accountRepository.findById(unlinkRequestDto.getPhoneNumber1())
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        Account account2 = accountRepository.findById(unlinkRequestDto.getPhoneNumber2())
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        linkRepository.deleteByAccount1PhoneNumberAndAccount2PhoneNumber(account1.getPhoneNumber(), account2.getPhoneNumber());
        linkRepository.deleteByAccount1PhoneNumberAndAccount2PhoneNumber(account2.getPhoneNumber(), account1.getPhoneNumber());
    }

    private String generateSerialNumber() {
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
