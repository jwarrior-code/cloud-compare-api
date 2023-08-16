package com.cloudcompare.msproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication//(exclude = {DataSourceAutoConfiguration.class })
public class MsProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsProjectApplication.class, args);
	}

}
