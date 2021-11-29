package com.cis.rockon.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Location")
@Setter @Getter
public class Location {

    @Id
    @GeneratedValue
    private Long id;

    private double longitude;

    private double latitude;

    public Location(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

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
