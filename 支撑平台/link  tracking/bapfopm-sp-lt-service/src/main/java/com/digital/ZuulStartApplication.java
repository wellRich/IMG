package com.digital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

import zipkin.server.EnableZipkinServer; 



@EnableZipkinServer  
@SpringBootApplication
public class ZuulStartApplication{   
	public static void main(String[] args) throws Exception {  
		SpringApplication.run(ZuulStartApplication.class, args);
	}   
}
