package weblab.shapes.templates;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import weblab.models.Point;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты трансформации по квадрантам")
public class QuadrantShapeTest {
    @Test
    @DisplayName("Квадрант 1: точка с положительными координатами - должна вернуть true")
    void quadrant1PositiveCoordinatesShouldReturnTrue() {
        Rectangle rect = new Rectangle(5.0, 3.0);
        QuadrantShape qs = new QuadrantShape(rect, 1);
        Point point = new Point(2.0, 1.0, 1.0);
        assertTrue(qs.contains(point));
    }

    @Test
    @DisplayName("Квадрант 1: точка с отрицательным x - должна вернуть false")
    void quadrant1NegativeXShouldReturnFalse() {
        Rectangle rect = new Rectangle(5.0, 3.0);
        QuadrantShape qs = new QuadrantShape(rect, 1);
        Point point = new Point(-2.0, 1.0, 1.0);
        assertFalse(qs.contains(point));
    }

    @Test
    @DisplayName("Квадрант 1: точка с отрицательным y - должна вернуть false")
    void quadrant1NegativeYShouldReturnFalse() {
        Rectangle rect = new Rectangle(5.0, 3.0);
        QuadrantShape qs = new QuadrantShape(rect, 1);
        Point point = new Point(2.0, -1.0, 1.0);
        assertFalse(qs.contains(point));
    }

    @Test
    @DisplayName("Квадрант 1: точка с отрицательными координатами - должна вернуть false")
    void quadrant1NegativeCoordinatesShouldReturnFalse() {
        Rectangle rect = new Rectangle(5.0, 3.0);
        QuadrantShape qs = new QuadrantShape(rect, 1);
        Point point = new Point(-2.0, -1.0, 1.0);
        assertFalse(qs.contains(point));
    }

    @Test
    @DisplayName("Квадрант 2: точка с положительными координатами - должна вернуть false")
    void quadrant2PositiveCoordinatesShouldReturnFalse() {
        Rectangle rect = new Rectangle(5.0, 3.0);
        QuadrantShape qs = new QuadrantShape(rect, 2);
        Point point = new Point(2.0, 1.0, 1.0);
        assertFalse(qs.contains(point));
    }

    @Test
    @DisplayName("Квадрант 2: точка с отрицательным x трансформируется - должна вернуть true")
    void quadrant2NegativeXShouldTransformAndReturnTrue() {
        Rectangle rect = new Rectangle(5.0, 3.0);
        QuadrantShape qs = new QuadrantShape(rect, 2);
        Point point = new Point(-2.0, 1.0, 1.0);
        assertTrue(qs.contains(point));
    }

    @Test
    @DisplayName("Квадрант 2: точка с отрицательным y - должна вернуть false")
    void quadrant2NegativeYShouldReturnFalse() {
        Rectangle rect = new Rectangle(5.0, 3.0);
        QuadrantShape qs = new QuadrantShape(rect, 2);
        Point point = new Point(2.0, -1.0, 1.0);
        assertFalse(qs.contains(point));
    }

    @Test
    @DisplayName("Квадрант 2: точка с отрицательными координатами - должна вернуть false")
    void quadrant2NegativeCoordinatesShouldReturnFalse() {
        Rectangle rect = new Rectangle(5.0, 3.0);
        QuadrantShape qs = new QuadrantShape(rect, 2);
        Point point = new Point(-2.0, -1.0, 1.0);
        assertFalse(qs.contains(point));
    }

    @Test
    @DisplayName("Квадрант 3: точка с положительными координатами - должна вернуть false")
    void quadrant3PositiveCoordinatesShouldReturnFalse() {
        Rectangle rect = new Rectangle(5.0, 3.0);
        QuadrantShape qs = new QuadrantShape(rect, 3);
        Point point = new Point(2.0, 1.0, 1.0);
        assertFalse(qs.contains(point));
    }

    @Test
    @DisplayName("Квадрант 3: точка с отрицательным x - должна вернуть false")
    void quadrant3NegativeXShouldReturnFalse() {
        Rectangle rect = new Rectangle(5.0, 3.0);
        QuadrantShape qs = new QuadrantShape(rect, 3);
        Point point = new Point(-2.0, 1.0, 1.0);
        assertFalse(qs.contains(point));
    }

