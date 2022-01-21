package com.cxh.common.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;


public class commonUtils {



	/**
	 * 近八年集合倒序
	 */
	public static final List<String> YEARLIST = new ArrayList<>();

	/**
	 * 近五年集合顺序
	 */
	public static final List<String> YEARORDERLIST = new ArrayList<>();

	/**
	 * 当前月份
	 */
	public static int currentMonth = (Calendar.getInstance().get(Calendar.MONTH) + 1);

	/**
	 * 当前年份
	 */
	public static int currenYear = Calendar.getInstance().get(Calendar.YEAR);

	/**
	 * 设备16次设备
	 */
	public static final List<String> DEVICETYPE = new ArrayList<>();
	static {
		// 添加近五年集合倒序
		for (int i = 0; i < 5; i++) {
			YEARLIST.add((currenYear - i) + "");
		}

		// 添加近五年集合顺序
		for (int i = 4; i >= 0; i--) {
			YEARORDERLIST.add((currenYear - i) + "");
		}
	}

	/**
	 * <p>
	 * 描述：排序并补全
	 * </p>
	 * 
	 * @param result
	 * 需要排序的集合
	 * @param codeKy
	 * chart展示中需要的code
	 * @param nameKy
	 * chart展示中需要的name
	 * @param year
	 * 年份
	 * @param valueKy
	 * chart展示中需要的name
	 * @return list
	 * @editor:安东磊 2018年7月10日 下午2:12:59
	 * 
	 */

	public static List<Map<String, Object>> monthOrder(List<Map<String, Object>> result, String codeKy, String nameKy,
			String valueKy, int year) {
		List<Integer> MONTHLIST = new ArrayList<>();
		// 添加近12月集合
		if (year == currenYear) {
			for (int i = 1; i <= currentMonth; i++) {
				MONTHLIST.add(i);
			}
		} else {
			for (int i = 1; i <= 12; i++) {
				MONTHLIST.add(i);
			}
		}

		// list去重复合并
		if (!StringUtils.isNoneEmpty(valueKy)) {
			valueKy = "value";
		}

		List<Map<String, Object>> infoList = new ArrayList<>();
		Map<String, Object> data = null;
		for (int month : MONTHLIST) {
			data = new HashMap<>();
			data.put(codeKy, month);
			// data.put(valueKy, 0);
			data.put(nameKy, month);
			data.put("0", 0);
			data.put("1", 0);
			for (Map<String, Object> map : result) {
				if (month == transformDoubleToInteger(map.get(codeKy))) {
					map.put(nameKy, month);
					data = map;
				}
			}
			infoList.add(data);
		}
		return infoList;
	}

	/**
	 * <p>
	 * 描述：排序并补全
	 * </p>
	 * 
	 * @param result
	 * 需要排序的集合
	 * @param codeKy
	 * chart展示中需要的code
	 * @param nameKy
	 * chart展示中需要的name
	 * @param valueKy
	 * chart展示中需要的name
	 * @return list
	 * @editor:安东磊 2018年7月10日 下午2:12:59
	 * 
	 */
	public static List<Map<String, Object>> yearOrder(List<Map<String, Object>> result, String codeKy, String nameKy,
			String valueKy) {
		// list去重复合并
		if (!StringUtils.isNoneEmpty(valueKy)) {
			valueKy = "value";
		}
		result = MakeAllList(result, codeKy, valueKy);
		List<Map<String, Object>> infoList = new ArrayList<>();
		Map<String, Object> data = null;
		for (String year : YEARORDERLIST) {
			data = new HashMap<>();
			data.put(codeKy, year);
			data.put(valueKy, 0);
			data.put(nameKy, year);
			for (Map<String, Object> map : result) {
				if (map.containsValue(year)) {
					map.put(nameKy, year);
					data = map;
				}
			}
			infoList.add(data);
		}
		return infoList;
	}

