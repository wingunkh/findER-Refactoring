package com.finder.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class BedDataDto {
    String successTime;

    double percent;

    double otherPercent;

    List<Integer> twoAgoList;

    List<Integer> oneAgoList;
}