    @Test
    @DisplayName("Квадрант 3: точка с отрицательным y - должна вернуть false")
    void quadrant3NegativeYShouldReturnFalse() {
        Rectangle rect = new Rectangle(5.0, 3.0);
        QuadrantShape qs = new QuadrantShape(rect, 3);
        Point point = new Point(2.0, -1.0, 1.0);
        assertFalse(qs.contains(point));
    }

    @Test
    @DisplayName("Квадрант 3: точка с отрицательными координатами трансформируется - должна вернуть true")
    void quadrant3NegativeCoordinatesShouldTransformAndReturnFalse() {
        Rectangle rect = new Rectangle(5.0, 3.0);
        QuadrantShape qs = new QuadrantShape(rect, 3);
        Point point = new Point(-2.0, -1.0, 1.0);
        assertTrue(qs.contains(point));
    }

    @Test
    @DisplayName("Квадрант 4: точка с положительными координатами - должна вернуть false")
    void quadrant4PositiveCoordinatesShouldReturnFalse() {
        Rectangle rect = new Rectangle(5.0, 3.0);
        QuadrantShape qs = new QuadrantShape(rect, 4);
        Point point = new Point(2.0, 1.0, 1.0);
        assertFalse(qs.contains(point));
    }

    @Test
    @DisplayName("Квадрант 4: точка с отрицательным x - должна вернуть false")
    void quadrant4NegativeXShouldReturnFalse() {
        Rectangle rect = new Rectangle(5.0, 3.0);
        QuadrantShape qs = new QuadrantShape(rect, 4);
        Point point = new Point(-2.0, 1.0, 1.0);
        assertFalse(qs.contains(point));
    }

    @Test
    @DisplayName("Квадрант 4: точка с отрицательным y трансформируется - должна вернуть true")
    void quadrant4NegativeYShouldTransformAndReturnFalse() {
        Rectangle rect = new Rectangle(5.0, 3.0);
        QuadrantShape qs = new QuadrantShape(rect, 4);
        Point point = new Point(2.0, -1.0, 1.0);
        assertTrue(qs.contains(point));
    }

    @Test
    @DisplayName("Квадрант 4: точка с отрицательными координатами - должна вернуть false")
    void quadrant4NegativeCoordinatesShouldReturnFalse() {
        Rectangle rect = new Rectangle(5.0, 3.0);
        QuadrantShape qs = new QuadrantShape(rect, 4);
        Point point = new Point(-2.0, -1.0, 1.0);
        assertFalse(qs.contains(point));
    }

    @Test
    @DisplayName("Треугольник в квадранте 2: точка с отрицательным x трансформируется - должна вернуть true")
    void triangleInQuadrant2NegativeXShouldTransformAndReturnTrue() {
        Triangle triangle = new Triangle(4.0, 4.0);
        QuadrantShape qs = new QuadrantShape(triangle, 2);
        Point point = new Point(-2.0, 1.0, 4.0);
        assertTrue(qs.contains(point));
    }

    @Test
    @DisplayName("Треугольник в квадранте 2: точка с отрицательными координатами - должна вернуть false")
    void triangleInQuadrant2NegativeCoordinatesShouldReturnFalse() {
        Triangle triangle = new Triangle(4.0, 4.0);
        QuadrantShape qs = new QuadrantShape(triangle, 2);
        Point point = new Point(-2.0, -1.0, 4.0);
        assertFalse(qs.contains(point));
    }

    @Test
    @DisplayName("Четверть круга в квадранте 4: точка с отрицательным y трансформируется - должна вернуть true")
    void quarterCircleInQuadrant4NegativeYShouldTransformAndReturnTrue() {
        QuarterCircle circle = new QuarterCircle(5.0);
        QuadrantShape qs = new QuadrantShape(circle, 4);
        Point point = new Point(3.0, -3.0, 1.0);
        assertTrue(qs.contains(point));
    }

    @Test
    @DisplayName("Четверть круга в квадранте 4: точка с положительными координатами - должна вернуть false")
    void quarterCircleInQuadrant4PositiveCoordinatesShouldReturnFalse() {
        QuarterCircle circle = new QuarterCircle(5.0);
        QuadrantShape qs = new QuadrantShape(circle, 4);
        Point point = new Point(3.0, 3.0, 1.0);
        assertFalse(qs.contains(point));
    }
}
