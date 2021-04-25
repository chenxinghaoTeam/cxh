package com.cxh.project.web.controller;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.cxh.project.core.TestClient;
import com.cxh.project.web.viewmodel.TestViewMode;

@RestController
@RequestMapping("/controller")
public class TestController {
	public static final Logger log = LogManager.getLogger(TestController.class);
	@Autowired
	TestClient service;

	// 查询列表
	@RequestMapping("/initPage")
	public TestViewMode initPage(String jsonParm) {
		TestViewMode vm = new TestViewMode();
		Map map = (Map) JSON.parse(jsonParm);
		List<Map<String, Object>> result = service.getResult(map);
		int total = result.size();
		vm.setRows(result);
		vm.setTotal(total);
		vm.setSuccessful(true);
		return vm;
	}
}
