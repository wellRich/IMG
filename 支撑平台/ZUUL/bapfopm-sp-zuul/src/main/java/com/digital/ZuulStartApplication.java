package com.digital;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
 


@EnableZuulProxy
@EnableEurekaClient
@SpringCloudApplication
public class ZuulStartApplication{   
	public static void main(String[] args) throws Exception {  
		SpringApplication.run(ZuulStartApplication.class, args);
	}   
}
