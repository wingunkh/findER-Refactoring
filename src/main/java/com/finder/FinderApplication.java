package com.finder;

import com.finder.api.PublicDataAPIService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.annotation.PostConstruct;

@SpringBootApplication
@RequiredArgsConstructor
public class FinderApplication {
	private final PublicDataAPIService publicDataAPIService;

	public static void main(String[] args) {
		SpringApplication.run(FinderApplication.class, args);
	}

	@PostConstruct
	public void init() {
		publicDataAPIService.updateEmergencyRoomInfo();
	}
}