	/**
	 * <p>
	 * 描述：排序并补全
	 * </p>
	 * 
	 * @param result
	 * 需要排序的集合
	 * @param codeKy
	 * chart展示中需要的code
	 * @param nameKy
	 * chart展示中需要的name
	 * @param valueKy
	 * chart展示中需要的name
	 * @return list
	 * @editor:安东磊 2018年7月10日 下午2:12:59
	 * 
	 */
	public static List<Map<String, Object>> yearFlashback(List<Map<String, Object>> result, String codeKy,
			String nameKy, String valueKy) {
		// list去重复合并
		if (!StringUtils.isNoneEmpty(valueKy)) {
			valueKy = "value";
		}
		result = MakeAllList(result, codeKy, valueKy);
		List<Map<String, Object>> infoList = new ArrayList<>();
		Map<String, Object> data = null;
		for (String year : YEARLIST) {
			data = new HashMap<>();
			data.put(codeKy, year);
			data.put(valueKy, 0);
			data.put(nameKy, year);
			for (Map<String, Object> map : result) {
				if (map.containsValue(year)) {
					map.put(nameKy, year);
					data = map;
				}
			}
			infoList.add(data);
		}
		return infoList;
	}


	/**
	 * <p>
	 * 描述：根据返回的结果自己进行分组合并，当valueKy不为默认value时使用
	 * </p>
	 * 
	 * @param list
	 * 参数
	 * @return list
	 * @editor:安东磊 2018年7月7日 上午11:36:41
	 * @param groupName
	 * 参数
	 * @param valueKy
	 * 参数
	 */
	public static List<Map<String, Object>> MakeAllList(List<Map<String, Object>> list, String groupName,
			String valueKy) {
		List<Map<String, Object>> resultReturn = new ArrayList<>();
		list.stream()
				.collect(Collectors.groupingBy(p -> p.get(groupName),
						Collectors.summingInt(p -> transformDoubleToInteger(p.get(valueKy)))))
				.forEach((Ky, value) -> {
					Map<String, Object> map = new HashMap<>();
					map.put(groupName, Ky);
					map.put(valueKy, value);
					resultReturn.add(map);
				});
		return resultReturn;
	}

	/**
	 * <p>
	 * 描述：字符串比较
	 * </p>
	 * 
	 * <pre>
	 * 空字符串与null认为一致
	 * </pre>
	 * 
	 * @param strOrg
	 * 比较源
	 * @param strTar
	 * 比较目标
	 * @return 比较结果
	 * @editor sunxq 2018年3月23日 下午2:52:33
	 */
	public static boolean equalsIgnoreEmpty(String strOrg, String strTar) {
		if (StringUtils.isEmpty(strOrg)) {
			return StringUtils.isEmpty(strTar);
		} else {
			return strOrg.equals(strTar);
		}
	}

	/**
	 * <p>
	 * 描述：字符串与对象值比较
	 * </p>
	 * 
	 * <pre>
	 * 空字符串与null认为一致
	 * </pre>
	 * 
	 * @param strOrg
	 * 比较源
	 * @param target
	 * 比较目标
	 * @return 比较结果
	 * @editor sunxq 2018年3月23日 下午2:52:33
	 */
	public static boolean equalsStrWithObj(String strOrg, Object target) {
		if (StringUtils.isEmpty(strOrg)) {
			return target == null || target.toString().length() == 0;
		} else {
			return strOrg.equals(String.valueOf(target));
		}
	}

	/**
	 * <p>
	 * 描述：数值转换
	 * </p>
	 * 
	 * @param num
	 * object对象
	 * @return Integer数字
	 * @editor:李传兵 2018年3月23日 下午2:52:33
	 */
	public static String cString(Object num) {
		if (num == null) {
			return "";
		} else {
			return num.toString();
		}
	}

	/**
	 * <p>
	 * 描述：数据库字段转成String
	 * </p>
	 * 
	 * @param object
	 * 数据库字段值
	 * @return 文字
	 * @editor:李传兵 2018年3月23日 下午2:52:33
	 */
	public static String parseObjectToString(Object object) {

		if (object == null) {
			return "";
		}

		if (object instanceof Integer) {
			return Integer.toString((Integer) object);
		} else if (object instanceof Double) {
			return Double.toString(((Double) object).doubleValue()); // 运检指标总览二级展示小数点。现改为double
		} else {
			return String.valueOf(object);
		}
	}

	
	/**
	 * 
	 * <p>
	 * 描述：对象的特殊字符转换
	 * </p>
	 * 
	 * @param str
	 * 参数
	 * @return true or false
	 * @editor:陈高云 2018年3月28日 上午10:26:13
	 */
	public static boolean isJsonObjectString(String str) {
		return (str != null) && (str.matches("^\\{.*\\}$"));
	}

