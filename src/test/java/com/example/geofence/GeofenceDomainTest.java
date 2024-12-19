package com.example.geofence;

import com.example.geofence.domain.model.Geofence;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GeofenceDomainTest {

    private final GeometryFactory factory = new GeometryFactory();

    @Test
    @DisplayName("Geofence 생성 성공 테스트")
    void createGeofence_success() {
        // Given
        List<Coordinate> coordinates = Arrays.asList(
                new Coordinate(0, 0),
                new Coordinate(0, 10),
                new Coordinate(10, 0),
                new Coordinate(0, 0)
        );

        // When
        Geofence geofence = Geofence.create(coordinates);

        // Then
        LinearRing linearRing = factory.createLinearRing(coordinates.toArray(new Coordinate[0]));
        assertEquals(linearRing, geofence.getLinearRing());

        Polygon polygon = factory.createPolygon(linearRing);
        assertEquals(polygon, geofence.getPolygon());
    }

    @Test
    @DisplayName("Geofence 생성 실패 테스트: 좌표가 3개 미만일 경우")
    void createGeofence_fail_coordinates_are_less_than_three() {
        // Given
        List<Coordinate> coordinates = Arrays.asList(
                new Coordinate(0, 0),
                new Coordinate(0, 10),
                new Coordinate(10, 0)
        );

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> Geofence.create(coordinates));
    }

    @Test
    @DisplayName("특정 좌표가 Geofence 내부에 있는 경우, true 반환")
    void isPointInside_point_inside() {
        // Given
        List<Coordinate> coordinates = Arrays.asList(
                new Coordinate(0, 0),
                new Coordinate(0, 10),
                new Coordinate(10, 0),
                new Coordinate(0, 0)
        );

        Geofence geofence = Geofence.create(coordinates);

        Coordinate coordinate = new Coordinate(1, 1);

        // When & Then
        assertTrue(geofence.isPointInside(coordinate));
    }

    @Test
    @DisplayName("특정 좌표가 Geofence 외부에 있는 경우, false 반환")
    void isPointInside_point_outside() {
        // Given
        List<Coordinate> coordinates = Arrays.asList(
                new Coordinate(0, 0),
                new Coordinate(0, 10),
                new Coordinate(10, 0),
                new Coordinate(0, 0)
        );

        Geofence geofence = Geofence.create(coordinates);

        Coordinate coordinate = new Coordinate(10, 10);

        // When & Then
        assertFalse(geofence.isPointInside(coordinate));
    }

    @Test
    @DisplayName("특정 좌표가 Geofence 경계에 있는 경우, true 반환")
    void isPointInside() {
        // Given
        List<Coordinate> coordinates = Arrays.asList(
                new Coordinate(0, 0),
                new Coordinate(0, 10),
                new Coordinate(10, 0),
                new Coordinate(0, 0)
        );

        Geofence geofence = Geofence.create(coordinates);

        Coordinate coordinate = new Coordinate(0, 10);

        // When & Then
        assertTrue(geofence.isPointInside(coordinate));
    }
}
