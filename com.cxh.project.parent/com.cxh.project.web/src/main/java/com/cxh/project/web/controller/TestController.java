package com.cxh.project.web.controller;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.cxh.common.utils.commonUtils;
import com.cxh.project.core.TestClient;
import com.cxh.project.web.viewmodel.TestViewMode;

@RestController
@RequestMapping("/controller")
@RefreshScope //动态刷新
public class TestController {
	public static final Logger log = LogManager.getLogger(TestController.class);
	@Autowired
	TestClient service;

	  @Value("${server.port}")
	  private String serverPort;

	
	/**
	 * 从nacos中心化配置文件读取
	 * @return
	 */
	@RequestMapping("/get_nacos")
	public String getNacos() {
		return serverPort;
	}

	// 查询列表
	@RequestMapping("/initPage")
	public TestViewMode initPage(String jsonParm) {
		TestViewMode vm = new TestViewMode();
		Map map = (Map) JSON.parse(jsonParm);
		System.out.println(commonUtils.currenYear);
		List<Map<String, Object>> result = service.getResult(map);
		int total = result.size();
		vm.setRows(result);
		vm.setTotal(total);
		vm.setSuccessful(true);
		return vm;
	}
}
