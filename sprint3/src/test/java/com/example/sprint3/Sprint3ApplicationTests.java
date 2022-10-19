package com.example.sprint3;

import com.example.sprint3.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Sprint3Application.class)
@WebAppConfiguration
public class Sprint3ApplicationTests {

	private static Logger LOGGER  =  LoggerFactory.getLogger(Sprint3ApplicationTests.class);



	@Autowired
	private WebApplicationContext webApplicationContext;

	MockMvc mvc;


	@Before
	public void setup(){
		LOGGER.info("start testing");
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void getTest() throws Exception {
		String url = "/send/6";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(url)).andReturn();
		int status = result.getResponse().getStatus();
		System.out.println(status);
		Assert.assertEquals("wrong",200,status);
	}

	@Test
	public void postTest() throws Exception {
		String url = "/send/create";
		mvc.perform(MockMvcRequestBuilders.post(url)
				.content(asJsonString(new User(24, "firstName4", "lastName4", "email4@mail.com")))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
		).andExpect(status().is2xxSuccessful());
	}

	public static String asJsonString(final Object obj){
		try{
			return new ObjectMapper().writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}




}
