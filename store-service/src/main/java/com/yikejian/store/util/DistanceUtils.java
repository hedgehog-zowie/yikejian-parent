package com.yikejian.store.util;

/**
 * <code>DistanceUtils</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/23 14:36
 */
public final class DistanceUtils {

    /**
     * 地球半径 平均值，千米
     */
    private static final double EARTH_RADIUS = 6371.393;

    /**
     * 给定的经度1，纬度1；经度2，纬度2. 计算2个经纬度之间的距离。
     * @param latitude1 纬度1
     * @param longitude1 经度1
     * @param latitude2 纬度2
     * @param longitude2 经度2
     * @return 距离（公里、千米）
     */
    public static double Distance(double latitude1, double longitude1, double latitude2, double longitude2) {
        //用haversine公式计算球面两点间的距离。
        //经纬度转换成弧度
        latitude1 = ConvertDegreesToRadians(latitude1);
        longitude1 = ConvertDegreesToRadians(longitude1);
        latitude2 = ConvertDegreesToRadians(latitude2);
        longitude2 = ConvertDegreesToRadians(longitude2);
        //差值
        double dLongitude = Math.abs(longitude1 - longitude2);
        double dLatitude = Math.abs(latitude1 - latitude2);
        //h is the great circle distance in radians, great circle就是一个球体上的切面，它的圆心即是球心的一个周长最大的圆。
        double h = haverSine(dLatitude) + Math.cos(latitude1) * Math.cos(latitude2) * haverSine(dLongitude);
        double distance = 2 * EARTH_RADIUS * Math.asin(Math.sqrt(h));
        return distance;
    }

    private static double haverSine(double theta) {
        double v = Math.sin(theta / 2);
        return v * v;
    }

    /**
     * 将角度换算为弧度。
     * @param degrees 角度
     * @return 弧度
     */
    private static double ConvertDegreesToRadians(double degrees) {
        return degrees * Math.PI / 180;
    }

    /**
     * 将弧度换算为角度
     * @param radian 弧度
     * @return 角度
     */
    private static double ConvertRadiansToDegrees(double radian) {
        return radian * 180.0 / Math.PI;
    }

}
