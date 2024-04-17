package com.finder.service;

import com.finder.domain.Bed;
import com.finder.domain.Hospital;
import com.finder.repository.BedRepository;
import com.finder.repository.HospitalRepository;
import com.finder.xml.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class PublicDataAPIService {
    @Value("${api.key}")
    private String key;
    Logger logger = LoggerFactory.getLogger(PublicDataAPIService.class);
    List<Bed> bedList = new ArrayList<>();
    Boolean isFirst = true;

    private final BedRepository bedRepository;
    private final HospitalRepository hospitalRepository;

    // 응급실 실시간 병상 수 갱신 (1분 간격)
    @Scheduled(cron = "1 * * * * *") // 스케줄링 (매 분 1초)
    public void updateBedsCountEveryMinute() {
        long startTime = System.currentTimeMillis();
        LocalDateTime localDateTime = LocalDateTime.now();
        String time = localDateTime.format(DateTimeFormatter.ofPattern("HH:mm"));

        try {
            List<XmlModel1.Item> itemList = getEmergencyRoomData();
            bedList.clear();

            for (XmlModel1.Item item : itemList) {
                Bed bed = Bed.builder()
                        .hpID(item.getHpid())
                        .time(time)
                        .localDateTime(localDateTime)
                        .count(item.getHvec()).build();

                bedList.add(bed);
            }

            bedRepository.saveAll(bedList);
            logger.info(time + " updateBedsCountEveryMinute() 함수 소요 시간: {}ms", (System.currentTimeMillis() - startTime));
        } catch (Exception e) {
            errorHandler(time);
        }
    }

    // 응급실 정보 저장 (최초 실행 시)
    public void updateEmergencyRoomInfo() {
        long startTime = System.currentTimeMillis();
        LocalDateTime localDateTime = LocalDateTime.now();
        String time = localDateTime.format(DateTimeFormatter.ofPattern("HH:mm"));

        String tmp = "http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEgytBassInfoInqire" +
                "?" + URLEncoder.encode("serviceKey", StandardCharsets.UTF_8) + "=" + key;

        try {
            List<XmlModel1.Item> itemList1 = getEmergencyRoomData();

            for (XmlModel1.Item item : itemList1) {
                // 응급의료기관 기본정보 조회 API 호출
                String xmlData = sendHttpRequest(tmp + "&" + URLEncoder.encode("HPID", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(item.getHpid(), StandardCharsets.UTF_8));
                StringReader reader = new StringReader(xmlData);

                JAXBContext jaxbContext = JAXBContext.newInstance(XmlModel2.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

                // XML 데이터를 Java 객체로 변환 (언마샬링)
                XmlModel2 xmlModel2 = (XmlModel2) unmarshaller.unmarshal(reader);
                List<XmlModel2.Item> itemList2 = xmlModel2.getBody().getItems().getItem();

                Hospital hospital = Hospital.builder()
                        .hpid(item.getHpid())
                        .name(itemList2.get(0).getDutyName())
                        .address(itemList2.get(0).getDutyAddr())
                        .mapAddress(itemList2.get(0).getDutyMapimg())
                        .tel(itemList2.get(0).getDutyTel1())
                        .ERTel(itemList2.get(0).getDutyTel3())
                        .ambulance(item.getHvamyn())
                        .CT(item.getHvctayn())
                        .MRI(item.getHvmriayn()).build();

                hospitalRepository.save(hospital);
            }
        } catch (Exception e) {
            logger.error("updateEmergencyRoomInfo() Error", e);
        }

        isFirst = true;
        logger.info(time + " updateEmergencyRoomInfo() 함수 소요 시간: {}ms", (System.currentTimeMillis() - startTime));
    }

    public String sendHttpRequest(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        // HTTP Response 저장
        int responseCode = conn.getResponseCode();
        BufferedReader bufferedReader;

        if (responseCode >= 200 && responseCode < 300) {
            bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            bufferedReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder stringBuilder = new StringBuilder();
        String line;

        try {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            logger.error("HTTP Response Error", e);
        }

        bufferedReader.close();
        conn.disconnect();

        return stringBuilder.toString();
    }

    public List<XmlModel1.Item> getEmergencyRoomData() throws Exception {
        String tmp = "http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire" +
                "?" + URLEncoder.encode("serviceKey", StandardCharsets.UTF_8) + "=" + key +
                "&" + URLEncoder.encode("STAGE1", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("", StandardCharsets.UTF_8) + // 주소(시도)
                "&" + URLEncoder.encode("STAGE2", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("", StandardCharsets.UTF_8) + // 주소(시군구)
                "&" + URLEncoder.encode("pageNo", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("1", StandardCharsets.UTF_8) + // 페이지 번호
                "&" + URLEncoder.encode("numOfRows", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("410", StandardCharsets.UTF_8); // 목록 건수

        // 응급실 실시간 가용병상정보 조회 API 호출
        String xmlData = sendHttpRequest(tmp);
        StringReader reader = new StringReader(xmlData);

        JAXBContext jaxbContext = JAXBContext.newInstance(XmlModel1.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        // XML 데이터를 Java 객체로 변환 (언마샬링)
        XmlModel1 xmlModel1 = (XmlModel1) unmarshaller.unmarshal(reader);
        List<XmlModel1.Item> itemList = xmlModel1.getBody().getItems().getItem();

        logger.info("Data 수 : {}", itemList.size());

        if (isFirst) {
            isFirst = false;
        } else if (!Objects.equals(bedList.size(), itemList.size())) {
            throw new Exception("특정 응급실에 대한 데이터가 누락되었습니다.");
        }

        return itemList;
    }

    public void errorHandler(String time) {
        long startTime = System.currentTimeMillis();

        for (Bed bed : bedList) {
            bed.increaseByOneMinute();
            bed.setLocalDateTime(bed.getLocalDateTime().plusMinutes(1));
        }

        bedRepository.saveAll(bedList);
        logger.info(time + " errorHandler() 함수 소요 시간: {}ms", (System.currentTimeMillis() - startTime));
    }
}
