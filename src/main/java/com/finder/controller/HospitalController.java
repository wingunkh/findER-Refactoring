package com.finder.controller;

import com.finder.dto.MapRequestDto;
import com.finder.dto.MapResponseDto;
import com.finder.service.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hospitals")
public class HospitalController {
    private final HospitalService hospitalService;

    //지도에 보여지는 부분에 대한 병원 정보 반환
    @GetMapping("/map")
    public ResponseEntity<List<MapResponseDto>> findHospitalMap(@RequestBody MapRequestDto mapRequestDto) {
        return ResponseEntity.ok(hospitalService.findHospitalMap(mapRequestDto));
    }
}
