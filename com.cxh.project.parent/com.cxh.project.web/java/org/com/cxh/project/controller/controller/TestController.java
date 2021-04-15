package org.com.cxh.project.controller.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.com.cxh.project.core.client.TestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testController")
public class TestController {
	public static final Logger log = LogManager.getLogger(TestController.class);
	@Autowired
	TestClient service;
	
	@RequestMapping("/test")
	public List<Map<String, Object>> test() {
		Map<String, Object> param = new HashMap<>();
		List<Map<String, Object>> result = service.getResult(param);
		return result;
	}
}
 