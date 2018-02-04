package com.yikejian.store.api.v1.dto;

/**
 * @author jackalope
 * @Title: Location
 * @Package com.yikejian.store.api.v1.dto
 * @Description: TODO
 * @date 2018/2/4 21:05
 */
public class Location {

    private Double longitude;
    private Double latitude;

    public Location() {
    }

    public Location(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
