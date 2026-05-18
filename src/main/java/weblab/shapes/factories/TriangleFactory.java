package weblab.shapes.factories;

import weblab.shapes.templates.Shape;
import weblab.shapes.templates.Triangle;

/**
 * Фабрика для создания треугольников с масштабированием относительно радиуса R.
 *
 * <p>Треугольник создаётся с основанием = {@code baseRatio * R}
 * и высотой = {@code heightRatio * R}. Это позволяет использовать
 * один и тот же тип фигуры с разными пропорциями.
 *
 * <p>Примеры использования:
 * <ul>
 *   <li>Треугольник 1:1 (равнобедренный): new TriangleFactory(1.0, 1.0)</li>
 *   <li>Треугольник 2:1 (основание в 2 раза больше высоты): new TriangleFactory(2.0, 1.0)</li>
 *   <li>Треугольник 1:2 (высота в 2 раза больше основания): new TriangleFactory(1.0, 2.0)</li>
 * </ul>
 *
 * @author Vladislav Dyadev
 * @version 1.0
 * @see ShapeFactory
 * @see Triangle
 */
public class TriangleFactory implements ShapeFactory {
    /**
     * Коэффициент масштабирования основания треугольника относительно R.
     */
    private final double baseRatio;

    /**
     * Коэффициент масштабирования высоты треугольника относительно R.
     */
    private final double heightRatio;

    /**
     * Конструктор фабрики треугольников.
     *
     * @param baseRatio   коэффициент масштабирования основания (относительно R)
     * @param heightRatio коэффициент масштабирования высоты (относительно R)
     */
    public TriangleFactory(double baseRatio, double heightRatio) {
        this.baseRatio = baseRatio;
        this.heightRatio = heightRatio;
    }

    /**
     * Создаёт треугольник с размерами, пропорциональными заданному радиусу R.
     *
     * @param r значение радиуса (определяет масштаб фигуры)
     * @return новый экземпляр треугольника с основанием {@code baseRatio * R}
     *         и высотой {@code heightRatio * R}
     */
    @Override
    public Shape create(double r) {
        return new Triangle(baseRatio * r, heightRatio * r);
    }
}