package weblab.shapes.factories;

import weblab.shapes.templates.Rectangle;
import weblab.shapes.templates.Shape;

/**
 * Фабрика для создания прямоугольников с масштабированием относительно радиуса R.
 *
 * <p>Прямоугольник создаётся с шириной = {@code widthRatio * R}
 * и высотой = {@code heightRatio * R}. Это позволяет использовать
 * один и тот же тип фигуры с разными пропорциями.</p>
 *
 * <p>Примеры использования:
 * <ul>
 *   <li>Квадрат R×R: new RectangleFactory(1.0, 1.0)</li>
 *   <li>Прямоугольник 2R×R: new RectangleFactory(2.0, 1.0)</li>
 *   <li>Прямоугольник R×2R: new RectangleFactory(1.0, 2.0)</li>
 * </ul>
 * </p>
 *
 * @author Vladislav Dyadev
 * @version 1.0
 * @see ShapeFactory
 * @see Rectangle
 */
public class RectangleFactory implements ShapeFactory {
    private final double widthRatio;
    private final double heightRatio;

    /**
     * Конструктор фабрики прямоугольников.
     *
     * @param widthRatio  коэффициент масштабирования ширины (относительно R)
     * @param heightRatio коэффициент масштабирования высоты (относительно R)
     */
    public RectangleFactory(double widthRatio, double heightRatio) {
        this.widthRatio = widthRatio;
        this.heightRatio = heightRatio;
    }

    /**
     * Создаёт прямоугольник с размерами, пропорциональными заданному радиусу R.
     *
     * @param r значение радиуса (определяет масштаб фигуры)
     * @return новый экземпляр прямоугольника с шириной {@code widthRatio * R}
     *         и высотой {@code heightRatio * R}
     */
    @Override
    public Shape create(double r) {
        return new Rectangle(widthRatio * r, heightRatio * r);
    }
}