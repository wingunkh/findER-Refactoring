package com.finder;

import com.finder.api.PublicDataAPIService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
