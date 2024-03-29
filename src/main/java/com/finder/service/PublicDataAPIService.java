package com.finder.service;

import com.finder.domain.Hospital;
import com.finder.repository.HospitalRepository;
import com.finder.xml.Item;
import com.finder.xml.XmlModel;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PublicDataAPIService {
    @Value("${api.key}")
    private String key;
    private static final Logger logger = LoggerFactory.getLogger(PublicDataAPIService.class);
    private final HospitalRepository hospitalRepository;

    // 병원 기본정보 조회
    public void getHospitalInfo() throws IOException, JAXBException {
        long startTime = System.currentTimeMillis();

        // HTTP Request 생성
        String urlBuilder = "http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEgytBassInfoInqire" +
                "?" + URLEncoder.encode("serviceKey", StandardCharsets.UTF_8) + "=" + key +
                "&" + URLEncoder.encode("HPID", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("", StandardCharsets.UTF_8) +
                "&" + URLEncoder.encode("pageNo", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("", StandardCharsets.UTF_8) +
                "&" + URLEncoder.encode("numOfRows", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("103000", StandardCharsets.UTF_8);
        URL url = new URL(urlBuilder);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        // HTTP Request 전송 및 HTTP Response 상태 코드 확인
        int responseCode = conn.getResponseCode();
        logger.info("Response code: {}", responseCode);

        // HTTP Response 저장
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

        // XML 데이터를 Java 객체로 변환 (언마샬링)
        String xmlData = stringBuilder.toString();
        StringReader reader = new StringReader(xmlData);

        JAXBContext jaxbContext = JAXBContext.newInstance(XmlModel.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        XmlModel xmlModel = (XmlModel) unmarshaller.unmarshal(reader);
        List<Item> items = xmlModel.getBody().getItems().getItem();

        logger.info("Data 수 : {}", items.size());

        // 병원 데이터 저장
        List<Hospital> hospitals = new ArrayList<>();

        for (Item item : items) {
            Hospital hospital = Hospital.builder()
                    .name(item.getDutyName())
                    .address(item.getDutyAddr())
                    .mapAddress(item.getDutyMapimg())
                    .tel(item.getDutyTel1())
                    .ERTel(item.getDutyTel3())
                    .ambulance(item.getHvamyn())
                    .CT(item.getHvctayn())
                    .MRI(item.getHvmriayn())
                    .latitude(item.getWgs84Lat())
                    .longitude(item.getWgs84Lon()).build();

            hospitals.add(hospital);
            logger.info(hospital.getAddress());
        }

        hospitalRepository.saveAll(hospitals);
        logger.info("getHospitalInfo 함수 소요 시간: {}ms", (System.currentTimeMillis() - startTime));
    }
}
