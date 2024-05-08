package com.finder.controller;

import com.finder.service.ERService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/er")
public class ERController {
    private final ERService ERService;

    // 사용자 주변 응급실 조회
    @GetMapping("/nearBy/{swLat}/{swLon}/{neLat}/{neLon}")
    public ResponseEntity<Object> findNearbyER(@PathVariable Double swLat, @PathVariable Double swLon, @PathVariable Double neLat, @PathVariable Double neLon) {
        return ERService.findNearbyER(swLat, swLon, neLat, neLon);
    }

    // 응급실 프리뷰
    @GetMapping("/preview/{hpID}")
    public ResponseEntity<Object> findERPreview(@PathVariable String hpID, @RequestParam Double lat, @RequestParam Double lon) {
        return ERService.findERPreview(hpID, lat, lon);
    }


    // 응급실 상세정보 조회
    @GetMapping("/detailView/{hpID}")
    public ResponseEntity<Object> findERDetail(@PathVariable String hpID, @RequestParam Double lat, @RequestParam Double lon) {
        return ERService.findERDetail(hpID, lat, lon);
    }
}
