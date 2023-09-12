package dev.odane.bagservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class BagServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BagServiceApplication.class, args);
	}

}
