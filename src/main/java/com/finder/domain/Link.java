package com.finder.domain;

import lombok.*;
import javax.persistence.*;

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
    private Long id;

    @ManyToOne
    private Account account1;

    @ManyToOne
    private Account account2;
}
