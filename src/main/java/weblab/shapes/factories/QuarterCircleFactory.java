package weblab.shapes.factories;

import weblab.shapes.templates.QuarterCircle;
import weblab.shapes.templates.Shape;

/**
 * Фабрика для создания четвертей круга с масштабированием относительно радиуса R.
 *
 * <p>Четверть круга создаётся с радиусом = {@code radiusRatio * R}.
 * Это позволяет использовать один и тот же тип фигуры с разными пропорциями.</p>
 *
 * <p>Примеры использования:
 * <ul>
 *   <li>Четверть круга с радиусом R: new QuarterCircleFactory(1.0)</li>
 *   <li>Четверть круга с радиусом R/2: new QuarterCircleFactory(0.5)</li>
 *   <li>Четверть круга с радиусом 2R: new QuarterCircleFactory(2.0)</li>
 * </ul>
 * </p>
 *
 * <p>В текущей конфигурации приложения используется фабрика с {@code radiusRatio = 0.5},
 * что соответствует четверти круга с радиусом R/2.</p>
 *
 * @author Vladislav Dyadev
 * @version 1.0
 * @see ShapeFactory
 * @see QuarterCircle
 */
public class QuarterCircleFactory implements ShapeFactory {
    private final double radiusRatio;

    /**
     * Конструктор фабрики четвертей круга.
     *
     * @param radiusRatio коэффициент масштабирования радиуса (относительно R)
     */
    public QuarterCircleFactory(double radiusRatio) {
        this.radiusRatio = radiusRatio;
    }

    /**
     * Создаёт четверть круга с радиусом, пропорциональным заданному значению R.
     *
     * @param r значение радиуса (определяет масштаб фигуры)
     * @return новый экземпляр четверти круга с радиусом {@code radiusRatio * R}
     */
    @Override
    public Shape create(double r) {
        return new QuarterCircle(radiusRatio * r);
    }
}