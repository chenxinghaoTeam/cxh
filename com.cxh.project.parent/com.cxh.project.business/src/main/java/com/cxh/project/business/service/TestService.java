package com.cxh.project.business.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/testClient")
public class TestService {
	public static final Logger log = LogManager.getLogger(TestService.class);
	
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	
	@RequestMapping("/getResult")
	public List<Map<String, Object>> getResult(@RequestBody Map<String, Object> params){
		List<Map<String, Object>> result = new ArrayList<>();
		Map<String, Object> param = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from Persons t ");
		sql.append("where 1 = 1 ");
		try {
			result = jdbcTemplate.queryForList(sql.toString(), param);
		} catch (Exception e) {
			log.error("查询失败!");
		}
		return result;
	}
}
 