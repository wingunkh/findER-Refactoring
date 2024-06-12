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
        String urlString = "https://apis-navi.kakaomobility.com/v1/directions?origin=" + originLon + "," + originLat +
                "&destination=" + destinationLon + "," + destinationLat +
                "&waypoints=&priority=RECOMMEND&car_fuel=GASOLINE&car_hipass=false&alternatives=false&road_details=false";
        String data;
        JSONObject jsonObject;
        ObjectMapper mapper = new ObjectMapper();

        try {
            data = sendHttpRequest(urlString, key);
            jsonObject = mapper.readValue(data, JSONObject.class);
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
            logger.error("getDistanceAndETA() Error", e);
        }

        return Map.of("distance", "0", "duration", "0분");
    }

    // 거리(m) -> 거리(km) 변환 (소수점 첫째 자리까지 반올림)
    private Double convertDistance(Double distance) {
        return Math.round((distance / 1000.0) * 10.0) / 10.0;
    }

    // 예상 이동 소요 시간 (초) -> 예상 이동 소요 시간 ("*시간 *분") 변환
    private String convertDuration(Integer duration) {
        int tmp = duration / 60; // 초 -> 분
        int hour, minute = 0;

        hour = tmp / 60;
        minute = tmp % 60;

        if (hour == 0) {
            return minute + "분";
        } else {
            return hour + "시간 " + minute + "분";
        }
    }
}
