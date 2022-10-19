package com.example.FirstProject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
class FirstProjectApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void cahngeFormat(){
		String s = "2022-09-28";
		String s2 = "2022-09-29";
		LocalDate l= LocalDate.parse(s);
		LocalDateTime d1 = l.atStartOfDay();
		LocalDate l2 = LocalDate.parse(s2);
		LocalDateTime d3 = l2.atTime(23,59,59);

		System.out.println(d1);
		System.out.println(d3);
	}

	@Test
	void stodateformat() throws ParseException {

		StringBuilder s = new StringBuilder("20220928");
		System.out.println(s);
		s.insert(4,"-");
		System.out.println(s);
		s.insert(7,"-");
		System.out.println(s);

		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
//		Date d = f.parse(s);
		System.out.println(f);
 		String d = f.format(f.parse(s.toString()));
		System.out.println(d);
	}

}
