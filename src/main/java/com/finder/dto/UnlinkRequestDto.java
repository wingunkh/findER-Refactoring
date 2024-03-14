package com.finder.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UnlinkRequestDto {
    private String phoneNumber1;

    private String phoneNumber2;
}
