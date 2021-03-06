package com.springboot.jpa.demo.core.util;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;

/**
 * GPS工具类.
 *
 * @author luojimeng
 * @date 2020/4/12
 */
public class GPSUtils {

    /**
     * 将经纬度的分秒转换为度数.
     *
     * @return
     */
    public static double fen2du(String fen) {
        return Double.parseDouble(fen) / 60d;
    }

    /**
     * 计算两个坐标的距离.
     *
     * @param latitudeFenFrom  纬度起点
     * @param longitudeFenFrom 经度起点
     * @param latitudeFenTo    纬度终点
     * @param longitudeFenTo   经度终点
     * @return 米
     */
    public static double getDistance(String latitudeFenFrom, String longitudeFenFrom, String latitudeFenTo, String longitudeFenTo) {
        GlobalCoordinates from = new GlobalCoordinates(fen2du(latitudeFenFrom), fen2du(longitudeFenFrom));
        GlobalCoordinates to = new GlobalCoordinates(fen2du(latitudeFenTo), fen2du(longitudeFenTo));
        GeodeticCurve geo = new GeodeticCalculator().calculateGeodeticCurve(Ellipsoid.Sphere, from, to);
        return geo.getEllipsoidalDistance();
    }

    public static double getDistance(double latitudeDuFrom, double longitudeDuFrom, double latitudeDuTo, double longitudeDuTo) {
        GlobalCoordinates from = new GlobalCoordinates(latitudeDuFrom, longitudeDuFrom);
        GlobalCoordinates to = new GlobalCoordinates(latitudeDuTo, longitudeDuTo);
        GeodeticCurve geo = new GeodeticCalculator().calculateGeodeticCurve(Ellipsoid.Sphere, from, to);
        return geo.getEllipsoidalDistance();
    }
}
