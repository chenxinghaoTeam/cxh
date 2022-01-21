package com.cxh.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <p>
 * ClassName:ConvertUtil，
 * </p>
 * <p>
 * 描述:转换函数
 * </p>
 * 
 * @editor wangmg
 * @version 1.0
 */
public class ConvertUtils {

	/**
	 * 日志
	 */
	private static final Logger LOG = LogManager.getLogger(ConvertUtils.class);

	/**
	 * 
	 * <p>
	 * 描述：日期格式化（yyyyMMdd）
	 * </p>
	 * 
	 * @param date
	 *            参数
	 * @return 日期
	 * @editor:周安东 2017年5月2日 上午11:01:26
	 */
	public static String formatDate(Date date) {
		return formatDate(date, "yyyyMMdd");
	}

	/**
	 * 
	 * <p>
	 * 描述：指定格式去格式化日期
	 * </p>
	 * 
	 * @param date
	 *            参数
	 * @param format
	 *            日期格式
	 * @return 日期
	 * @editor:周安东 2017年5月2日 上午11:01:26
	 */
	public static String formatDate(Date date, String format) {
		SimpleDateFormat formate = new SimpleDateFormat(format);
		return formate.format(date);
	}

	/**
	 * 
	 * <p>
	 * 描述：日期格式化（yyyyMMdd）
	 * </p>
	 * 
	 * @param date
	 *            参数
	 * @return 日期
	 * @editor:周安东 2017年5月2日 上午11:01:26
	 */
	public static Date formatDateObj(Object date) {
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = null;
		try {
			dt = formate.parse(cString(date));
		} catch (ParseException e) {
			LOG.error("日期转换失败", e);
		}
		return dt;
	}

	/**
	 * 
	 * <p>
	 * 描述：日期格式化（yyyy-MM-dd HH:mm:ss）
	 * </p>
	 * 
	 * @param date
	 *            参数
	 * @return 日期
	 * @editor:周安东 2017年5月2日 上午11:01:26
	 */
	public static String cFullDateToString(Date date) {
		if (date == null)
			return null;
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formate.format(date);
	}



	/**
	 * <p>
	 * 描述：转换字符串类型
	 * </p>
	 * 
	 * @param obj
	 *            目标Data
	 * @return 字符串类型Data
	 * @editor:wangmg 2017年4月12日 下午7:36:02
	 */
	public static String cString(Object obj) {

		if (obj == null) {
			return null;
		}
		return obj.toString();
	}

	/**
	 * <p>
	 * 描述：转换Double类型
	 * </p>
	 * 
	 * @param obj
	 *            目标Data
	 * @param defaultValue
	 *            默认值
	 * @return Double类型Data
	 * @editor:wangmg 2017年4月12日 下午7:36:02
	 */
	public static Double cDouble(Object obj, Double defaultValue) {

		if (obj == null) {
			return defaultValue;
		}
		if (!obj.toString().matches("^(-?\\d+)(\\.\\d+)?$")) {
			return defaultValue;
		}
		return Double.valueOf(obj.toString());
	}

	/**
	 * <p>
	 * 描述：转换Integer类型
	 * </p>
	 * 
	 * @param obj
	 *            目标Data
	 * @param defaultValue
	 *            默认值
	 * @return Integer类型Data
	 * @editor:wangmg 2017年4月12日 下午7:36:02
	 * @changer sxx 修正正则表达式 支持 (1.0,1.00..) 转int 类型
	 */
	public static Integer cInteger(Object obj, Integer defaultValue) {

		if (obj == null) {
			return defaultValue;
		}
		if (!obj.toString().matches("^(-?\\d+)(\\.[0]+)?$")) {
			return defaultValue;
		}
		return Double.valueOf(obj.toString()).intValue();
	}
	
	
}
