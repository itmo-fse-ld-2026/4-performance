package weblab.beans;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import weblab.models.Point;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты валидации точек")
public class PointValidationBeanTest {
    private PointValidationBean validationBean;

    @BeforeEach
    void setUp() {
        validationBean = new PointValidationBean();
    }

    @Test
    @DisplayName("Валидная точка - должен вернуть true")
    void validPointShouldReturnTrue() {
        Point point = new Point(2.5, 3.0, 2.0);
        assertTrue(validationBean.validateForm(point));
    }

    @Test
    @DisplayName("X на нижней границе - должен вернуть true")
    void xAtMinBoundaryShouldReturnTrue() {
        Point point = new Point(-3.0, 0.0, 2.0);
        assertTrue(validationBean.validateForm(point));
    }

    @Test
    @DisplayName("X на верхней границе - должен вернуть true")
    void xAtMaxBoundaryShouldReturnTrue() {
        Point point = new Point(5.0, 0.0, 2.0);
        assertTrue(validationBean.validateForm(point));
    }

    @Test
    @DisplayName("X дробное значение - должен вернуть true")
    void xFractionalShouldReturnTrue() {
        Point point = new Point(1.75, 2.0, 2.0);
        assertTrue(validationBean.validateForm(point));
    }

    @Test
    @DisplayName("X меньше нижней границы - должен вернуть false")
    void xLessThanMinShouldReturnFalse() {
        Point point = new Point(-3.1, 0.0, 2.0);
        assertFalse(validationBean.validateForm(point));
    }

    @Test
    @DisplayName("X больше верхней границы - должен вернуть false")
    void xGreaterThanMaxShouldReturnFalse() {
        Point point = new Point(5.1, 0.0, 2.0);
        assertFalse(validationBean.validateForm(point));
    }

    @Test
    @DisplayName("Y = -3 - должен вернуть true")
    void yMinus3ShouldReturnTrue() {
        Point point = new Point(0.0, -3.0, 2.0);
        assertTrue(validationBean.validateForm(point));
    }

    @Test
    @DisplayName("Y = -2 - должен вернуть true")
    void yMinus2ShouldReturnTrue() {
        Point point = new Point(0.0, -2.0, 2.0);
        assertTrue(validationBean.validateForm(point));
    }

    @Test
    @DisplayName("Y = -1 - должен вернуть true")
    void yMinus1ShouldReturnTrue() {
        Point point = new Point(0.0, -1.0, 2.0);
        assertTrue(validationBean.validateForm(point));
    }

    @Test
    @DisplayName("Y = 0 - должен вернуть true")
    void yZeroShouldReturnTrue() {
        Point point = new Point(0.0, 0.0, 2.0);
        assertTrue(validationBean.validateForm(point));
    }

    @Test
    @DisplayName("Y = 1 - должен вернуть true")
    void yOneShouldReturnTrue() {
        Point point = new Point(0.0, 1.0, 2.0);
        assertTrue(validationBean.validateForm(point));
    }

    @Test
    @DisplayName("Y = 2 - должен вернуть true")
    void yTwoShouldReturnTrue() {
        Point point = new Point(0.0, 2.0, 2.0);
        assertTrue(validationBean.validateForm(point));
    }

    @Test
    @DisplayName("Y = 3 - должен вернуть true")
    void yThreeShouldReturnTrue() {
        Point point = new Point(0.0, 3.0, 2.0);
        assertTrue(validationBean.validateForm(point));
    }

    @Test
    @DisplayName("Y = 4 - должен вернуть true")
    void yFourShouldReturnTrue() {
        Point point = new Point(0.0, 4.0, 2.0);
        assertTrue(validationBean.validateForm(point));
    }

    @Test
    @DisplayName("Y = 5 - должен вернуть true")
    void yFiveShouldReturnTrue() {
        Point point = new Point(0.0, 5.0, 2.0);
        assertTrue(validationBean.validateForm(point));
    }

    @Test
    @DisplayName("Y = -4 - должен вернуть false")
    void yLessThanMinShouldReturnFalse() {
        Point point = new Point(0.0, -4.0, 2.0);
        assertFalse(validationBean.validateForm(point));
    }

    @Test
    @DisplayName("Y = 6 - должен вернуть false")
    void yGreaterThanMaxShouldReturnFalse() {
        Point point = new Point(0.0, 6.0, 2.0);
        assertFalse(validationBean.validateForm(point));
    }

