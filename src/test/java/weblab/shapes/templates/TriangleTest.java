package weblab.shapes.templates;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import weblab.models.Point;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты треугольника")
public class TriangleTest {
    @Test
    @DisplayName("Точка внутри треугольника - должна вернуть true")
    void pointInsideTriangleShouldReturnTrue() {
        Triangle triangle = new Triangle(4.0, 4.0);
        Point point = new Point(1.0, 1.0, 4.0);
        assertTrue(triangle.contains(point));
    }

    @Test
    @DisplayName("Точка на левой границе (x=0) - должна вернуть true")
    void pointOnLeftBoundaryShouldReturnTrue() {
        Triangle triangle = new Triangle(4.0, 4.0);
        Point point = new Point(0.0, 1.0, 4.0);
        assertTrue(triangle.contains(point));
    }

    @Test
    @DisplayName("Точка на нижней границе (y=0) - должна вернуть true")
    void pointOnBottomBoundaryShouldReturnTrue() {
        Triangle triangle = new Triangle(4.0, 4.0);
        Point point = new Point(1.0, 0.0, 4.0);
        assertTrue(triangle.contains(point));
    }

    @Test
    @DisplayName("Точка на гипотенузе - должна вернуть true")
    void pointOnHypotenuseShouldReturnTrue() {
        Triangle triangle = new Triangle(4.0, 4.0);
        Point point = new Point(2.0, 2.0, 4.0);
        assertTrue(triangle.contains(point));
    }

    @Test
    @DisplayName("Точка в вершине (0, 0) - должна вернуть true")
    void pointAtOriginShouldReturnTrue() {
        Triangle triangle = new Triangle(4.0, 4.0);
        Point point = new Point(0.0, 0.0, 4.0);
        assertTrue(triangle.contains(point));
    }

    @Test
    @DisplayName("Точка в вершине (base, 0) - должна вернуть true")
    void pointAtBaseVertexShouldReturnTrue() {
        Triangle triangle = new Triangle(4.0, 4.0);
        Point point = new Point(4.0, 0.0, 4.0);
        assertTrue(triangle.contains(point));
    }

    @Test
    @DisplayName("Точка в вершине (0, height) - должна вернуть true")
    void pointAtHeightVertexShouldReturnTrue() {
        Triangle triangle = new Triangle(4.0, 4.0);
        Point point = new Point(0.0, 4.0, 4.0);
        assertTrue(triangle.contains(point));
    }

    @Test
    @DisplayName("Точка выше гипотенузы - должна вернуть false")
    void pointAboveHypotenuseShouldReturnFalse() {
        Triangle triangle = new Triangle(4.0, 4.0);
        Point point = new Point(1.0, 3.5, 4.0);
        assertFalse(triangle.contains(point));
    }

    @Test
    @DisplayName("Точка справа от основания (x > base) - должна вернуть false")
    void pointRightOfBaseShouldReturnFalse() {
        Triangle triangle = new Triangle(4.0, 4.0);
        Point point = new Point(5.0, 1.0, 4.0);
        assertFalse(triangle.contains(point));
    }

    @Test
    @DisplayName("Точка выше высоты (y > height) - должна вернуть false")
    void pointAboveHeightShouldReturnFalse() {
        Triangle triangle = new Triangle(4.0, 4.0);
        Point point = new Point(1.0, 5.0, 4.0);
        assertFalse(triangle.contains(point));
    }

    @Test
    @DisplayName("Точка с отрицательным x - должна вернуть false")
    void pointWithNegativeXShouldReturnFalse() {
        Triangle triangle = new Triangle(4.0, 4.0);
        Point point = new Point(-1.0, 1.0, 4.0);
        assertFalse(triangle.contains(point));
    }

    @Test
    @DisplayName("Точка с отрицательным y - должна вернуть false")
    void pointWithNegativeYShouldReturnFalse() {
        Triangle triangle = new Triangle(4.0, 4.0);
        Point point = new Point(1.0, -1.0, 4.0);
        assertFalse(triangle.contains(point));
    }

    @Test
    @DisplayName("Точка с отрицательными x и y - должна вернуть false")
    void pointWithNegativeXYShouldReturnFalse() {
        Triangle triangle = new Triangle(4.0, 4.0);
        Point point = new Point(-1.0, -1.0, 4.0);
        assertFalse(triangle.contains(point));
    }

    @ParameterizedTest
    @CsvSource({
            "0.5, 0.25, true",
            "1.0, 0.5, true",
            "1.5, 0.5, true",
            "2.0, 1.0, true",
            "2.5, 0.5, true",
            "3.0, 0.5, true",
            "0.0, 0.0, true",
            "4.0, 0.0, true",
            "0.0, 4.0, true",
            "2.0, 2.0, true",
            "0.0, 2.0, true",
            "2.0, 0.0, true",
            "1.0, 3.0, true",
            "3.0, 1.0, true",
            "1.0, 2.0, true",

            "3.0, 1.5, false",
            "1.0, 3.5, false",
            "2.5, 2.0, false",
            "5.0, 1.0, false",
            "1.0, 5.0, false",
            "-1.0, 1.0, false",
            "1.0, -1.0, false",
            "-1.0, -1.0, false",
            "5.0, 5.0, false"
    })
    @DisplayName("Параметризованный тест треугольника")
    void triangleContainsShouldReturnExpectedResult(double x, double y, boolean expected) {
        Triangle triangle = new Triangle(4.0, 4.0);
        Point point = new Point(x, y, 4.0);
        assertEquals(expected, triangle.contains(point));
    }
}
