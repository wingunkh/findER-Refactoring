package com.finder.API;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class KakaoMobilityAPIService extends APIService{
    @Value("${kakao.key}")
    private String key;
    private final Logger logger = LoggerFactory.getLogger(KakaoMobilityAPIService.class);
    private final Map<String, String> map = new HashMap<>();

    // 응급실 거리 & 예상 도착 시간 조회
    public Map<String, String> getDistanceAndETA(Double originLat, Double originLon, Double destinationLat, Double destinationLon) {
        String urlString = "https://apis-navi.kakaomobility.com/v1/directions?origin=" + originLon + "," + originLat +
                "&destination=" + destinationLon + "," + destinationLat +
                "&waypoints=&priority=RECOMMEND&car_fuel=GASOLINE&car_hipass=false&alternatives=false&road_details=false";

        ObjectMapper mapper = new ObjectMapper();
        JSONObject jsonObject = null;

        try {
            String data = sendHttpRequest(urlString, key);
            // JSON 데이터를 Map으로 변환
            jsonObject = mapper.readValue(data, JSONObject.class);
        } catch (Exception e) {
            logger.error("getDistanceAndETA() Error", e);
        }

        if (jsonObject == null) {
            return null;
        }

        ArrayList<Map<String, Object>> routes = (ArrayList<Map<String, Object>>) jsonObject.get("routes"); // 경로 정보
        Map<String, Object> routesMap = routes.get(0); // 첫번째 경로 선택
        Map<String, Object> summaryMap = (Map<String, Object>) routesMap.get("summary"); // 경로 요약 정보

        Double distance = ((Double) summaryMap.get("distance")); // 거리(미터)
        Integer duration = (Integer) summaryMap.get("duration"); // 목적지까지 소요 시간(초)

        // 거리, 도착 예정 시간 계산 후 저장
        calculate(distance, duration);

        return map;
    }

    // 거리, 도착 예정 시간 계산
    private void calculate(Double distance, Integer duration) {
        // 거리를 km 단위로 변환하고 소수점 첫째 자리까지 반올림
        distance = Math.round((distance / 1000.0) * 10.0) / 10.0;

        LocalDateTime now = LocalDateTime.now();
        int minute = duration / 60;
        int arriveHour = now.getHour();
        int arriveMinute = now.getMinute() + minute;

        if (arriveMinute >= 60) {
            arriveHour += arriveMinute / 60;
            arriveMinute %= 60;
        }

        String ETA = arriveHour >= 12 ?
                String.format("오후 %d시 %d분", arriveHour == 12 ? 12 : arriveHour - 12, arriveMinute) :
                String.format("오전 %d시 %d분", arriveHour, arriveMinute);

        map.put("distance", String.valueOf(distance));
        map.put("ETA", ETA);
    }
}
