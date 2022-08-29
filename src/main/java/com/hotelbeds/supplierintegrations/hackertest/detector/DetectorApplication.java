package com.hotelbeds.supplierintegrations.hackertest.detector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DetectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DetectorApplication.class, args);

		/*
		//SpringApplication.run(DetectorApplication.class, args);

		ApplicationContext context = SpringApplication.run(DetectorApplication.class, args);

		LoginAttemptsCollection loginAttemptsCollection = context.getBean(LoginAttemptsCollection.class);

		loginAttemptsCollection.getFailedIPLoggingAttemptsList();
		 */
	}

}
