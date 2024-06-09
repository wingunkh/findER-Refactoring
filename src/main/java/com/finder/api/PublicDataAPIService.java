package com.finder.api;

import com.finder.domain.Bed;
import com.finder.domain.ER;
import com.finder.repository.BedRepository;
import com.finder.repository.ERRepository;
import com.finder.xml.*;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.io.StringReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class PublicDataAPIService extends APIService {
    @Value("${api.key}")
    private String key;
    private final Logger logger = LoggerFactory.getLogger(PublicDataAPIService.class);

    private final BedRepository bedRepository;
    private final ERRepository erRepository;

    // 응급의료기관 실시간 병상 수 갱신 (1분 간격)
    @Scheduled(cron = "1 * * * * *") // 스케줄링 (매 분 1초)
    public void updateBedCountEveryMinute() {
        long startTime = System.currentTimeMillis();
        LocalDateTime localDateTime = LocalDateTime.now();
        String time = localDateTime.format(DateTimeFormatter.ofPattern("yy/MM/dd HH:mm"));

        List<XmlModel1.Item1> item1List = getBedCount();
        List<Bed> bedList = new ArrayList<>();

        for (XmlModel1.Item1 item1 : item1List) {
            Bed bed = Bed.builder()
                    .hpID(item1.getHpid())
                    .time(time)
                    .count(item1.getHvec())
                    .build();

            bedList.add(bed);
        }

        bedRepository.saveAll(bedList);
        logger.info(time + " updateBedsCountEveryMinute() 함수 소요 시간: {}ms", (System.currentTimeMillis() - startTime));
    }

    // 응급의료기관 기본정보 갱신 (최초 실행 시)
    public void updateEmergencyRoomInfo() {
        long startTime = System.currentTimeMillis();
        LocalDateTime localDateTime = LocalDateTime.now();
        String time = localDateTime.format(DateTimeFormatter.ofPattern("yy/MM/dd HH:mm"));

        String urlString = "https://apis.data.go.kr/B552657/ErmctInfoInqireService/getEgytBassInfoInqire" +
                "?" + URLEncoder.encode("serviceKey", StandardCharsets.UTF_8) + "=" + key;

        List<XmlModel1.Item1> item1List = getBedCount();

        for (XmlModel1.Item1 item1 : item1List) {
            // 응급의료기관 기본정보 조회 API 호출
            String xmlData = sendHttpRequest(urlString + "&" + URLEncoder.encode("HPID", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(item1.getHpid(), StandardCharsets.UTF_8), null);

            // XML 데이터를 Java 객체로 변환 (언마샬링)
            StringReader stringReader = new StringReader(xmlData);
            XmlModel2 xmlModel2;

            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(XmlModel2.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                xmlModel2 = (XmlModel2) unmarshaller.unmarshal(stringReader);
            } catch (JAXBException e) {
                logger.error("JAXB Error", e);

                throw new RuntimeException(e.toString(), e);
            }

            List<XmlModel2.Item2> item2List = xmlModel2.getBody().getItems().getItem2();

            ER er = ER.builder()
                    .hpID(item1.getHpid())
                    .name(item2List.get(0).getDutyName())
                    .address(item2List.get(0).getDutyAddr())
                    .mapAddress(item2List.get(0).getDutyMapimg())
                    .tel(item2List.get(0).getDutyTel1())
                    .ERTel(item2List.get(0).getDutyTel3())
                    .ambulance(item1.getHvamyn())
                    .CT(item1.getHvctayn())
                    .MRI(item1.getHvmriayn())
                    .latitude(item2List.get(0).getWgs84Lat())
                    .longitude(item2List.get(0).getWgs84Lon())
                    .subject(item2List.get(0).getDgidIdName())
                    .build();

            erRepository.save(er);
        }

        logger.info(time + " updateEmergencyRoomInfo() 함수 소요 시간: {}ms", (System.currentTimeMillis() - startTime));
    }

    // 응급실 실시간 가용병상정보 조회 API 호출
    private List<XmlModel1.Item1> getBedCount() {
        String urlString = "https://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire" +
                "?" + URLEncoder.encode("serviceKey", StandardCharsets.UTF_8) + "=" + key +
                "&" + URLEncoder.encode("STAGE1", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("", StandardCharsets.UTF_8) + // 주소(시도)
                "&" + URLEncoder.encode("STAGE2", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("", StandardCharsets.UTF_8) + // 주소(시군구)
                "&" + URLEncoder.encode("pageNo", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("1", StandardCharsets.UTF_8) + // 페이지 번호
                "&" + URLEncoder.encode("numOfRows", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("410", StandardCharsets.UTF_8); // 목록 건수

        String data = sendHttpRequest(urlString, null);

        // XML 데이터를 Java 객체로 변환 (언마샬링)
        StringReader stringReader = new StringReader(data);
        XmlModel1 xmlModel1;

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(XmlModel1.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            xmlModel1 = (XmlModel1) unmarshaller.unmarshal(stringReader);
        } catch (JAXBException e) {
            logger.error("JAXB Error", e);

            throw new RuntimeException(e.toString(), e);
        }

        List<XmlModel1.Item1> item1List = xmlModel1.getBody().getItems().getItem1();
        logger.info("Data 수 : {}", item1List.size());

        return item1List;
    }
}
