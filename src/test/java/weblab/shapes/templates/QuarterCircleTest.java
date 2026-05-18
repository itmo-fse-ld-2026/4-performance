package weblab.shapes.templates;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import weblab.models.Point;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты четверти круга")
public class QuarterCircleTest {
    @Test
    @DisplayName("Точка внутри четверти круга - должна вернуть true")
    void pointInsideQuarterCircleShouldReturnTrue() {
        QuarterCircle circle = new QuarterCircle(5.0);
        Point point = new Point(3.0, 3.0, 5.0);
        assertTrue(circle.contains(point));
    }

    @Test
    @DisplayName("Точка на дуге - должна вернуть true")
    void pointOnArcShouldReturnTrue() {
        QuarterCircle circle = new QuarterCircle(5.0);
        Point point = new Point(3.0, 4.0, 5.0);
        assertTrue(circle.contains(point));
    }

    @Test
    @DisplayName("Точка на оси X (x > 0, y = 0) - должна вернуть true")
    void pointOnXAxisShouldReturnTrue() {
        QuarterCircle circle = new QuarterCircle(5.0);
        Point point = new Point(3.0, 0.0, 5.0);
        assertTrue(circle.contains(point));
    }

    @Test
    @DisplayName("Точка на оси Y (x = 0, y > 0) - должна вернуть true")
    void pointOnYAxisShouldReturnTrue() {
        QuarterCircle circle = new QuarterCircle(5.0);
        Point point = new Point(0.0, 3.0, 5.0);
        assertTrue(circle.contains(point));
    }

    @Test
    @DisplayName("Точка в начале координат (0, 0) - должна вернуть true")
    void pointAtOriginShouldReturnTrue() {
        QuarterCircle circle = new QuarterCircle(5.0);
        Point point = new Point(0.0, 0.0, 5.0);
        assertTrue(circle.contains(point));
    }

    @Test
    @DisplayName("Точка за пределами круга - должна вернуть false")
    void pointOutsideArcShouldReturnFalse() {
        QuarterCircle circle = new QuarterCircle(5.0);
        Point point = new Point(4.0, 4.0, 5.0);
        assertFalse(circle.contains(point));
    }

    @Test
    @DisplayName("Точка с отрицательным x - должна вернуть false")
    void pointWithNegativeXShouldReturnFalse() {
        QuarterCircle circle = new QuarterCircle(5.0);
        Point point = new Point(-3.0, 3.0, 5.0);
        assertFalse(circle.contains(point));
    }

    @Test
    @DisplayName("Точка с отрицательным y - должна вернуть false")
    void pointWithNegativeYShouldReturnFalse() {
        QuarterCircle circle = new QuarterCircle(5.0);
        Point point = new Point(3.0, -3.0, 5.0);
        assertFalse(circle.contains(point));
    }

    @Test
    @DisplayName("Точка с отрицательными x и y - должна вернуть false")
    void pointWithNegativeXYShouldReturnFalse() {
        QuarterCircle circle = new QuarterCircle(5.0);
        Point point = new Point(-3.0, -3.0, 5.0);
        assertFalse(circle.contains(point));
    }

    @ParameterizedTest
    @CsvSource({
            "2.0, 2.0, true",
            "3.0, 4.0, true",
            "4.0, 3.0, true",
            "0.0, 5.0, true",
            "5.0, 0.0, true",
            "0.0, 0.0, true",
            "1.0, 1.0, true",
            "3.5, 3.5, true",

            "4.0, 4.0, false",
            "6.0, 0.0, false",
            "0.0, 6.0, false",
            "-2.0, 2.0, false",
            "2.0, -2.0, false",
            "-2.0, -2.0, false"
    })
    @DisplayName("Параметризованный тест четверти круга")
    void quarterCircleContainsShouldReturnExpectedResult(double x, double y, boolean expected) {
        QuarterCircle circle = new QuarterCircle(5.0);
        Point point = new Point(x, y, 5.0);
        assertEquals(expected, circle.contains(point));
    }
}
