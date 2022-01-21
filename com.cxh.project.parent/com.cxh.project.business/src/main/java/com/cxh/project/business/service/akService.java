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

import com.cxh.common.utils.QueryParam;

@RestController
@RequestMapping("/akClient")
public class akService {
	public static final Logger log = LogManager.getLogger(akService.class);

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@RequestMapping("/getGridResult")
	public List<Map<String, Object>> getGridResult(@RequestBody QueryParam queryParam) {
		Map<String, Object> map = queryParam.getParams();
		Map<String, Object> param = new HashMap<>();
		int take = queryParam.getTake();
		int skip = queryParam.getSkip();
		List<Map<String, Object>> result = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
//		sql.append(" SELECT * ");
//		sql.append(" FROM (Select ROWNUM AS ROWNO, T.* ");
//		sql.append(" from cxh.persons T ");
//		sql.append(" where 1 = 1  ");
//		sql.append(" AND ROWNUM <= :takes) TABLE_ALIAS ");
//		sql.append(" WHERE TABLE_ALIAS.ROWNO > :skips ");
		
		sql.append(" SELECT * FROM ( ");
		sql.append(" SELECT A.*, rownum r FROM ( ");
		sql.append(" SELECT * FROM rywbxx ");
		sql.append(" ) A WHERE rownum <= :takes  ");
		sql.append(" ) B WHERE r > :skips ");
		param.put("takes", take);
		param.put("skips", skip);
		try {
			result = jdbcTemplate.queryForList(sql.toString(), param);
		} catch (Exception e) {
			log.error("查询失败!");
		}
		return result;
	}
	
	
	@RequestMapping("/getGridResultCount")
	public Map<String, Object> getGridResultCount(@RequestBody QueryParam queryParam){
		Map<String, Object> map = queryParam.getParams();
		Map<String, Object> param = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(t.id) as count from rywbxx t ");
		sql.append(" where 1 = 1  ");
		try {
			result = jdbcTemplate.queryForMap(sql.toString(), param);
		} catch (Exception e) {
			log.error("查询失败!");
		}
		return result;
	}
	
}
