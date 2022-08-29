package com.hotelbeds.supplierintegrations.hackertest.detector;

import com.hotelbeds.supplierintegrations.hackertest.detector.tool.LoginAttemptsCollection;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class DetectorApplication {


	public static void main(String[] args) {

		//SpringApplication.run(DetectorApplication.class, args);

		ApplicationContext context = SpringApplication.run(DetectorApplication.class, args);

		LoginAttemptsCollection loginAttemptsCollection = context.getBean(LoginAttemptsCollection.class);

		loginAttemptsCollection.getFailedIPLoggingAttemptsList();

	}

}
