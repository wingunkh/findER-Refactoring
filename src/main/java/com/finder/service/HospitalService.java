package com.finder.service;

import com.finder.domain.Hospital;
import com.finder.dto.MapRequestDto;
import com.finder.dto.MapResponseDto;
import com.finder.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HospitalService {
    private final HospitalRepository hospitalRepository;

    public List<MapResponseDto> findHospitalMap(MapRequestDto mapRequestDto) {
        List<Hospital> hospitals = hospitalRepository.findHospitalMap(mapRequestDto.getSouthWestLat(), mapRequestDto.getSouthWestLon(),
                mapRequestDto.getNorthEastLat(), mapRequestDto.getNorthEastLon());
        List<MapResponseDto> mapResponseDtos = hospitals.stream()
                .map(hospital -> new MapResponseDto(hospital.getId(), hospital.getLatitude(), hospital.getLongitude()))
                .collect(Collectors.toList());

        return mapResponseDtos;
    }
}
