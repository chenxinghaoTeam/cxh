package com.cxh.project.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cxh.common.utils.QueryParam;
import com.cxh.project.core.akClient;

@RestController
@RequestMapping("/akController")
public class akController {
	public static final Logger log = LogManager.getLogger(akController.class);
	@Autowired
	akClient service;
	
	// 查询列表
	@SuppressWarnings("unchecked")
	@RequestMapping("/initPage")
	public Map<String, Object> initPage(HttpServletRequest request,
			@RequestParam Map<String, Object> jsonParam, int pageIndex, int pageSize) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		QueryParam queryParam = new QueryParam();
		queryParam.setSkip(pageIndex);
		queryParam.setTake(pageSize);
		queryParam.setParams(jsonParam);
		List<Map<String, Object>> result = service.getGridResult(queryParam);
		Map<String, Object> count  =service.getGridResultCount(queryParam);
		resultMap.put("data", result);
		resultMap.put("total", count.get("COUNT"));
		return resultMap;
	}
}
