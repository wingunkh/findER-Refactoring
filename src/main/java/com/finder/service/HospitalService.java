package com.finder.service;

import com.finder.domain.Hospital;
import com.finder.dto.BedDataDto;
import com.finder.dto.HospitalDetailDto;
import com.finder.dto.HospitalPreviewDto;
import com.finder.dto.MapResponseDto;
import com.finder.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HospitalService {
    private final HospitalRepository hospitalRepository;
    private final KakaoMobilityService kakaoMobilityService;
    private final BedService bedService;

    public List<MapResponseDto> findHospitalMap(Double swLat, Double swLon, Double neLat, Double neLon) {
        List<Hospital> hospitals = hospitalRepository.findHospitalMap(swLat, swLon, neLat, neLon);
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

        HospitalPreviewDto hospitalPreviewDto = new HospitalPreviewDto(hospital.getId(), hospital.getName(), hospital.getAddress(), hospital.getRepresentativeContact(), hospital.getEmergencyContact(),
                null, Double.parseDouble(map.get("distance")), map.get("arriveTime"));

        return hospitalPreviewDto;
    }

    public List<HospitalPreviewDto> findHospitalList(Double lat, Double lon) {
        List<Hospital> hospitals = hospitalRepository.findAll();
        List<Hospital> nearbyHospitals = new ArrayList<>();
        for (Hospital h : hospitals) {
            Double findLat = h.getLatitude();
            Double findLon = h.getLongitude();
            if(findLat != null && findLon != null) {
                double distance = getDistance(lat, lon, findLat, findLon);
                //반경 5km이내 병원
                if(distance/1000 <= 5) nearbyHospitals.add(h);
            }
        }

        // 거리, 도착 예정 시간 조회
        List<HospitalPreviewDto> hospitalPreviewDtos = nearbyHospitals.stream()
                .map(nearbyHospital ->  {
                    Map<String, String> map = kakaoMobilityService.requestKakaoMobilityApi(lat, lon, nearbyHospital.getLatitude(), nearbyHospital.getLongitude());
                    return new HospitalPreviewDto(nearbyHospital.getId(), nearbyHospital.getName(), nearbyHospital.getAddress(), nearbyHospital.getRepresentativeContact(),
                            nearbyHospital.getEmergencyContact(), null, Double.parseDouble(map.get("distance")), map.get("arriveTime"));
                }).collect(Collectors.toList());

        // 거리 순 내림차순 정렬
        Collections.sort(hospitalPreviewDtos, new Comparator<HospitalPreviewDto>() {
            @Override
            public int compare(HospitalPreviewDto o1, HospitalPreviewDto o2) {
                if(o1.getDistance() - o2.getDistance() < 0) return -1;
                else if(o1.getDistance() - o2.getDistance() == 0) return 0;
                else return 1;
            }
        });

        return hospitalPreviewDtos;
    }

    // 직선 거리 계산
    public double getDistance(double lat1, double lon1, double lat2, double lon2) {
        double earthRadius = 6371.;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = earthRadius * c * 1000;
        return d;
    }

    public HospitalDetailDto findHospitalDetail(Long id, Double lat, Double lon) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("찾으시는 병원이 존재하지 않습니다."));
        Boolean ambulance, ct, mri;

        if(hospital.getAmbulance().equals("Y")) ambulance = true;
        else ambulance = false;

        if(hospital.getCt().equals("Y")) ct = true;
        else ct = false;

        if(hospital.getMri().equals("Y")) mri = true;
        else mri = false;

        // 거리, 도착 예정 시간 조회
        Map<String, String> map = kakaoMobilityService.requestKakaoMobilityApi(lat, lon, hospital.getLatitude(), hospital.getLongitude());

        // 병상수, 병상 데이터 조회
        BedDataDto bedDataDto = bedService.findByRecentV2(hospital.getName());
        int hvec = bedDataDto.getTwoAgoList().get(8);

        HospitalDetailDto hospitalDetailDto = new HospitalDetailDto(hospital.getName(), hospital.getAddress(), hospital.getSimpleAddress(),
                hospital.getRepresentativeContact(), hospital.getEmergencyContact(), ambulance, ct, mri,
                hvec, Double.parseDouble(map.get("distance")), map.get("arriveTime"), hospital.getLatitude(),
                hospital.getLongitude(), bedDataDto);

        return hospitalDetailDto;
    }
}