	/**
	 * 
	 * <p>
	 * 描述：Array特殊字符转换
	 * </p>
	 * 
	 * @param str
	 * 参数
	 * @return true or false
	 * @editor:陈高云 2018年3月28日 上午10:27:33
	 */
	public static boolean isJsonArrayString(String str) {
		return (str != null) && (str.matches("^\\[.*\\]$"));
	}





	/**
	 * <p>
	 * 描述：饼图或者圆环图超过五个就是5+1
	 * </p>
	 * 
	 * @param codeKy
	 * 参数
	 * @param nameKy
	 * 参数
	 * @param valueKy
	 * 参数
	 * @return List
	 * @editor:王跃 2018年7月3日 下午6:56:54
	 * @param infoList
	 * 参数
	 */
	public static List<Map<String, Object>> displayModelWithOther(String codeKy, String nameKy, String valueKy,
			List<Map<String, Object>> infoList) {
		infoList = infoList.stream().sorted((p, m) -> Integer.valueOf(String.valueOf(m.get(valueKy)))
				- Integer.valueOf(String.valueOf(p.get(valueKy)))).collect(Collectors.toList());
		if (infoList == null || infoList.isEmpty()) {
			return infoList;
		}
		// 前五笔数据+OtherData
		List<Map<String, Object>> fristFiveDataList = new ArrayList<>();
		List<Map<String, Object>> otherDataList = new ArrayList<>();
		if (infoList.size() > 5) {
			fristFiveDataList = infoList.subList(0, 5);
			otherDataList = infoList.subList(5, infoList.size());
		}
		if (0 < infoList.size() && infoList.size() <= 5) {
			fristFiveDataList = infoList;
		}
		// 处理其它数据
		if (!otherDataList.isEmpty() && otherDataList.size() > 0) {
			Map<String, Object> otherMap = new HashMap<String, Object>();
			Integer otherCount = 0;
			String code = "";
			boolean flag = true;
			for (Map<String, Object> map : otherDataList) {
				otherCount = otherCount + ConvertUtils.cInteger(map.get(valueKy), 0);
				if (flag) {
					code = cString(map.get(codeKy));
					flag = false;
				} else {
					code += "," + cString(map.get(codeKy));
				}
			}
			otherMap.put(valueKy, otherCount);
			otherMap.put(codeKy, code);
			otherMap.put(nameKy, "其它");
			fristFiveDataList.add(otherMap);
		}
		return fristFiveDataList;
	}

	/**
	 * <p>
	 * 描述：饼图或者圆环图超过五个就是5+1,针对两个value值时候新增的方法
	 * </p>
	 * 
	 * @param codeKy
	 * 参数
	 * @param nameKy
	 * 参数
	 * @param valueKy
	 * 参数
	 * @return List
	 * @editor:王跃 2018年7月3日 下午6:56:54
	 * @param infoList
	 * 参数
	 */
	public static List<Map<String, Object>> displayModelWithOther2(String codeKy, String nameKy, String valueKy,String valueKy2,
			List<Map<String, Object>> infoList) {
		infoList = infoList.stream().sorted((p, m) -> Integer.valueOf(String.valueOf(m.get(valueKy)))
				- Integer.valueOf(String.valueOf(p.get(valueKy)))).collect(Collectors.toList());
		if (infoList == null || infoList.isEmpty()) {
			return infoList;
		}
		// 前五笔数据+OtherData
		List<Map<String, Object>> fristFiveDataList = new ArrayList<>();
		List<Map<String, Object>> otherDataList = new ArrayList<>();
		if (infoList.size() > 5) {
			fristFiveDataList = infoList.subList(0, 5);
			otherDataList = infoList.subList(5, infoList.size());
		}
		if (0 < infoList.size() && infoList.size() <= 5) {
			fristFiveDataList = infoList;
		}
		// 处理其它数据
		if (!otherDataList.isEmpty() && otherDataList.size() > 0) {
			Map<String, Object> otherMap = new HashMap<String, Object>();
			Integer otherCount = 0;
			Integer otherCount2 = 0;
			String code = "";
			boolean flag = true;
			for (Map<String, Object> map : otherDataList) {
				otherCount = otherCount + ConvertUtils.cInteger(map.get(valueKy), 0);
				otherCount2 = otherCount2 + ConvertUtils.cInteger(map.get(valueKy2), 0);
				if (flag) {
					code = cString(map.get(codeKy));
					flag = false;
				} else {
					code += "," + cString(map.get(codeKy));
				}
			}
			otherMap.put(valueKy, otherCount);
			otherMap.put(valueKy2, otherCount2);
			otherMap.put(codeKy, code);
			otherMap.put(nameKy, "其它");
			fristFiveDataList.add(otherMap);
		}
		return fristFiveDataList;
	}