    @Test
    @DisplayName("Y дробное значение - должен вернуть false")
    void yFractionalShouldReturnFalse() {
        Point point = new Point(0.0, 2.5, 2.0);
        assertFalse(validationBean.validateForm(point));
    }

    @Test
    @DisplayName("R = 1 - должен вернуть true")
    void rOneShouldReturnTrue() {
        Point point = new Point(0.0, 0.0, 1.0);
        assertTrue(validationBean.validateForm(point));
    }

    @Test
    @DisplayName("R = 1.5 - должен вернуть true")
    void rOnePointFiveShouldReturnTrue() {
        Point point = new Point(0.0, 0.0, 1.5);
        assertTrue(validationBean.validateForm(point));
    }

    @Test
    @DisplayName("R = 2 - должен вернуть true")
    void rTwoShouldReturnTrue() {
        Point point = new Point(0.0, 0.0, 2.0);
        assertTrue(validationBean.validateForm(point));
    }

    @Test
    @DisplayName("R = 2.5 - должен вернуть true")
    void rTwoPointFiveShouldReturnTrue() {
        Point point = new Point(0.0, 0.0, 2.5);
        assertTrue(validationBean.validateForm(point));
    }

    @Test
    @DisplayName("R = 3 - должен вернуть true")
    void rThreeShouldReturnTrue() {
        Point point = new Point(0.0, 0.0, 3.0);
        assertTrue(validationBean.validateForm(point));
    }

    @Test
    @DisplayName("R = 0.5 - должен вернуть false")
    void rLessThanMinShouldReturnFalse() {
        Point point = new Point(0.0, 0.0, 0.5);
        assertFalse(validationBean.validateForm(point));
    }

    @Test
    @DisplayName("R = 3.5 - должен вернуть true")
    void rGreaterThanMaxShouldReturnFalse() {
        Point point = new Point(0.0, 0.0, 3.5);
        assertFalse(validationBean.validateForm(point));
    }

    @ParameterizedTest
    @CsvSource({
            "-3.0, 0, 1.0, true",
            "5.0, 0, 1.0, true",
            "0.0, -3, 1.0, true",
            "0.0, 5, 1.0, true",
            "0.0, 0, 1.0, true",
            "0.0, 0, 3.0, true",
            "2.5, 3, 2.0, true",
            "-1.5, -2, 1.5, true",
            "3.7, 4, 2.5, true",

            "-3.1, 0, 1.0, false",
            "5.1, 0, 1.0, false",

            "0.0, -4, 1.0, false",
            "0.0, 6, 1.0, false",

            "0.0, 2.5, 1.0, false",
            "0.0, 3.7, 1.0, false",

            "0.0, 0, 0.5, false",
            "0.0, 0, 3.5, false",
            "0.0, 0, 4.0, false"
    })
    @DisplayName("Параметризованный тест валидации формы")
    void validateForm_shouldReturnExpectedResult(double x, double y, double r, boolean expected) {
        Point point = new Point(x, y, r);
        assertEquals(expected, validationBean.validateForm(point));
    }

    @ParameterizedTest
    @CsvSource({
            "0.0, 0.0, 1.0, true",
            "-5.0, 0.0, 1.0, true",
            "5.0, 0.0, 1.0, true",
            "0.0, -5.0, 1.0, true",
            "0.0, 5.0, 1.0, true",
            "-6.0, 0.0, 1.0, true",
            "6.0, 0.0, 1.0, true",
            "0.0, -6.0, 1.0, true",
            "0.0, 6.0, 1.0, true",
            "-3.5, 2.5, 2.0, true",
            "4.2, -1.8, 1.5, true",

            "-6.1, 0.0, 1.0, false",
            "6.1, 0.0, 1.0, false",

            "0.0, -6.1, 1.0, false",
            "0.0, 6.1, 1.0, false",

            "0.0, 0.0, 0.5, false",
            "0.0, 0.0, 3.5, false"
    })
    @DisplayName("Параметризованный тест валидации клика по графику")
    void validateGraphClick_shouldReturnExpectedResult(double x, double y, double r, boolean expected) {
        Point point = new Point(x, y, r);
        assertEquals(expected, validationBean.validateGraphClick(point, -6, 6, -6, 6));
    }
}