package com.cxh.project.business.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
		sql.append(" SELECT * FROM ( ");
		sql.append(" SELECT A.*, rownum r FROM ( ");
		sql.append(" SELECT * FROM rywbxx t");
		sql.append(" where 1 =1 ");
		if(!StringUtils.isEmpty(map.get("zg").toString())) {
			sql.append(" and t.zg =:zgs ");
			param.put("zgs", map.get("zg").toString());
		}
		if(!StringUtils.isEmpty(map.get("zt").toString())) {
			sql.append(" and t.zt =:zts ");
			param.put("zts", map.get("zt").toString());
		}
		if(!StringUtils.isEmpty(map.get("xl").toString())) {
			sql.append(" and t.xl =:xls ");
			param.put("xls", map.get("xl").toString());
		}
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
	public Map<String, Object> getGridResultCount(@RequestBody QueryParam queryParam) {
		Map<String, Object> map = queryParam.getParams();
		Map<String, Object> param = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(t.id) as count from rywbxx t ");
		sql.append(" where 1 = 1  ");
		if(!StringUtils.isEmpty(map.get("zg").toString())) {
			sql.append(" and t.zg =:zgs ");
			param.put("zgs", map.get("zg").toString());
		}
		if(!StringUtils.isEmpty(map.get("zt").toString())) {
			sql.append(" and t.zt =:zts ");
			param.put("zts", map.get("zt").toString());
		}
		if(!StringUtils.isEmpty(map.get("xl").toString())) {
			sql.append(" and t.xl =:xls ");
			param.put("xls", map.get("xl").toString());
		}
		try {
			result = jdbcTemplate.queryForMap(sql.toString(), param);
		} catch (Exception e) {
			log.error("查询失败!");
		}
		return result;
	}
	
	/**
	 * 下拉框
	 * @param queryParam
	 * @return
	 */
	@RequestMapping("/getSelectResult")
	public List<Map<String, Object>> getSelectResult(@RequestBody QueryParam queryParam) {
		Map<String, Object> map = queryParam.getParams();
		Map<String, Object> param = new HashMap<>();
		List<Map<String, Object>> result = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		if (!StringUtils.isEmpty(String.valueOf(map.get("type")))) {
			sql.append(" select distinct "+String.valueOf(map.get("type"))+" as id from rywbxx ");
		}
		try {
			result = jdbcTemplate.queryForList(sql.toString(), param);
		} catch (Exception e) {
			log.error("查询失败!");
		}
		return result;
	}

}
