package com.cxh.common.utils;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 参数封装
 * @author chenxinghao
 * 2022年1月21日-下午1:59:45
 */
public class QueryParam {
	
	@JsonInclude
	private int skip;
	@JsonInclude
	private int take;
	@JsonInclude
	private Map<String, Object> params;
	/**
	 * @return the skip
	 */
	public int getSkip() {
		return skip;
	}
	/**
	 * @param skip the skip to set
	 */
	public void setSkip(int skip) {
		this.skip = skip;
	}
	/**
	 * @return the take
	 */
	public int getTake() {
		return take;
	}
	/**
	 * @param take the take to set
	 */
	public void setTake(int take) {
		this.take = take;
	}
	/**
	 * @return the params
	 */
	public Map<String, Object> getParams() {
		return params;
	}
	/**
	 * @param params the params to set
	 */
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
}
 