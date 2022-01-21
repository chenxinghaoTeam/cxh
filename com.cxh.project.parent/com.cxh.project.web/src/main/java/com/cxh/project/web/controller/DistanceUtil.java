package com.cxh.project.web.controller;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalCoordinates;

import com.cxh.project.web.enums.Colors;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2021年8月18日 下午6:13:11 类说明
 */
public class DistanceUtil {

	public static void main(String[] args) {
		// System.out.println("经纬度距离计算结果：" + getDistance(109.371319, 22.155406,
		// 108.009758, 21.679011) + "米");
		System.out.println(Colors.getValue("4"));
	}

	public static double getDistance(double longitudeFrom, double latitudeFrom, double longitudeTo, double latitudeTo) {
		GlobalCoordinates source = new GlobalCoordinates(latitudeFrom, longitudeFrom);
		GlobalCoordinates target = new GlobalCoordinates(latitudeTo, longitudeTo);

		return new GeodeticCalculator().calculateGeodeticCurve(Ellipsoid.Sphere, source, target)
				.getEllipsoidalDistance();
	}
}
