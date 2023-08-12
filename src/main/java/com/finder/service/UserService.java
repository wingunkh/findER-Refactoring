package com.finder.service;

import com.finder.domain.User;
import com.finder.dto.SignUpDto;
import com.finder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public String createUser(SignUpDto signUpDto) {
        userRepository.save(new User(signUpDto.getEmail(), signUpDto.getPassword(), signUpDto.getName()));
        return "사용자 생성 완료";
    }
}
