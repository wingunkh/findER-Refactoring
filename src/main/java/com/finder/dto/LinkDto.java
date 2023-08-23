package com.finder.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LinkDto {
    private Long userId;

    private Long linkedUserId;
}