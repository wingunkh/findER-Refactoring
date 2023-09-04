package com.finder;

import com.finder.xml.XmlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;

@SpringBootApplication
@Slf4j
@EnableJpaAuditing
public class FinderApplication {

	@Autowired
	XmlService xs;

	public static void main(String[] args) {
		SpringApplication.run(FinderApplication.class, args);
	}

	@PostConstruct
	public void init() {
		// 응급실 실시간 가용병상정보 조회 API 요청
		boolean success = false;
		while (!success) {
			try {
				xs.callApi1();
				success = true; // 호출이 성공하면 반복문을 종료
			} catch (Exception e) {
				log.error("API 재 요청");
			}
		}

		System.out.println();

		// 응급의료기관 기본정보 조회 API 요청
		success = false;
		while (!success) {
			try {
				xs.callApi2();
				success = true; // 호출이 성공하면 반복문을 종료
			} catch (Exception e) {
				log.error("API 재 요청");
			}
		}
	}
}
