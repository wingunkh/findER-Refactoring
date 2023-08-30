package com.finder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BedDataDto {
    String successTime;
    double percent;
    double otherPercent;
    List<Integer> twoAgoList;
    List<Integer> oneAgoList;
}