	/**
	 * <p>
	 * 描述：根据返回的结果 处理成国产化率需要的数据格式
	 * </p>
	 * 
	 * @param list
	 * 参数
	 * @return list 参数
	 * @editor:王跃 2018年7月7日 上午11:36:41
	 */
	public static List<Map<String, Object>> MakeChinaRateList(List<Map<String, Object>> list) {
		List<Map<String, Object>> resultReturn = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		list.stream().collect(Collectors.groupingBy(p -> p.get("isMadeInCN"),
				Collectors.summingInt(p -> transformDoubleToInteger(p.get("value"))))).forEach((Ky, value) -> {
					map.put("1".equals(String.valueOf(Ky)) ? "country" : "exportdata", value);
					resultReturn.add(map);
				});
		if (!map.containsKey("country")) {
			map.put("country", 0);
		} else if (!map.containsKey("exportdata")) {
			map.put("exportdata", 0);
		}
		return resultReturn;
	}

	/**
	 * <p>
	 * 描述：根据投运日期计算运行年限
	 * </p>
	 * 
	 * @return list
	 * @editor:王跃 2018年7月7日 上午11:36:41
	 * @param inUseDate
	 * 参数
	 */
	public static String calDeploymentYear(Date inUseDate) {
		String intervalTag = "";
		LocalDate today = LocalDate.now();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(inUseDate);
		LocalDate yr = LocalDate.of(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1),
				calendar.get(Calendar.DAY_OF_MONTH));
		long interval = ChronoUnit.YEARS.between(yr, today);
		if (interval < 1) {
			intervalTag = "01";
		} else if (interval >= 1 && interval < 3) {
			intervalTag = "02";
		} else if (interval >= 3 && interval < 5) {
			intervalTag = "03";
		} else if (interval >= 5 && interval < 10) {
			intervalTag = "04";
		} else if (interval >= 10) {
			intervalTag = "05";
		} else {
			intervalTag = "N/A";
		}
		return intervalTag;
	}


	/**
	 * <p>
	 * 描述：根据返回的结果自己进行分组合并
	 * </p>
	 * 
	 * @param list
	 * @return list
	 * @editor:王跃 2018年7月7日 上午11:36:41
	 * @param groupName
	 */
	public static List<Map<String, Object>> MakeAllList(List<Map<String, Object>> list, String groupName) {
		List<Map<String, Object>> resultReturn = new ArrayList<>();
		list.forEach(p -> {
			if (p.get(groupName) == null) {
				Map<String, Object> map = new HashMap<>();
				map.put(groupName, "N/A");
			}
		});
		list.stream().collect(Collectors.groupingBy(p -> p.get(groupName),
				Collectors.summingInt(p -> transformDoubleToInteger(p.get("value"))))).forEach((Ky, value) -> {
					Map<String, Object> map = new HashMap<>();
					map.put(groupName, Ky);
					map.put("value", value);
					resultReturn.add(map);
				});
		return resultReturn;
	}

	/**
	 * <p>
	 * 描述：根据运行年限获取开始投运日期
	 * </p>
	 * 
	 * @param code
	 * @return Date
	 * @editor:王跃 2018年7月11日 下午4:02:52
	 */
	public static Date getStartTimeByCode(String code) {
		if (StringUtils.isEmpty(code)) {
			return null;
		}
		Calendar result = Calendar.getInstance();
		switch (code) {
		case "01":
			result.add(Calendar.YEAR, -1);
			break;
		case "02":
			result.add(Calendar.YEAR, -3);
			break;
		case "03":
			result.add(Calendar.YEAR, -5);
			break;
		case "04":
			result.add(Calendar.YEAR, -10);
			break;
		case "05":
			return null;
		default:
			result.add(Calendar.YEAR, 1);
			break;
		}
		return result.getTime();
	}

	/**
	 * <p>
	 * 描述：根据运行年限获取开始结束投运日期
	 * </p>
	 * 
	 * @param code
	 * 参数
	 * @return Date
	 * @editor:王跃 2018年7月11日 下午4:02:52
	 */
	public static Date getEndTimeByCode(String code) {
		if (StringUtils.isEmpty(code)) {
			return null;
		}
		Calendar result = Calendar.getInstance();
		switch (code) {
		case "01":
			break;
		case "02":
			result.add(Calendar.YEAR, -1);
			break;
		case "03":
			result.add(Calendar.YEAR, -3);
			break;
		case "04":
			result.add(Calendar.YEAR, -5);
			break;
		case "05":
			result.add(Calendar.YEAR, -10);
			break;
		default:
			break;
		}
		return result.getTime();
	}

	/**
	 * <p>
	 * 描述：日期转换(字符串转成日期)
	 * </p>
	 * 
	 * @param obj
	 * 对象
	 * @return Date
	 * @editor:王跃 2018年7月19日 下午3:00:53
	 * @param format
	 * 格式化格式
	 */
	public static Date transformStringToDate(Object obj, String format) {
		String sDate = null;
		if (obj == null) {
			return null;
		} else {
			sDate = obj.toString();
		}

		if (StringUtils.isEmpty(sDate)) {
			return null;
		}

		Date dt = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			dt = sdf.parse(sDate);
		} catch (ParseException e) {
		}
		return dt;
	}

	/**
	 * <p>
	 * 描述：日期转换(日期转成字符串)
	 * </p>
	 * 
	 * @return Date
	 * @editor:王跃 2018年7月19日 下午3:00:53
	 * @param date
	 * 参数
	 * @param format
	 * 参数
	 */
	public static String transformDateToString(Date date, String format) {
		if (date == null) {
			return null;
		}
		String dt = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		dt = sdf.format(date);
		return dt;
	}

	/**
	 * <p>
	 * 描述：double转换为int（目前服务器存在此问题，需要转码）
	 * </p>
	 * 
	 * @return Date
	 * @editor:王跃 2018年7月19日 下午3:00:53
	 * @param object
	 * 参数
	 */
	public static Integer transformDoubleToInteger(Object object) {
		String value = String.valueOf(object);
		if (object == null || StringUtils.isEmpty(value)) {
			return 0;
		}
		try {
			BigDecimal big = new BigDecimal(value);
			return Integer.valueOf(String.valueOf(big.setScale(0)));
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * <p>
	 * 描述：筛选list中某个字段不为空，并且返回
	 * </p>
	 * 
	 * @editor:王跃 2018年7月19日 下午3:00:53
	 * @param list
	 * 参数
	 * @param code
	 * 参数
	 * @return List
	 */
	public static List<Map<String, Object>> filterIsNotNulll(List<Map<String, Object>> list, String code) {
		return list.stream().filter(p -> p.get(code) != null).collect(Collectors.toList());
	}

	/**
	 * <p>
	 * 描述：排序list
	 * </p>
	 * 
	 * @editor:王跃 2018年7月19日 下午3:00:53
	 * @param list
	 * 要排序的list
	 * @param code
	 * 排序code
	 * @param order
	 * 1倒叙
	 * @return List
	 * 
	 */
	public static List<Map<String, Object>> listSorted(List<Map<String, Object>> list, String code, String order) {
		if ("1".equals(order))
			return list.stream().sorted((p, m) -> m.get(code).toString().compareTo(p.get(code).toString()))
					.collect(Collectors.toList());

		return list.stream().sorted((p, m) -> p.get(code).toString().compareTo(m.get(code).toString()))
				.collect(Collectors.toList());
	}

	/**
	 * <p>
	 * 描述：获取本周第一天（字符串格式）
	 * </p>
	 * 
	 * @return Calendar
	 * @editor:王跃 2018年8月1日 下午4:59:42
	 */
	public static Calendar getFirstCalendarOfWeek() {
		Calendar day = Calendar.getInstance();
		int dayOfWeek = day.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek != Calendar.SUNDAY) {
			day.add(Calendar.DAY_OF_WEEK, Calendar.MONDAY - dayOfWeek);
		} else {
			day.add(Calendar.DAY_OF_WEEK, -6);
		}
		day.set(Calendar.HOUR_OF_DAY, 0);
		day.set(Calendar.MINUTE, 0);
		day.set(Calendar.SECOND, 0);
		day.set(Calendar.MILLISECOND, 0);
		return day;
	}

	/**
	 * <p>
	 * 描述：获取本周最后一天
	 * </p>
	 * 
	 * @return Calendar
	 * @editor:王跃 2018年8月1日 下午4:59:42
	 */
	public static Calendar getLastCalendarOfWeek() {
		Calendar cal = getFirstCalendarOfWeek();
		cal.add(Calendar.DAY_OF_MONTH, 6);
		return cal;
	}

	/**
	 * <p>
	 * 描述：判断日期是否是本周
	 * </p>
	 * 
	 * @param date
	 * 参数
	 * @return Boolean
	 * @editor:安东磊 2018年8月1日 下午4:59:42
	 * 
	 */
	public static Boolean isCurrentWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, -24);
		int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
		if (paramWeek == currentWeek) {
			return true;
		}
		return false;
	}

	/**
	 * <p>
	 * 描述：判断日期是否是本年，本月，本日
	 * </p>
	 * 
	 * @param date
	 * <p>
	 * 传入的时间
	 * </p>
	 * @param dateFormat
	 * <p>
	 * 参数格式为yyyy||yyyy-MM||yyyy-MM-dd
	 * </p>
	 * @return Boolean
	 * @editor:安东磊 2018年8月1日 下午4:59:42
	 */
	public static Boolean isCurrentTime(Date date, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		String param = sdf.format(date);
		String now = sdf.format(new Date());
		if (param.equals(now)) {
			return true;
		}
		return false;
	}



	/**
	 * <p>
	 * 描述：数据库时间转换日期类型
	 * </p>
	 * 
	 * @param obj
	 * 目标Data
	 * @param dateFormatStr
	 * 日期格式
	 * @return 日期类型Data
	 * @editor:安东磊 2017年4月12日 下午7:36:02
	 */
	public static String cDate(Object obj, String dateFormatStr) {
		return cDate(obj, "yyyy-MM-dd HH:mm:ss", dateFormatStr);
	}

	/**
	 * <p>
	 * 描述：数据库时间转换日期类型
	 * </p>
	 * 
	 * @param obj
	 * 目标Data
	 * @param objDataFormatStr
	 * 传入的时间格式，默认（yyyy-MM-dd HH:mm:ss）
	 * @param useDateFormatStr
	 * 所需要转换的时间格式
	 * @return 日期类型Data
	 * @editor:李传兵 2017年4月12日 下午7:36:02
	 */
	public static String cDate(Object obj, String objDataFormatStr, String useDateFormatStr) {

		String sDate = null;
		if (obj == null) {
			return null;
		} else {
			sDate = obj.toString();
		}

		if (StringUtils.isEmpty(sDate)) {
			return null;
		}

		String dt = null;

		SimpleDateFormat dateFormat = new SimpleDateFormat(useDateFormatStr);
		SimpleDateFormat dbDateFormat = new SimpleDateFormat(objDataFormatStr);

		try {
			dt = dateFormat.format(dbDateFormat.parse(sDate));
		} catch (ParseException e) {
		}
		return dt;
	}

	



	/**
	 * <p>
	 * 描述：获取月的最大天数
	 * </p>
	 *
	 * @return 获取当前月最大天数
	 * @editor:陈高云 2018年8月22日 上午10:45:59
	 */
	public static int getMonthMaxDays() {
		Calendar cal = Calendar.getInstance();
		return cal.getActualMaximum(Calendar.DATE);
	}

	/**
	 * <p>
	 * 描述：得到当前年份
	 * </p>
	 * 
	 * @return 获取当前年份
	 * @editor:陈高云 2018年8月22日 上午10:45:32
	 */
	public static String getYear() {
		return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	}

	/**
	 * <p>
	 * 描述：获取本年年份
	 * </p>
	 * 
	 * @return String
	 * @editor:安东磊 2018年8月17日 下午12:30:48
	 */
	public static String getNowYear() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		String _nowDay = dateFormat.format(calendar.getTime());
		return _nowDay;
	}

	/**
	 * <p>
	 * 描述：获取上月最大天数
	 * </p>
	 * 
	 * @return String
	 * @editor:安东磊 2018年8月17日 下午12:30:48
	 */
	public static String getbeforMonthEndDay() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);

		return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * <p>
	 * 描述：获取上月最大天数
	 * </p>
	 * 
	 * @return String
	 * @editor:安东磊 2018年8月17日 下午12:30:48
	 */
	public static String getbeforMonthStartDay() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-1";
	}

	/**
	 * <p>
	 * 描述：获取本月
	 * </p>
	 * 
	 * @return String
	 * @editor:安东磊 2018年8月16日 下午4:16:00
	 */
	public static String getNowMonth() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		String month = dateFormat.format(calendar.getTime());
		return month;
	}

	/**
	 * <p>
	 * 描述：获取昨日时间
	 * </p>
	 * 
	 * @return String
	 * @editor:安东磊 2018年8月22日 下午3:36:42
	 */
	@SuppressWarnings("static-access")
	public static String getYesterDay() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(calendar.DATE, -1);
		String time = dateFormat.format(calendar.getTime());
		return time;
	}

	/**
	 * <p>
	 * 描述：根据传入key的值，拼接list中获取的value的值
	 * </p>
	 * 
	 * @param list
	 * 从数据库中获取的list
	 * @param listForGroups
	 * 要预测的分组值
	 * @return String
	 * @editor:王跃 2018年9月15日 下午12:59:27
	 * 
	 */
	public static List<String> makeJunitResultList(List<Map<String, Object>> list, List<String> listForGroups) {
		return list.stream().map(p -> {
			List<String> returnList = new ArrayList<String>();
			listForGroups.forEach(q -> {
				returnList.add(String.valueOf(p.get(q)));
			});
			return returnList.stream().collect(Collectors.joining(","));
		}).distinct().collect(Collectors.toList());
	}

	
	/**
	 * <p>
	 * 描述：根据网省list转化网省String参数
	 * </p>
	 * 
	 * @param list
	 * 要转化的list
	 * @return String
	 * @editor:王跃 2018年10月11日 上午11:18:24
	 */
	public static String getProvinceStringByProvinceList(List<String> list) {

		return list.stream().collect(Collectors.joining(","));
	}


	/**
	 * <p>
	 * 描述：权限控制（防止越权访问）
	 * </p>
	 * 
	 * 
	 * @editor:王跃 2018年10月11日 上午11:18:24
	 * @param list
	 * 参数
	 * @param province
	 * 参数
	 * @return boolean
	 */
	public static boolean judgmenteditority(List<String> list, Object province) {
		if ("".equals(String.valueOf(province)))
			return true;
		return list.containsAll(Stream.of(String.valueOf(province).split(",")).collect(Collectors.toList()));
	}
	
	/**
	 * <p>
	 * 描述：后台时间验证开始时间不能大于结束时间
	 * </p>
	 * @editor:安东磊 2018年10月11日 上午11:18:24
	 * @param jsonParam
	 * @param strtime
	 * @param endtime
	 * 参数
	 * @param resultMap
	 */
	public static boolean checkTime(Map<String, Object> jsonParam, String strtime, String endtime,Map<String, Object> resultMap) {
		List<Map<String, Object>> resultList = new ArrayList<>();
		// 时间校验
		if (!"".equals(jsonParam.get(strtime)) && !"".equals(jsonParam.get(endtime))) {
			Object str = jsonParam.get(strtime);
			Object end = jsonParam.get(endtime);
			if (transformDoubleToInteger(String.valueOf(end).replaceAll("-", "")) < transformDoubleToInteger(String.valueOf(str).replaceAll("-", ""))) {
				resultMap.put("data", resultList);
				resultMap.put("total", 0);
				return true;
			}
		}
		return false;
		}

}
 