package com.finder;

import com.finder.domain.Bed;
import com.finder.repository.BedRepository;
import com.finder.xml.XmlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@SpringBootApplication
@Slf4j
@EnableJpaAuditing
public class FinderApplication {

	@Autowired
	XmlService xs;
	@Autowired
	BedRepository bedRepository;
	public static void main(String[] args) {
		SpringApplication.run(FinderApplication.class, args);
	}

//	@PostConstruct
//	public void init() {
//		// 응급실 실시간 가용병상정보 조회 API 요청
//		boolean success = false;
//		while (!success) {
//			try {
//				xs.callApi1();
//				success = true; // 호출이 성공하면 반복문을 종료
//			} catch (Exception e) {
//				log.error("API 재 요청");
//			}
//		}
//
//		System.out.println();
//
//		// 응급의료기관 기본정보 조회 API 요청
//		success = false;
//		while (!success) {
//			try {
//				xs.callApi2();
//				success = true; // 호출이 성공하면 반복문을 종료
//			} catch (Exception e) {
//				log.error("API 재 요청");
//			}
//		}
//
//		LocalDateTime now = LocalDateTime.now();
//		LocalDateTime fourHoursAgo = now.minusHours(4);
//		System.out.println(now.getHour() + " : " + now.getMinute());
//
////		for (LocalDateTime time = fourHoursAgo; time.isBefore(now); time = time.plusMinutes(1)) {
////			String hospitalName = "연세대학교의과대학강남세브란스병원";
////			LocalDateTime dataTime = time;
////			String t;
////			if(time.getMinute() < 10) t = dataTime.getHour() + ":0" + dataTime.getMinute();
////			else t = dataTime.getHour() + ":" + dataTime.getMinute();
////			bedRepository.save(new Bed(hospitalName, t, dataTime, 1));
//	}
}
