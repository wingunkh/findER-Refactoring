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
    private String phoneNumber; // 연락처

    @OneToOne
    @MapsId
    private Account account;

    private String name; // 이름

    private String age; // 나이

    private String gender; // 성별

    private String relation; // 가족 관계

    private String bloodType; // 혈액형

    private String address; // 주소

    private String allergy; // 알러지 정보

    private String disease; // 앓고있는 질환

    private String medicine; // 복용 중인 약

    private String surgery; // 수술 이력

    private String drink; // 음주 정보

    private String smoke; // 흡연 정보

    private String etc; // 기타 특이사항

    private Boolean isLinked; // 연동 여부
}
