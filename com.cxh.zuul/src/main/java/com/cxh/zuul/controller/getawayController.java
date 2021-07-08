package com.cxh.zuul.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RefreshScope
public class getawayController {

	
	@Value("${spring.application.name}")
	private String name;
	
	
	@RequestMapping("/getaway")
	public String getway() {
		return name;
	}
}
 