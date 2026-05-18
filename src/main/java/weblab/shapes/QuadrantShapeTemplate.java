package weblab.shapes;

import weblab.shapes.factories.ShapeFactory;

/**
 * Шаблон для создания фигуры в определённом квадранте.
 *
 * <p>Этот класс служит конфигурационной единицей для {@link weblab.services.AreaCheckService}.
 * Он связывает фабрику, создающую фигуру определённого типа, с номером квадранта,
 * в котором эта фигура должна быть размещена.
 *
 * <p>Пример использования:
 * <pre>
 * new QuadrantShapeTemplate(new TriangleFactory(1.0, 0.5), 1)
 * </pre>
 * Это означает: создать треугольник в квадранте 1, где основание = R, высота = R/2.
 *
 * @author Vladislav Dyadev
 * @version 1.0
 * @see ShapeFactory
 * @see weblab.shapes.templates.QuadrantShape
 * @see weblab.services.AreaCheckService
 */
public class QuadrantShapeTemplate {
    private final ShapeFactory factory;
    private final int quadrant;

    /**
     * Конструктор шаблона фигуры по квадранту.
     *
     * @param factory  фабрика, создающая фигуру (определяет тип фигуры и пропорции)
     * @param quadrant номер квадранта (1, 2, 3 или 4), в котором размещается фигура
     */
    public QuadrantShapeTemplate(ShapeFactory factory, int quadrant) {
        this.factory = factory;
        this.quadrant = quadrant;
    }

    /**
     * Возвращает фабрику для создания фигуры.
     *
     * @return фабрика фигур
     */
    public ShapeFactory getFactory() {
        return factory;
    }

    /**
     * Возвращает номер квадранта для размещения фигуры.
     *
     * @return номер квадранта (1, 2, 3 или 4)
     */
    public int getQuadrant() {
        return quadrant;
    }
}