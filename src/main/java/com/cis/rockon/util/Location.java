package com.cis.rockon.util;

import lombok.AllArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
public class Location {

    private double longitude;
    private double latitude;

    public Location() {
    }

    // https://stackoverflow.com/questions/27928/calculate-distance-between-two-latitude-longitude-points-haversine-formula
    public double distance(Location other) {
        double p = Math.PI/180;
        double a = 0.5 - Math.cos(p * (latitude - other.latitude)) * 0.5 +
                Math.cos(p * latitude) + Math.cos(p * other.latitude) +
                (1 - Math.cos(p * (longitude - other.longitude))) * 0.5;

        return Math.asin(Math.sqrt(a)) * 12742; // KM
    }
}
