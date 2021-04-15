package com.cxh.project.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cxh.project.core.TestClient;

@RestController
@RequestMapping("/controller")
public class TestController {
	public static final Logger log = LogManager.getLogger(TestController.class);
	@Autowired
	TestClient service;
	
//	@Value("${driverClassName}")
//	private String driverClassName;
	
	
	@RequestMapping("/initPage")
	public List<Map<String, Object>> initPage() {
//		System.out.println(driverClassName);
		return service.getResult(new HashMap<>());
	}
}
 