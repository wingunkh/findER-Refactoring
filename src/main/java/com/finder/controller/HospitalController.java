package com.finder.controller;

import com.finder.dto.HospitalPreviewDto;
import com.finder.dto.MapResponseDto;
import com.finder.service.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hospitals")
public class HospitalController {
    private final HospitalService hospitalService;

    //지도에 보여지는 부분에 대한 병원 정보 반환
    @GetMapping("/map")
    public ResponseEntity<List<MapResponseDto>> findHospitalMap(@RequestParam Double swLat, @RequestParam Double swLon,
                                                                @RequestParam Double neLat, @RequestParam Double neLon) {
        return ResponseEntity.ok(hospitalService.findHospitalMap(swLat, swLon, neLat, neLon));
    }

    //병원 미리보기
    @GetMapping("/preview/{id}")
    public ResponseEntity<HospitalPreviewDto> findHospitalPreview(@PathVariable Long id, @RequestParam Double lat, @RequestParam Double lon) {
        return ResponseEntity.ok(hospitalService.findHospitalPreview(id, lat, lon));
    }

    //병원 목록 조회
    @GetMapping("/list")
    public ResponseEntity findHospitalList(@RequestParam Double lat, @RequestParam Double lon) {
        return ResponseEntity.ok(hospitalService.findHospitalList(lat, lon));
    }
}
