package weblab.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import weblab.models.CheckResult;
import weblab.models.Point;
import weblab.shapes.QuadrantShapeTemplate;
import weblab.shapes.factories.QuarterCircleFactory;
import weblab.shapes.factories.RectangleFactory;
import weblab.shapes.factories.TriangleFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты сервиса проверки областей попадания")
public class AreaCheckServiceTest {
    private AreaCheckService service;

    @BeforeEach
    void setUp() {
        service = new AreaCheckService(List.of(
                new QuadrantShapeTemplate(new TriangleFactory(1.0, 0.5), 1),
                new QuadrantShapeTemplate(new RectangleFactory(1.0, 1.0), 2),
                new QuadrantShapeTemplate(new QuarterCircleFactory(0.5), 4)
        ));
    }

    @Test
    @DisplayName("Квадрант 1: точка внутри треугольника - должнен вернуть true")
    void quadrant1PointInsideTriangleShouldReturnTrue() {
        Point point = new Point(0.5, 0.25, 1.0);
        List<CheckResult> results = service.checkPoints(List.of(point));
        assertTrue(results.get(0).hit());
    }

    @Test
    @DisplayName("Квадрант 1: точка на границе треугольника - должен вернуть true")
    void quadrant1PointOnTriangleBoundaryShouldReturnTrue() {
        Point point = new Point(0.5, 0.25, 1.0);
        List<CheckResult> results = service.checkPoints(List.of(point));
        assertTrue(results.get(0).hit());
    }

    @Test
    @DisplayName("Квадрант 1: точка выше гипотенузы - должен вернуть false")
    void quadrant1PointAboveHypotenuseShouldReturnFalse() {
        Point point = new Point(0.5, 0.4, 1.0);
        List<CheckResult> results = service.checkPoints(List.of(point));
        assertFalse(results.get(0).hit());
    }

    @Test
    @DisplayName("Квадрант 1: точка вне треугольника по x - должен вернуть false")
    void quadrant1PointXOutOfTriangleShouldReturnFalse() {
        Point point = new Point(0.9, 0.1, 1.0);
        List<CheckResult> results = service.checkPoints(List.of(point));
        assertFalse(results.get(0).hit());
    }

    @Test
    @DisplayName("Квадрант 2: точка внутри прямоугольника - должен вернуть true")
    void quadrant2PointInsideRectangleShouldReturnTrue() {
        Point point = new Point(-0.5, 0.5, 1.0);
        List<CheckResult> results = service.checkPoints(List.of(point));
        assertTrue(results.get(0).hit());
    }

    @Test
    @DisplayName("Квадрант 2: точка на границе прямоугольника - должен вернуть true")
    void quadrant2PointOnRectangleBoundaryShouldReturnTrue() {
        Point point = new Point(-1.0, 0.5, 1.0);
        List<CheckResult> results = service.checkPoints(List.of(point));
        assertTrue(results.get(0).hit());
    }

    @Test
    @DisplayName("Квадрант 2: точка вне прямоугольника - должен вернуть false")
    void quadrant2PointOutsideRectangleShouldReturnFalse() {
        Point point = new Point(-1.5, 0.5, 1.0);
        List<CheckResult> results = service.checkPoints(List.of(point));
        assertFalse(results.get(0).hit());
    }

    @Test
    @DisplayName("Квадрант 4: точка внутри четверти круга - должен вернуть true")
    void quadrant4PointInsideQuarterCircleShouldReturnTrue() {
        Point point = new Point(0.3, -0.3, 1.0);
        List<CheckResult> results = service.checkPoints(List.of(point));
        assertTrue(results.get(0).hit());
    }

    @Test
    @DisplayName("Квадрант 4: точка на дуге - должен вернуть true")
    void quadrant4PointOnArcShouldReturnTrue() {
        Point point = new Point(0.35, -0.35, 1.0);
        List<CheckResult> results = service.checkPoints(List.of(point));
        assertTrue(results.get(0).hit());
    }

    @Test
    @DisplayName("Квадрант 4: точка за дугой - должен вернуть false")
    void quadrant4PointOutsideArcShouldReturnFalse() {
        Point point = new Point(0.4, -0.4, 1.0);
        List<CheckResult> results = service.checkPoints(List.of(point));
        assertFalse(results.get(0).hit());
    }

    @Test
    @DisplayName("Квадрант 3: нет фигуры - должен вернуть false")
    void quadrant3NoShapeShouldReturnFalse() {
        Point point = new Point(-0.5, -0.5, 1.0);
        List<CheckResult> results = service.checkPoints(List.of(point));
        assertFalse(results.get(0).hit());
    }

    @Test
    @DisplayName("Точки с разными R должны корректно масштабироваться - должен вернуть true")
    void pointsWithDifferentRShouldReturnTrue() {
        Point pointR1 = new Point(0.5, 0.25, 1.0);
        Point pointR2 = new Point(1.0, 0.5, 2.0);
        Point pointR3 = new Point(1.5, 0.75, 3.0);

        List<CheckResult> results = service.checkPoints(List.of(pointR1, pointR2, pointR3));

        assertTrue(results.get(0).hit(), "R=1");
        assertTrue(results.get(1).hit(), "R=2");
        assertTrue(results.get(2).hit(), "R=3");
    }

    @Test
    @DisplayName("Несколько точек - должен вернуть true")
    void multiplePointsShouldReturnTrue() {
        List<Point> points = List.of(
                new Point(0.5, 0.25, 1.0),
                new Point(2.0, 2.0, 1.0),
                new Point(-0.5, 0.5, 1.0),
                new Point(0.3, -0.3, 1.0),
                new Point(-0.5, -0.5, 1.0)
        );

        List<CheckResult> results = service.checkPoints(points);

        assertTrue(results.get(0).hit(), "Точка в треугольнике");
        assertFalse(results.get(1).hit(), "Точка вне области");
        assertTrue(results.get(2).hit(), "Точка в прямоугольнике");
        assertTrue(results.get(3).hit(), "Точка в четверти круга");
        assertFalse(results.get(4).hit(), "Точка в квадранте 3");
    }

    @Test
    @DisplayName("Пустой список точек - должен вернуть пустой список")
    void emptyPointsListShouldReturnEmptyList() {
        List<CheckResult> results = service.checkPoints(List.of());
        assertTrue(results.isEmpty());
    }

    @Test
    @DisplayName("Точка в начале координат - должен вернуть true")
    void originPointShouldReturnTrue() {
        Point point = new Point(0.0, 0.0, 1.0);
        List<CheckResult> results = service.checkPoints(List.of(point));
        assertTrue(results.get(0).hit());
    }
}
