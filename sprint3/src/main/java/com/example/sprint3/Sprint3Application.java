package com.example.sprint3;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

@SpringBootApplication(exclude = WebSocketServletAutoConfiguration.class)
@EnableJms
public class Sprint3Application {

	private static JmsTemplate jmsTemplate;
	public static void main(String[] args) throws JsonProcessingException {
		SpringApplication.run(Sprint3Application.class, args);
	}

}
