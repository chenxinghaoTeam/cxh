package com.cxh.project.core;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "TestClient", url = "http://localhost:8082/") // business的端口
@RequestMapping("/testClient")
public interface TestClient {

	@RequestMapping("/getResult")
	public List<Map<String, Object>> getResult(@RequestBody Map<String, Object> params);

	// 更新查询
	@RequestMapping("/updateListData")
	public List<Map<String, Object>> updateListData(@RequestBody Map<String, Object> params);

	// 删除
	@RequestMapping("/deleteData")
	public int deleteData(@RequestBody Map<String, Object> params);

	// 新增
	@RequestMapping("/addData")
	public int addData(@RequestBody Map<String, Object> params);

	// 修改
	@RequestMapping("/uodateData")
	public int uodateData(@RequestBody Map<String, Object> params);

	// Excel导入
	@RequestMapping("/insertExcel")
	public int insertExcel(@RequestBody Map<String, Object> params);
}
