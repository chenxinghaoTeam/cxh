package com.cxh.project.business.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/uploadClient")
public class UploadService {
	public static final Logger log = LogManager.getLogger(UploadService.class);

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	/**
	 * 查询文件信息
	 * @param params
	 * @return
	 */
	@RequestMapping("/getFileResult")
	public List<Map<String, Object>> getFileResult(@RequestBody Map<String, Object> params) {
		List<Map<String, Object>> result = new ArrayList<>();
		Map<String, Object> param = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from fileinfor t ");
		sql.append("where 1 = 1 ");
		try {
			result = jdbcTemplate.queryForList(sql.toString(), param);
		} catch (Exception e) {
			log.error("查询失败!");
		}
		return result;
	}
	
	/**
	 * 插入文件信息
	 * @param params
	 * @return
	 */
	@RequestMapping("/insertFile")
	public int insertFile(@RequestBody Map<String, Object> params) {
		Map<String, Object> param = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		int result = 0;
		sql.append("insert into fileinfor(id,title,imageName,fileName,uploadDate)");
		sql.append("values (:id,:title,:imageName,:fileName,:uploadDate)");
		param.put("id", UUID.randomUUID().toString());
		param.put("title", params.get("title"));
		param.put("imageName", params.get("imageName"));
		param.put("fileName", params.get("fileName"));
		param.put("uploadDate", new Date());
		try {
			result = jdbcTemplate.update(sql.toString(), param);
		} catch (Exception e) {
			log.error("插入失败!");
		}
		 return result;
	}
	
	/**
	 * 删除文件信息
	 * @param params
	 * @return
	 */
	@RequestMapping("/deleteFile")
	public int deleteFile(@RequestBody Map<String, Object> params) {
		Map<String, Object> param = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		int result = 0;
		sql.append("delete from fileinfor where id=:ids");
		param.put("ids", params.get("id"));
		try {
			result = jdbcTemplate.update(sql.toString(), param);
		} catch (Exception e) {
			log.error("删除失败!");
		}
		 return result;
	}
	
}
