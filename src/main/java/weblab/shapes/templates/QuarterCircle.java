package weblab.shapes.templates;

import weblab.models.Point;

/**
 * Фигура, представляющая четверть круга в первом квадранте.
 *
 * <p>Четверть круга расположена в первом квадранте системы координат
 * (x ≥ 0, y ≥ 0) и ограничена дугой радиуса {@code radius}.</p>
 *
 * <p>Точка считается попавшей, если она находится в первом квадранте
 * и расстояние от начала координат не превышает радиуса.
 * Граница (дуга) считается попаданием.</p>
 *
 * <p>Используется в комбинации с {@link QuadrantShape} для размещения
 * в других квадрантах (например, в квадранте 4, где y отрицательный).</p>
 *
 * @author Vladislav Dyadev
 * @version 1.0
 * @see Shape
 * @see QuadrantShape
 */
public class QuarterCircle implements Shape {
    private final double radius;

    /**
     * Конструктор четверти круга.
     *
     * @param radius радиус круга (положительное число)
     */
    public QuarterCircle(double radius) {
        this.radius = radius;
    }

    /**
     * Проверяет, находится ли точка внутри четверти круга.
     *
     * <p>Условия попадания:
     * <ul>
     *   <li>X координата неотрицательна (x ≥ 0)</li>
     *   <li>Y координата неотрицательна (y ≥ 0)</li>
     *   <li>Расстояние от начала координат не превышает радиус: x² + y² ≤ radius²</li>
     * </ul>
     * </p>
     *
     * @param p проверяемая точка
     * @return {@code true}, если точка находится внутри или на границе четверти круга,
     *         {@code false} в противном случае
     */
    @Override
    public boolean contains(Point p) {
        return p.x() >= 0 && p.y() >= 0 && (p.x() * p.x() + p.y() * p.y() <= radius * radius);
    }
}