package com.digital;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableFeignClients
@SpringBootApplication
@EnableCircuitBreaker
@ServletComponentScan
@EnableTransactionManagement
public class BapfopmPfpsmasZcmsServiceApplication {
	public static void main(String[] args) {

		SpringApplication.run(BapfopmPfpsmasZcmsServiceApplication.class, args);
	}
}
