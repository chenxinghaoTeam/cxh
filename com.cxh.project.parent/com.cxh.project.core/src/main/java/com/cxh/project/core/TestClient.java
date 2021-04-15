package com.cxh.project.core;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "TestClient", url = "http://localhost:8082/")//business的端口
@RequestMapping("/testClient")
public interface TestClient {

	@RequestMapping("/getResult")
	public List<Map<String, Object>> getResult(@RequestBody Map<String, Object> params);
}
