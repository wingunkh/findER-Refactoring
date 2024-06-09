package com.finder.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public abstract class APIService {
    Logger logger = LoggerFactory.getLogger(APIService.class);

    protected String sendHttpRequest(String urlString, String key) {
        // URL 연결을 위한 HttpURLConnection 객체 생성
        HttpURLConnection conn = null;
        // 응답 데이터를 읽기 위한 BufferedReader 객체 생성
        BufferedReader bufferedReader = null;

        try {
            // URL 객체 생성
            URL url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();
            // HTTP GET 요청을 설정
            conn.setRequestMethod("GET");

            // HTTP 헤더에 Authorization 추가 (key가 null이 아니라면)
            if (key != null) {
                conn.setRequestProperty("Authorization", "KakaoAK " + key);
            }

            // HTTP 응답 코드를 저장
            int responseCode = conn.getResponseCode();

            if (responseCode >= 200 && responseCode < 300) {
                bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
            }

            // 응답 데이터를 저장하기 위한 StringBuilder 객체 초기화
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            // BufferedReader를 사용하여 한 줄씩 읽으면서 StringBuilder에 추가
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            // 읽은 응답 데이터를 문자열로 반환
            return stringBuilder.toString();
        } catch (IOException e) {
            logger.error("HTTP Request Error", e);

            throw new RuntimeException(e.toString(), e);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    logger.error("Closing BufferedReader Error", e);
                }
            }

            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}
