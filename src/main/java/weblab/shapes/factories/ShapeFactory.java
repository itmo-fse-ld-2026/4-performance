package weblab.shapes.factories;

import weblab.shapes.templates.Shape;

/**
 * Интерфейс фабрики для создания геометрических фигур.
 *
 * <p>Фабрика создаёт фигуры, размеры которых масштабируются относительно
 * значения радиуса R. Это позволяет использовать одни и те же типы фигур
 * с разными пропорциями и размерами в зависимости от переданного R.
 *
 * <p>Реализации интерфейса:
 * <ul>
 *   <li>{@link RectangleFactory} — создаёт прямоугольники</li>
 *   <li>{@link TriangleFactory} — создаёт треугольники</li>
 *   <li>{@link QuarterCircleFactory} — создаёт четверти круга</li>
 * </ul>
 *
 *
 * <p>Фабрики используются в {@link weblab.shapes.QuadrantShapeTemplate}
 * для определения типа фигуры и её пропорций в конкретном квадранте.
 *
 * @author Vladislav Dyadev
 * @version 1.0
 * @see Shape
 * @see RectangleFactory
 * @see TriangleFactory
 * @see QuarterCircleFactory
 * @see weblab.shapes.QuadrantShapeTemplate
 */
public interface ShapeFactory {
    /**
     * Создаёт экземпляр фигуры с масштабированием относительно радиуса R.
     *
     * @param r значение радиуса (определяет масштаб фигуры)
     * @return новая фигура, размеры которой пропорциональны R
     */
    Shape create(double r);
}