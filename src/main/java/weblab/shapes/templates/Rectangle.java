package weblab.shapes.templates;

import weblab.models.Point;

/**
 * Фигура, представляющая прямоугольник в первом квадранте.
 *
 * <p>Прямоугольник расположен в первом квадранте системы координат
 * (x ≥ 0, y ≥ 0) с нижним левым углом в начале координат,
 * шириной {@code width} по оси X и высотой {@code height} по оси Y.
 *
 * <p>Точка считается попавшей, если она находится в первом квадранте
 * и её координаты не превышают ширины и высоты прямоугольника.
 * Границы прямоугольника считаются попаданием.
 *
 * <p>Используется в комбинации с {@link QuadrantShape} для размещения
 * в других квадрантах (например, в квадранте 2, где x отрицательный).
 *
 * @author Vladislav Dyadev
 * @version 1.0
 * @see Shape
 * @see QuadrantShape
 */
public class Rectangle implements Shape {
    private final double width;
    private final double height;

    /**
     * Конструктор прямоугольника.
     *
     * @param width  ширина прямоугольника (положительное число, откладывается по оси X)
     * @param height высота прямоугольника (положительное число, откладывается по оси Y)
     */
    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Проверяет, находится ли точка внутри прямоугольника.
     *
     * <p>Условия попадания:
     * <ul>
     *   <li>X координата неотрицательна (x ≥ 0)</li>
     *   <li>Y координата неотрицательна (y ≥ 0)</li>
     *   <li>X координата не превышает ширину (x ≤ width)</li>
     *   <li>Y координата не превышает высоту (y ≤ height)</li>
     * </ul>
     *
     *
     * @param p проверяемая точка
     * @return {@code true}, если точка находится внутри или на границе прямоугольника,
     *         {@code false} в противном случае
     */
    @Override
    public boolean contains(Point p) {
        return p.x() >= 0 && p.y() >= 0 && p.x() <= width && p.y() <= height;
    }
}