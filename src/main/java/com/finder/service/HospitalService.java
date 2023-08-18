package com.finder.service;

import com.finder.domain.Hospital;
import com.finder.dto.HospitalPreviewDto;
import com.finder.dto.MapRequestDto;
import com.finder.dto.MapResponseDto;
import com.finder.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HospitalService {
    private final HospitalRepository hospitalRepository;
    private final KakaoMobilityService kakaoMobilityService;

    public List<MapResponseDto> findHospitalMap(MapRequestDto mapRequestDto) {
        List<Hospital> hospitals = hospitalRepository.findHospitalMap(mapRequestDto.getSouthWestLat(), mapRequestDto.getSouthWestLon(),
                mapRequestDto.getNorthEastLat(), mapRequestDto.getNorthEastLon());
        List<MapResponseDto> mapResponseDtos = hospitals.stream()
                .map(hospital -> new MapResponseDto(hospital.getId(), hospital.getLatitude(), hospital.getLongitude()))
                .collect(Collectors.toList());

        return mapResponseDtos;
    }

    public HospitalPreviewDto findHospitalPreview(Long id, Double lat, Double lon) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("찾으시는 병원이 존재하지 않습니다."));

        // 거리, 도착 예정 시간 조회
        Map<String, String> map = kakaoMobilityService.requestKakaoMobilityApi(lat, lon, hospital.getLatitude(), hospital.getLongitude());

        HospitalPreviewDto hospitalPreviewDto = new HospitalPreviewDto(hospital.getName(), hospital.getAddress(), hospital.getRepresentativeContact(), hospital.getEmergencyContact(),
                null, map.get("distance"), map.get("arriveTime"));

        return hospitalPreviewDto;
    }
}
