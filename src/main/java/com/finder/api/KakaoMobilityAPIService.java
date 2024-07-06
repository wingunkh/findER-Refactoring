package com.finder.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class KakaoMobilityAPIService extends APIService {
    @Value("${kakao.key}")
    private String key;
    private final Logger logger = LoggerFactory.getLogger(KakaoMobilityAPIService.class);
    private final Map<String, String> distanceAndDuration = new HashMap<>();

    // 응급실 거리 & 예상 이동 소요 시간 조회
    public Map<String, String> getDistanceAndDuration(Double originLat, Double originLon, Double destinationLat, Double destinationLon) {
        try {
            String urlString = buildUrlString(originLat, originLon, destinationLat, destinationLon);
            String httpResponse = sendHttpRequest(urlString, key);
            JSONObject jsonObject = new ObjectMapper().readValue(httpResponse, JSONObject.class);
            ArrayList<HashMap<String, Object>> routes = (ArrayList<HashMap<String, Object>>) jsonObject.get("routes"); // 경로 정보

            if (!routes.isEmpty()) {
                HashMap<String, Object> routesMap = routes.get(0); // 첫번째 경로 선택
                HashMap<String, Object> summaryMap = (HashMap<String, Object>) routesMap.get("summary"); // 경로 요약 정보

                if (summaryMap != null) {
                    Double distance = ((Integer) summaryMap.get("distance")).doubleValue(); // 거리(m)
                    Double convertedDistance = convertDistance(distance); // 거리(km)
                    Integer duration = (Integer) summaryMap.get("duration"); // 예상 이동 소요 시간(초)
                    String convertedDuration = convertDuration(duration); // 예상 이동 소요 시간 ("*시간 *분")

                    distanceAndDuration.put("distance", String.valueOf(convertedDistance));
                    distanceAndDuration.put("duration", convertedDuration);

                    return distanceAndDuration;
                }
            }
        } catch (RuntimeException | IOException e) {
            logger.error("getDistanceAndDuration() Error", e);
        }

        return Map.of("distance", "0", "duration", "0분");
    }

    private String buildUrlString(Double originLat, Double originLon, Double destinationLat, Double destinationLon) {
        String baseUrl = "https://apis-navi.kakaomobility.com/v1/directions";
        String waypoints = ""; // 경유지
        String priority = "RECOMMEND"; // 경로 탐색 우선순위 옵션
        String car_fuel = "GASOLINE"; // 차량 유종 정보
        String car_hipass = "false"; // 하이패스 장착 여부
        String alternatives = "false"; // 대안 경로 제공 여부
        String road_details = "false"; // 상세 도로 정보 제공 여부

        return String.format("%s?origin=%f,%f&destination=%f,%f&waypoints=%s&priority=%s&car_fuel=%s&car_hipass=%s&alternatives=%s&road_details=%s",
                baseUrl, originLon, originLat, destinationLon, destinationLat, waypoints, priority, car_fuel, car_hipass, alternatives, road_details);
    }

    // 거리(m) -> 거리(km) 변환 (소수점 첫째 자리까지 반올림)
    private Double convertDistance(Double distance) {
        return Math.round((distance / 1000.0) * 10.0) / 10.0;
    }

    // 예상 이동 소요 시간 (초) -> 예상 이동 소요 시간 ("*시간 *분") 변환
    private String convertDuration(Integer duration) {
        int tmp = duration / 60; // 초 -> 분
        int hour = tmp / 60;
        int minute = tmp % 60;

        if (hour == 0) {
            return minute + "분";
        } else {
            return hour + "시간 " + minute + "분";
        }
    }
}
