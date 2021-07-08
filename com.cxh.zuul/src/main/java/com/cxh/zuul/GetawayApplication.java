package com.cxh.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 路由启动
 *
 */
@EnableDiscoveryClient
@SpringBootApplication
public class GetawayApplication {
	public static void main(String[] args) {
		SpringApplication.run(GetawayApplication.class, args);
		System.out.println("getaway路由正在启动。。。。。。");
	}
}
