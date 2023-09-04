package com.finder.domain;

import lombok.*;
import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Questionnaire {
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "QUESTIONNAIRE_SEQUENCE_GENERATOR")
    @SequenceGenerator(name = "QUESTIONNAIRE_SEQUENCE_GENERATOR", sequenceName = "QUESTIONNAIRE_SQ", initialValue = 1, allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private Users user;

    private String name;

    private String birthday;

    private String familyRelations;

    private String phoneNum;

    private String address;

    private String gender;

    private String bloodType;

    private String allergy;

    private String medicine;

    private String smokingCycle;

    private String drinkingCycle;

    private String etc;
}