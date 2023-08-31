package com.finder.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Users extends BaseEntity{
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "USERS_SEQUENCE_GENERATOR")
    @SequenceGenerator(name = "USERS_SEQUENCE_GENERATOR", sequenceName = "USERS_SQ", initialValue = 1, allocationSize = 1)
    private Long id;

    private String email;

    private String password;

    private String name;

    private String refreshToken;

    @Enumerated(EnumType.STRING)
    private Role role;

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }
}
