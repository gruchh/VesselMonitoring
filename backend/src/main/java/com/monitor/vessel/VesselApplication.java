package com.monitor.vessel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VesselApplication {

	public static void main(String[] args) {
		SpringApplication.run(VesselApplication.class, args);
	}

}
