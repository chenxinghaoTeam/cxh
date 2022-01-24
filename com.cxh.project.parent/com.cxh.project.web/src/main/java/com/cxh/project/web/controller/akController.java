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

import com.alibaba.fastjson.JSON;
import com.cxh.common.utils.QueryParam;
import com.cxh.common.utils.commonUtils;
import com.cxh.project.core.akClient;
import com.cxh.project.web.viewmodel.TestViewMode;

@RestController
@RequestMapping("/akController")
public class akController {
	public static final Logger log = LogManager.getLogger(akController.class);
	@Autowired
	akClient service;

	// 查询列表
	@SuppressWarnings("unchecked")
	@RequestMapping("/initPage")
	public Map<String, Object> initPage(HttpServletRequest request, @RequestParam Map<String, Object> jsonParam,
			int pageIndex, int pageSize) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		QueryParam queryParam = new QueryParam();
		queryParam.setSkip(pageIndex);
		queryParam.setTake(pageSize);
		queryParam.setParams(jsonParam);
		List<Map<String, Object>> result = service.getGridResult(queryParam);
		Map<String, Object> count = service.getGridResultCount(queryParam);
		resultMap.put("data", result);
		resultMap.put("total", count.get("COUNT"));
		return resultMap;
	}

	@RequestMapping("/initSelect")
	public TestViewMode initSelect(String jsonParam) {
		TestViewMode vm = new TestViewMode();
		Map<String, Object> map = (Map) JSON.parse(jsonParam);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		QueryParam param = new QueryParam();
		// 主管
		map.put("type", "zg");
		param.setParams(map);
		List<Map<String, Object>> zgData = zgFilter(param);
		// 状态
		map.put("type", "zt");
		param.setParams(map);
		List<Map<String, Object>> ztData = zgFilter(param);
		// 学历
		map.put("type", "xl");
		param.setParams(map);
		List<Map<String, Object>> xlData = zgFilter(param);
		resultMap.put("zg", zgData);
		resultMap.put("zt", ztData);
		resultMap.put("xl", xlData);
		vm.setFilterData(resultMap);
		vm.setSuccessful(true);
		return vm;
	}

	private List<Map<String, Object>> zgFilter(QueryParam param) {
		List<Map<String, Object>> resultList = service.getSelectResult(param);
		resultList.forEach(a -> {
			a.put("id", a.get("ID"));
			a.put("text", a.get("ID"));
		});
		return commonUtils.filterIsNotNulll(resultList, "id");
	}
}
