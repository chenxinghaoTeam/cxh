package com.cxh.project.core;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cxh.common.utils.QueryParam;

@FeignClient(name = "akClient", url = "http://localhost:8082/") // business的端口
@RequestMapping("/akClient")
public interface akClient {

	/**
	 * 详情
	 * @param queryParam
	 * @return
	 */
	@RequestMapping("/getGridResult")
	public List<Map<String, Object>> getGridResult(@RequestBody QueryParam queryParam);

	/**
	 * 详情个数
	 * @param queryParam
	 * @return
	 */
	@RequestMapping("/getGridResultCount")
	public Map<String, Object> getGridResultCount(@RequestBody QueryParam queryParam);

}
