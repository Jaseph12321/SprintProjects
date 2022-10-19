package com.example.Sprint3Consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@SpringBootApplication
public class Sprint3ConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Sprint3ConsumerApplication.class, args);
	}

}
