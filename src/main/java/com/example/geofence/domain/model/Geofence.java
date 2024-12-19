package com.example.geofence.domain.model;

import lombok.*;
import org.locationtech.jts.geom.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Geofence {

    private List<Coordinate> coordinates;
    private LinearRing linearRing;
    private Polygon polygon;

    public static Geofence create(List<Coordinate> coordinates) {

        if(coordinates.size() < LinearRing.MINIMUM_VALID_SIZE) {
            throw new IllegalArgumentException("좌표는 4개 이상이어야 합니다.");
        }

        GeometryFactory factory = new GeometryFactory();

        LinearRing linearRing = factory.createLinearRing(coordinates.toArray(new Coordinate[0]));

        Polygon polygon = factory.createPolygon(linearRing);

        GeofenceBuilder builder = Geofence.builder();
        builder.linearRing(linearRing);
        builder.polygon(polygon);

        return builder.build();
    }

    public boolean isPointInside(Coordinate coordinate) {

        GeometryFactory factory = new GeometryFactory();

        return polygon.covers(factory.createPoint(coordinate));
    }
}
