package com.example.Sprint3Consumer;


import com.example.Sprint3Consumer.model.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Sprint3ConsumerApplicationTests {

	@Autowired
	private UserRepository repo;



	@Before
	public

	@Test
	void contextLoads() {

		System.out.println(this.repo.findById(6));
	}

}
