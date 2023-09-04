package com.finder.xml;

import com.finder.domain.Hospital;
import com.finder.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class XmlService {
    private HashMap<String, Item> map = new HashMap();
    private final HospitalRepository hospitalRepository;

    @Value("${api.key}")
    private String key;

    // 응급실 실시간 가용병상정보 조회
    public void callApi1() throws IOException, JAXBException {
        // HTTP Request 생성
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire"); // URL
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + key); //Service Key
        urlBuilder.append("&" + URLEncoder.encode("STAGE1","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); // 주소(시도)
        urlBuilder.append("&" + URLEncoder.encode("STAGE2","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); // 주소(시군구)
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); // 페이지 번호
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); // 목록 건수
        URL url = new URL(urlBuilder.toString());

        // HTTP Request 전송
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        // 현재 시간 기록
        long startTime = System.currentTimeMillis();

        // HTTP Response 상태 코드 확인
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        // HTTP Response 저장
        StringBuilder sb = new StringBuilder();
        String line;

        try {
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        rd.close();
        conn.disconnect();

        String xmlData = sb.toString();

        // XML 데이터를 Java 객체로 변환 (언마샬링)
        JAXBContext jaxbContext = JAXBContext.newInstance(XmlModel.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        StringReader reader = new StringReader(xmlData);
        XmlModel xmlModel = (XmlModel) unmarshaller.unmarshal(reader);

        List<Item> items = xmlModel.getBody().getItems().getItem();
        for (Item item : items) {
            if(!map.containsKey(item.getDutyName()))
                map.put(item.getDutyName(), item);
        }
        System.out.println("map size: " + map.size()); // 411

        System.out.println("Data 수 : " + xmlModel.getBody().getItems().getItem().size());
        long endTime = System.currentTimeMillis();
        System.out.println("callApi() 함수 소요 시간: " + (endTime - startTime) + "ms");
    }

    // 응급의료기관 기본정보 조회
    public void callApi2() throws IOException, JAXBException {
        // HTTP Request 생성
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEgytBassInfoInqire"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + key); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("HPID","UTF-8") + "=" + URLEncoder.encode("", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("39700", "UTF-8")); /*목록 건수*/
        URL url = new URL(urlBuilder.toString());

        // HTTP Request 전송
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        // 현재 시간 기록
        long startTime = System.currentTimeMillis();

        // HTTP Response 상태 코드 확인
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        // HTTP Response 저장
        StringBuilder sb = new StringBuilder();
        String line;

        try {
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        rd.close();
        conn.disconnect();

        String xmlData = sb.toString();

        // XML 데이터를 Java 객체로 변환 (언마샬링)
        JAXBContext jaxbContext = JAXBContext.newInstance(XmlModel.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        StringReader reader = new StringReader(xmlData);
        XmlModel hospitalResponse = (XmlModel) unmarshaller.unmarshal(reader);

        System.out.println("Data 수 : " + hospitalResponse.getBody().getItems().getItem().size());
        List<Item> items = hospitalResponse.getBody().getItems().getItem();

        long count = 0;
        for (Item item : items) {
            if(map.containsKey(item.getDutyName())) {
                Item getItem = map.get(item.getDutyName());
                getItem.update(item.getDutyAddr(), item.getDutyMapimg(), item.getDutyTel1(), item.getWgs84Lat(), item.getWgs84Lon());
                map.put(item.getDutyName(), getItem);
                count += 1;
            }
        }

        List<Hospital> hospitals = new ArrayList<>();
        for (String key : map.keySet()) {
            Item item = map.get(key);
            Hospital hospital = Hospital.builder()
                    .name(item.getDutyName())
                    .address(item.getDutyAddr())
                    .simpleAddress(item.getDutyMapimg())
                    .representativeContact(item.getDutyTel1())
                    .emergencyContact(item.getDutyTel3())
                    .ambulance(item.getHvamyn())
                    .ct(item.getHvctayn())
                    .mri(item.getHvmriayn())
                    .latitude(item.getWgs84Lat())
                    .longitude(item.getWgs84Lon()).build();
            hospitals.add(hospital);
        }

        hospitalRepository.saveAll(hospitals);

        System.out.println("api1, api2 매핑 횟수: " + count); // 411
        long endTime = System.currentTimeMillis();
        System.out.println("callApi() 함수 소요 시간: " + (endTime - startTime) + "ms");
    }
}
