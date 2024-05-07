package com.finder.service;

import com.finder.api.KakaoMobilityAPIService;
import com.finder.domain.ER;
import com.finder.dto.ERDetailDto;
import com.finder.dto.ERPreviewDto;
import com.finder.dto.MarkerResponseDto;
import com.finder.repository.ERRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ERService {
    private final ERRepository ERRepository;
    private final BedService bedService;
    private final KakaoMobilityAPIService kakaoMobilityAPIService;

    @Transactional(readOnly = true)
    public ResponseEntity<Object> findNearbyER(Double swLat, Double swLon, Double neLat, Double neLon) {
        List<ER> erList = ERRepository.findByLatitudeBetweenAndLongitudeBetween(swLat, swLon, neLat, neLon);
        List<MarkerResponseDto> markerResponseDtoList = new ArrayList<>();

        for (ER er : erList) {
            MarkerResponseDto markerResponseDto = MarkerResponseDto.builder()
                    .hpID(er.getHpID())
                    .lat(er.getLatitude())
                    .lon(er.getLongitude())
                    .build();

            markerResponseDtoList.add(markerResponseDto);
        }

        return ResponseEntity.status(HttpStatus.OK).body(markerResponseDtoList);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> findERPreview(String hpID, Double lat, Double lon) {
        Optional<ER> optionalER = ERRepository.findById(hpID);

        if (optionalER.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
        }

        ER er = optionalER.get();

        // 병상 수 조회
        Integer bedCount = bedService.getBedCount(er.getHpID());

        // 거리, 도착 예정 시간 조회
        Map<String, String> map = kakaoMobilityAPIService.getDistanceAndETA(lat, lon, er.getLatitude(), er.getLongitude());

        ERPreviewDto erPreviewDto = ERPreviewDto.builder()
                .hpID(er.getHpID())
                .name(er.getName())
                .address(er.getAddress())
                .tel(er.getTel())
                .ERTel(er.getERTel())
                .bedCount(bedCount)
                .distance(Double.parseDouble(map.get("distance")))
                .arrivalTime(map.get("arriveTime"))
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(erPreviewDto);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> findERDetail(String hpID, Double lat, Double lon) {
        Optional<ER> optionalER = ERRepository.findById(hpID);

        if (optionalER.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
        }

        ER er = optionalER.get();

        // 병상 수 조회
        Integer bedCount = bedService.getBedCount(er.getHpID());

        // 거리, 도착 예정 시간 조회
        Map<String, String> map = kakaoMobilityAPIService.getDistanceAndETA(lat, lon, er.getLatitude(), er.getLongitude());

        ERDetailDto ERDetailDto = com.finder.dto.ERDetailDto.builder()
                .name(er.getName())
                .address(er.getAddress())
                .mapAddress(er.getMapAddress() != null ? er.getMapAddress() : "")
                .tel(er.getTel())
                .ERTel(er.getERTel())
                .ambulance(er.getAmbulance())
                .CT(er.getCT())
                .MRI(er.getMRI())
                .latitude(er.getLatitude())
                .longitude(er.getLongitude())
                .subject(er.getSubject())
                .bedCount(bedCount)
                .distance(Double.parseDouble(map.get("distance")))
                .arrivalTime(map.get("arriveTime"))
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(ERDetailDto);
    }
}
