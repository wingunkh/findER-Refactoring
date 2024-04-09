package com.finder.domain;

import com.finder.idClass.BedId;
import lombok.*;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.time.LocalDateTime;

@Entity
@IdClass(BedId.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Bed {
    @Id
    private String hpID; // 기관 코드

    @Id
    private String time; // 시간 (String)

    private LocalDateTime localDateTime; // 시간 (LocalDateTime)
    
    private Integer count; // 병상 수

    public void increaseByOneMinute() {
        String[] timeParts = this.time.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);

        minute += 1;

        if (minute == 60) {
            hour += 1;
            minute = 0;
        }

        if (hour == 24) {
            hour = 0;
        }

        this.time = String.format("%02d:%02d", hour, minute);
    }
}
