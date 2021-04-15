package org.com.cxh.project.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients({"com.cxh"})
public class webApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(webApplication.class, args);
    }
}
