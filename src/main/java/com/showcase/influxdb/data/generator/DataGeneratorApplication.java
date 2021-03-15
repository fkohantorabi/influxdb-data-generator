package com.showcase.influxdb.data.generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.showcase.influxdb.data.generator")
public class DataGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataGeneratorApplication.class, args);
	}

}
