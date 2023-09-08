package com.finder.domain;

import com.finder.idClass.BedId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.time.LocalDateTime;

@IdClass(BedId.class)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Bed {
    @Id
    private String name;

    @Id
    private String time;

    private LocalDateTime localDateTime;
    
    private Integer count;
}