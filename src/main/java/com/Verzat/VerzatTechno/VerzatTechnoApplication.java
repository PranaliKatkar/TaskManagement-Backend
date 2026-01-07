package com.Verzat.VerzatTechno;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class VerzatTechnoApplication {

	public static void main(String[] args) {
		SpringApplication.run(VerzatTechnoApplication.class, args);
	}

}
