package com.app.potholewarning.Models;

public class CheckPoint {
    private Double Lat;
    private Double Lng;
    public CheckPoint() {
    }
    public CheckPoint(Double lat, Double lng) {
        Lat = lat;
        Lng = lng;
    }

    public Double getLat() {
        return Lat;
    }

    public Double getLng() {
        return Lng;
    }

    public void setLat(Double lat) {
        Lat = lat;
    }

    public void setLng(Double lng) {
        Lng = lng;
    }
}
