package com.hotelbeds.supplierintegrations.hackertest.detector;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DetectorApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void mainAppClassTest(){
		DetectorApplication.main(new String[]{});
	}

}
