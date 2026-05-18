package weblab.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import weblab.models.Point;
import weblab.shapes.templates.QuarterCircle;
import weblab.shapes.templates.Rectangle;
import weblab.shapes.templates.Triangle;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты попадания точки по списку фигур")
public class HitCheckerTest {
    @Test
    @DisplayName("Одна фигура, точка внутри - должен вернуть true")
    void singleShapeWithPointInsideShouldReturnTrue() {
        Rectangle rect = new Rectangle(5.0, 3.0);
        HitChecker checker = new HitChecker(List.of(rect));
        Point point = new Point(2.0, 1.0, 1.0);
        assertTrue(checker.isHit(point));
    }

    @Test
    @DisplayName("Одна фигура, точка снаружи - должен вернуть false")
    void singleShapeWithPointOutsideShouldReturnFalse() {
        Rectangle rect = new Rectangle(5.0, 3.0);
        HitChecker checker = new HitChecker(List.of(rect));
        Point point = new Point(10.0, 10.0, 1.0);
        assertFalse(checker.isHit(point));
    }

    @Test
    @DisplayName("Пустой список фигур - должен вернуть false")
    void emptyShapesListShouldReturnFalse() {
        HitChecker checker = new HitChecker(List.of());
        Point point = new Point(1.0, 1.0, 1.0);
        assertFalse(checker.isHit(point));
    }

    @Test
    @DisplayName("Две фигуры, точка внутри первой - должен вернуть true")
    void twoShapesWithPointInsideFirstShouldReturnTrue() {
        Rectangle rect1 = new Rectangle(2.0, 2.0);
        Rectangle rect2 = new Rectangle(5.0, 5.0);
        HitChecker checker = new HitChecker(List.of(rect1, rect2));
        Point point = new Point(1.0, 1.0, 1.0);
        assertTrue(checker.isHit(point));
    }

    @Test
    @DisplayName("Две фигуры, точка внутри второй - должен вернуть true")
    void twoShapesWithPointInsideSecondShouldReturnTrue() {
        Rectangle rect1 = new Rectangle(1.0, 1.0);
        Rectangle rect2 = new Rectangle(5.0, 5.0);
        HitChecker checker = new HitChecker(List.of(rect1, rect2));
        Point point = new Point(3.0, 3.0, 1.0);
        assertTrue(checker.isHit(point));
    }

    @Test
    @DisplayName("Две фигуры, точка снаружи обеих - должен вернуть false")
    void twoShapesWithPointOutsideBothShouldReturnFalse() {
        Rectangle rect1 = new Rectangle(1.0, 1.0);
        Rectangle rect2 = new Rectangle(2.0, 2.0);
        HitChecker checker = new HitChecker(List.of(rect1, rect2));
        Point point = new Point(10.0, 10.0, 1.0);
        assertFalse(checker.isHit(point));
    }

    @Test
    @DisplayName("Прямоугольник и треугольник, точка в прямоугольнике - должен вернуть true")
    void rectangleAndTriangleWithPointInRectangleShouldReturnTrue() {
        Rectangle rect = new Rectangle(5.0, 3.0);
        Triangle triangle = new Triangle(4.0, 4.0);
        HitChecker checker = new HitChecker(List.of(rect, triangle));
        Point point = new Point(2.0, 1.0, 1.0);
        assertTrue(checker.isHit(point));
    }

    @Test
    @DisplayName("Прямоугольник и треугольник, точка в треугольнике - должен вернуть true")
    void rectangleAndTriangleWithPointInTriangleShouldReturnTrue() {
        Rectangle rect = new Rectangle(1.0, 1.0);
        Triangle triangle = new Triangle(4.0, 4.0);
        HitChecker checker = new HitChecker(List.of(rect, triangle));
        Point point = new Point(1.0, 1.0, 4.0);
        assertTrue(checker.isHit(point));
    }

    @Test
    @DisplayName("Прямоугольник, треугольник и четверть круга - точка в круге - должен вернуть true")
    void allThreeShapes_pointInCircleShouldReturnTrue() {
        Rectangle rect = new Rectangle(1.0, 1.0);
        Triangle triangle = new Triangle(2.0, 2.0);
        QuarterCircle circle = new QuarterCircle(5.0);
        HitChecker checker = new HitChecker(List.of(rect, triangle, circle));
        Point point = new Point(3.0, 3.0, 1.0);
        assertTrue(checker.isHit(point));
    }

    @Test
    @DisplayName("Прямоугольник, треугольник и четверть круга - точка нигде - должен вернуть false")
    void allThreeShapes_pointNowhereShouldReturnFalse() {
        Rectangle rect = new Rectangle(1.0, 1.0);
        Triangle triangle = new Triangle(2.0, 2.0);
        QuarterCircle circle = new QuarterCircle(5.0);
        HitChecker checker = new HitChecker(List.of(rect, triangle, circle));
        Point point = new Point(10.0, 10.0, 1.0);
        assertFalse(checker.isHit(point));
    }

    // ==================== ДОПОЛНИТЕЛЬНЫЕ ТЕСТЫ ====================

    @Test
    @DisplayName("Точка на границе фигуры - должен вернуть true")
    void pointOnBoundaryShouldReturnTrue() {
        Rectangle rect = new Rectangle(5.0, 3.0);
        HitChecker checker = new HitChecker(List.of(rect));
        Point point = new Point(5.0, 2.0, 1.0);
        assertTrue(checker.isHit(point));
    }

    @Test
    @DisplayName("Точка (0,0) - должна попадать в любую фигуру в 1-м квадранте - должен вернуть true")
    void originPointShouldReturnTrue() {
        Rectangle rect = new Rectangle(5.0, 3.0);
        Triangle triangle = new Triangle(4.0, 4.0);
        QuarterCircle circle = new QuarterCircle(5.0);
        HitChecker checker = new HitChecker(List.of(rect, triangle, circle));
        Point point = new Point(0.0, 0.0, 1.0);
        assertTrue(checker.isHit(point));
    }

    @Test
    @DisplayName("Отрицательные координаты - точка не должна попасть (если фигура только в 1-м квадранте) - должен вернуть false")
    void negativeCoordinatesShouldReturnFalse() {
        Rectangle rect = new Rectangle(5.0, 3.0);
        HitChecker checker = new HitChecker(List.of(rect));
        Point point = new Point(-2.0, -2.0, 1.0);
        assertFalse(checker.isHit(point));
    }

    @Test
    @DisplayName("Точка с большими координатами - должен вернуть false")
    void veryLargePointShouldReturnFalse() {
        Rectangle rect = new Rectangle(5.0, 3.0);
        HitChecker checker = new HitChecker(List.of(rect));
        Point point = new Point(1000.0, 1000.0, 1.0);
        assertFalse(checker.isHit(point));
    }
}
