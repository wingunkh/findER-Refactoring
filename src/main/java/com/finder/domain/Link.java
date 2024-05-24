package com.finder.domain;

import lombok.*;
import jakarta.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "LINK_SEQUENCE_GENERATOR")
    @SequenceGenerator(name = "LINK_SEQUENCE_GENERATOR", sequenceName = "LINK_SQ", initialValue = 1, allocationSize = 1)
    private Long id; // 기본키

    @ManyToOne
    private Account account1; // 연동 계정1

    @ManyToOne
    private Account account2; // 연동 계정2
}
