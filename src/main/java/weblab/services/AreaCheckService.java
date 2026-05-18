package weblab.services;

import weblab.models.CheckResult;
import weblab.models.Point;
import weblab.shapes.QuadrantShapeTemplate;
import weblab.shapes.templates.QuadrantShape;
import weblab.shapes.templates.Shape;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для проверки попадания списка точек в область.
 *
 * <p>Основной сервис приложения, который координирует процесс проверки точек.
 * Для каждой точки создаёт соответствующие фигуры через фабрики и шаблоны,
 * затем использует {@link HitChecker} для определения факта попадания.
 *
 * <p>Алгоритм работы:
 * <ol>
 *   <li>Для каждой точки создаются фигуры во всех заданных квадрантах</li>
 *   <li>Размеры фигур масштабируются в зависимости от значения R точки</li>
 *   <li>Создаётся {@link HitChecker} с набором фигур для точки</li>
 *   <li>Выполняется проверка попадания</li>
 *   <li>Результат упаковывается в {@link CheckResult}</li>
 * </ol>
 *
 *
 * @author Vladislav Dyadev
 * @version 1.0
 * @see QuadrantShapeTemplate
 * @see HitChecker
 * @see CheckResult
 */
public class AreaCheckService {
    private final List<QuadrantShapeTemplate> shapeTemplates;

    /**
     * Конструктор сервиса проверки области.
     *
     * @param shapeTemplates список шаблонов фигур по квадрантам
     *                       (определяет, какие фигуры и в каких квадрантах будут проверяться)
     */
    public AreaCheckService(List<QuadrantShapeTemplate> shapeTemplates) {
        this.shapeTemplates = shapeTemplates;
    }

    /**
     * Проверяет список точек на попадание в область.
     *
     * <p>Для каждой точки создаются фигуры с масштабированием относительно значения R,
     * затем выполняется проверка попадания. Результаты возвращаются в том же порядке,
     * в котором были переданы точки.
     *
     * @param points список точек для проверки (может быть пустым, но не {@code null})
     * @return список результатов проверки для каждой точки
     */
    public List<CheckResult> checkPoints(List<Point> points) {
        List<CheckResult> results = new ArrayList<>();
        for (Point p : points) {
            List<Shape> shapesForPoint = new ArrayList<>();
            for (QuadrantShapeTemplate template : shapeTemplates) {
                shapesForPoint.add(new QuadrantShape(template.getFactory().create(p.r()), template.getQuadrant()));
            }
            HitChecker checker = new HitChecker(shapesForPoint);
            results.add(new CheckResult(p, checker.isHit(p)));
        }
        return results;
    }
}