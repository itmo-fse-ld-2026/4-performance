package weblab.shapes.templates;

import weblab.models.Point;

/**
 * Интерфейс, представляющий геометрическую фигуру для проверки попадания точки.
 *
 * <p>Все фигуры в приложении реализуют этот интерфейс и должны определять
 * логику проверки принадлежности точки фигуре.
 *
 * <p>Фигуры предназначены для работы в первом квадранте системы координат
 * (x ≥ 0, y ≥ 0). Для размещения фигур в других квадрантах используется
 * декоратор {@link QuadrantShape}, который преобразует координаты точки
 * перед вызовом метода {@link #contains(Point)}.
 *
 * <p>Реализации интерфейса:
 * <ul>
 *   <li>{@link Triangle} — треугольная область</li>
 *   <li>{@link Rectangle} — прямоугольная область</li>
 *   <li>{@link QuarterCircle} — четверть круга</li>
 *   <li>{@link QuadrantShape} — декоратор для трансформации координат</li>
 * </ul>
 *
 *
 * @author Vladislav Dyadev
 * @version 1.0
 * @see Point
 * @see QuadrantShape
 * @see Triangle
 * @see Rectangle
 * @see QuarterCircle
 */
public interface Shape {
    /**
     * Проверяет, содержит ли фигура указанную точку.
     *
     * <p>Точки на границе фигуры считаются попавшими.
     *
     * @param p проверяемая точка
     * @return {@code true}, если точка находится внутри или на границе фигуры,
     *         {@code false} в противном случае
     */
    boolean contains(Point p);
}