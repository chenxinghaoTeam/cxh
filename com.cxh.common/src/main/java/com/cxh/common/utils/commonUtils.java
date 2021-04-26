package com.cxh.common.utils;

import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class commonUtils {
	public static final Logger log = LogManager.getLogger(commonUtils.class);
	
	
	/**
	 * 当前年份
	 */
	
	public static int currenYear = Calendar.getInstance().get(Calendar.YEAR);
}
 