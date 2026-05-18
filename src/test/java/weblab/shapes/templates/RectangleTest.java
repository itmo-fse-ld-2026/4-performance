package weblab.shapes.templates;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import weblab.models.Point;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты прямоугольника")
public class RectangleTest {
    @Test
    @DisplayName("Точка внутри прямоугольника - должна вернуть true")
    void pointInsideRectangleShouldReturnTrue() {
        Rectangle rect = new Rectangle(5.0, 3.0);
        Point point = new Point(2.0, 1.0, 1.0);
        assertTrue(rect.contains(point));
    }

    @Test
    @DisplayName("Точка на левой границе (x = 0) - должна вернуть true")
    void pointOnLeftBoundaryShouldReturnTrue() {
        Rectangle rect = new Rectangle(5.0, 3.0);
        Point point = new Point(0.0, 1.0, 1.0);
        assertTrue(rect.contains(point));
    }

    @Test
    @DisplayName("Точка на правой границе (x = width) - должна вернуть true")
    void pointOnRightBoundaryShouldReturnTrue() {
        Rectangle rect = new Rectangle(5.0, 3.0);
        Point point = new Point(5.0, 1.0, 1.0);
        assertTrue(rect.contains(point));
    }

    @Test
    @DisplayName("Точка на нижней границе (y = 0) - должна вернуть true")
    void pointOnBottomBoundaryShouldReturnTrue() {
        Rectangle rect = new Rectangle(5.0, 3.0);
        Point point = new Point(2.0, 0.0, 1.0);
        assertTrue(rect.contains(point));
    }

    @Test
    @DisplayName("Точка на верхней границе (y = height) - должна вернуть true")
    void pointOnTopBoundaryShouldReturnTrue() {
        Rectangle rect = new Rectangle(5.0, 3.0);
        Point point = new Point(2.0, 3.0, 1.0);
        assertTrue(rect.contains(point));
    }

    @Test
    @DisplayName("Точка в левом нижнем углу (0, 0) - должна вернуть true")
    void pointAtBottomLeftCornerShouldReturnTrue() {
        Rectangle rect = new Rectangle(5.0, 3.0);
        Point point = new Point(0.0, 0.0, 1.0);
        assertTrue(rect.contains(point));
    }

    @Test
    @DisplayName("Точка в правом верхнем углу (width, height) - должна вернуть true")
    void pointAtTopRightCornerShouldReturnTrue() {
        Rectangle rect = new Rectangle(5.0, 3.0);
        Point point = new Point(5.0, 3.0, 1.0);
        assertTrue(rect.contains(point));
    }

    @Test
    @DisplayName("Точка справа от правой границы (x > width) - должна вернуть false")
    void pointRightOfRightBoundaryShouldReturnFalse() {
        Rectangle rect = new Rectangle(5.0, 3.0);
        Point point = new Point(6.0, 1.0, 1.0);
        assertFalse(rect.contains(point));
    }

    @Test
    @DisplayName("Точка выше верхней границы (y > height) - должна вернуть false")
    void pointAboveTopBoundaryShouldReturnFalse() {
        Rectangle rect = new Rectangle(5.0, 3.0);
        Point point = new Point(2.0, 4.0, 1.0);
        assertFalse(rect.contains(point));
    }

    @Test
    @DisplayName("Точка с отрицательным x - должна вернуть false")
    void pointWithNegativeXShouldReturnFalse() {
        Rectangle rect = new Rectangle(5.0, 3.0);
        Point point = new Point(-1.0, 1.0, 1.0);
        assertFalse(rect.contains(point));
    }

    @Test
    @DisplayName("Точка с отрицательным y - должна вернуть false")
    void pointWithNegativeYShouldReturnFalse() {
        Rectangle rect = new Rectangle(5.0, 3.0);
        Point point = new Point(2.0, -1.0, 1.0);
        assertFalse(rect.contains(point));
    }

    @Test
    @DisplayName("Точка с отрицательными x и y - должна вернуть false")
    void pointWithNegativeXYShouldReturnFalse() {
        Rectangle rect = new Rectangle(5.0, 3.0);
        Point point = new Point(-1.0, -1.0, 4.0);
        assertFalse(rect.contains(point));
    }

    @ParameterizedTest
    @CsvSource({
            "1.0, 1.0, true",
            "2.5, 1.5, true",
            "4.9, 2.9, true",
            "0.0, 0.0, true",
            "5.0, 3.0, true",
            "0.0, 1.5, true",
            "2.5, 0.0, true",
            "5.0, 1.5, true",
            "2.5, 3.0, true",

            "5.1, 1.0, false",
            "1.0, 3.1, false",
            "-1.0, 1.0, false",
            "1.0, -1.0, false",
            "-1.0, -1.0, false",
            "6.0, 4.0, false"
    })
    @DisplayName("Параметризованный тест прямоугольника")
    void rectangleContainsShouldReturnExpectedResult(double x, double y, boolean expected) {
        Rectangle rect = new Rectangle(5.0, 3.0);
        Point point = new Point(x, y, 1.0);
        assertEquals(expected, rect.contains(point));
    }
}

