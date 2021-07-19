package com.cxh.project.core;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 上传接口
 * @author chenxinghao
 * 2021年7月14日-下午3:12:13
 */
@FeignClient(name = "uploadClient", url = "http://localhost:8082/") // business的端口
@RequestMapping("/uploadClient")
public interface UploadClient {

	
	/**
	 * 查询文件信息
	 * @param params
	 * @return
	 */
	@RequestMapping("/getFileResult")
	public List<Map<String, Object>> getFileResult(@RequestBody Map<String, Object> params);

	
	
	/**
	 * 插入文件信息
	 * @param params
	 * @return
	 */
	@RequestMapping("/insertFile")
	public int insertFile(@RequestBody Map<String, Object> params);
	
	
	/**
	 * 删除文件信息
	 * @param params
	 * @return
	 */
	@RequestMapping("/deleteFile")
	public int deleteFile(@RequestBody Map<String, Object> params);

}
