package com.finder.controller;

import com.finder.service.ERService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/er")
public class ERController {
    private final ERService erService;

    // 전체 응급실 위치 정보 조회
    @GetMapping("/location")
    public ResponseEntity<Object> findAllERLocation() {
        return ResponseEntity.status(HttpStatus.OK).body(erService.findAllERLocation());
    }

    // 응급실 프리뷰
    @GetMapping("/preview/{hpID}")
    public ResponseEntity<Object> findERPreview(@PathVariable String hpID, @RequestParam Double lat, @RequestParam Double lon) {
        return ResponseEntity.status(HttpStatus.OK).body(erService.findERPreview(hpID, lat, lon));
    }


    // 응급실 상세정보 조회
    @GetMapping("/detailView/{hpID}")
    public ResponseEntity<Object> findERDetail(@PathVariable String hpID, @RequestParam Double lat, @RequestParam Double lon) {
        return ResponseEntity.status(HttpStatus.OK).body(erService.findERDetail(hpID, lat, lon));
    }
}
