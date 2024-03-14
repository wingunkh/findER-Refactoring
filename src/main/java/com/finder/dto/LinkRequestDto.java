package com.finder.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LinkRequestDto {
    private String phoneNumber;

    private String linkedSerialNumber;
}
