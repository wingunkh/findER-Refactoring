package com.finder.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class KakaoMobilityService {
    @Value("${kakao.key}")
    private String REST_KEY;
    private Map<String, String> map = new HashMap<>();

    public Map<String, String> requestKakaoMobilityApi(Double originLat, Double originLon, Double destinationLat, Double destinationLon) {
        String urlStr =
                "https://apis-navi.kakaomobility.com/v1/directions?origin=" + originLon + "," + originLat +
                        "&destination=" + destinationLon + "," + destinationLat +
                        "&waypoints=&priority=RECOMMEND&car_fuel=GASOLINE&car_hipass=false&alternatives=false&road_details=false";
        BufferedReader br = null;
        JSONObject jsonObject = new JSONObject();
        ObjectMapper mapper = new ObjectMapper();

        try {
            URL url = new URL(urlStr);

            // API 요청
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("Authorization", "KakaoAK " + REST_KEY); // 인증키 등록

            //응답값 뽑아서 JSONObject로 변환
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            if(br != null) jsonObject = mapper.readValue(br, JSONObject.class);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        ArrayList<LinkedHashMap> routes = (ArrayList)jsonObject.get("routes");
        LinkedHashMap routesMap = routes.get(0);
        LinkedHashMap summaryMap = (LinkedHashMap) routesMap.get("summary");

        // 거리, 소요 시간 조회
        Double distance = (Integer) summaryMap.get("distance") + 0.;
        Integer duration = (Integer) summaryMap.get("duration");

        // 거리, 도착 예정 시간 조회
        calcalate(distance, duration);

        return map;
    }

    // 거리, 도착 예정 시간 조회
    public void calcalate(Double distance, Integer duration) {
        distance = Math.round((distance / 1000) * 10.0) / 10.0;

        LocalDateTime now = LocalDateTime.now();
        int minute = duration/60;
        int arriveHour = now.getHour();;
        int arriveminute = now.getMinute() + minute;
        if(arriveminute >= 60) {
            arriveHour += (arriveminute / 60);
            arriveminute = (arriveminute % 60);
        }
        String arriveTime;
        if (arriveHour>=12) {
            if(arriveHour == 12) arriveTime = "오후 " + arriveHour + "시 " + arriveminute + "분";
            else arriveTime = "오후 " + (arriveHour-12) + "시 " + arriveminute + "분";
        } else arriveTime = "오전 " + arriveHour + "시 " + arriveminute + "분";

        map.put("distance", distance + "");
        map.put("arriveTime", arriveTime);
    }
}
