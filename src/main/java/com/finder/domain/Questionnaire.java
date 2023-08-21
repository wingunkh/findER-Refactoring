package com.finder.domain;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "QUESTIONNAIRE")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Questionnaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    private String name;

    private Integer age;

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