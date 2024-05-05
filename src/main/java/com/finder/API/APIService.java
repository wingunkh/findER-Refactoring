package com.finder.API;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

public abstract class APIService {
    public String sendHttpRequest(String urlString, String key) throws IOException {
        // URL 객체 생성
        URL url = new URL(urlString);
        // URL 연결을 위한 HttpURLConnection 객체 생성
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // HTTP GET 요청을 설정
        conn.setRequestMethod("GET");
        // HTTP 헤더에 Authorization 추가 (key가 null이 아니라면)
        Optional.ofNullable(key).ifPresent(k -> conn.setRequestProperty("Authorization", "KakaoAK " + k));
        // HTTP 응답 코드를 저장
        int responseCode = conn.getResponseCode();
        // 응답 데이터를 읽기 위한 BufferedReader 객체 초기화
        BufferedReader bufferedReader;

        if (responseCode >= 200 && responseCode < 300) {
            bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            bufferedReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        // 응답 데이터를 저장하기 위한 StringBuilder 객체 초기화
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        try {
            // BufferedReader를 사용하여 한 줄씩 읽으면서 StringBuilder에 추가
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (Exception e) {
            Logger logger = LoggerFactory.getLogger(APIService.class);
            logger.error("HTTP Response Error", e);
        }

        bufferedReader.close();
        conn.disconnect();

        // 읽은 응답 데이터를 문자열로 반환
        return stringBuilder.toString();
    }
}
