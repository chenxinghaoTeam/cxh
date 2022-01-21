package com.cxh.project.web.enums; 
/**
 * 枚举
 * @author chenxinghao
 * 2021年8月26日-下午2:06:55
 */
public enum Colors {

	A("0","红色"),
	B("1","橙色"),
	C("2","黄色"),
	D("3","绿色"),
	E("4","青色"),
	F("5","蓝色"),
	G("6","紫色");
	
	private String code;
	private String color;
	
	private Colors(String code,String color) {
		this.code = code;
		this.color = color;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}
	
	
	/**
	 * 根据code获取value
	 * @param code
	 * @return
	 */
	public static String getValue(String code) {
		for(Colors color:values()) {
			if(color.getCode().equals(code))return color.getColor();
		}
		return "";
	}

}
 