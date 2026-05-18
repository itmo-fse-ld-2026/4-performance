package weblab.services;

import weblab.models.Point;
import weblab.shapes.templates.Shape;

import java.util.List;

/**
 * Класс для проверки попадания точки в набор фигур.
 *
 * <p>Принимает список фигур и проверяет, содержится ли точка хотя бы в одной из них.
 * Используется внутри {@link AreaCheckService} после того, как для точки
 * созданы все необходимые фигуры с учётом квадрантов и масштабирования.
 *
 * <p>Проверка выполняется с помощью метода {@link Shape#contains(Point)},
 * который каждая фигура реализует по-своему. Точка считается попавшей,
 * если хотя бы одна фигура из списка вернёт {@code true}.
 *
 * @author Vladislav Dyadev
 * @version 1.0
 * @see Shape
 * @see AreaCheckService
 */
public class HitChecker {
    /**
     * Список фигур, по которым будет проверяться точка.
     */
    private final List<Shape> shapes;

    /**
     * Конструктор проверщика попаданий.
     *
     * @param shapes список фигур, по которым будет проверяться точка
     *               (может быть пустым — тогда точка никогда не попадёт)
     */
    public HitChecker(List<Shape> shapes) {
        this.shapes = shapes;
    }

    /**
     * Проверяет, попадает ли точка хотя бы в одну из фигур.
     *
     * <p>Использует Stream API для последовательной проверки всех фигур.
     * Как только находится фигура, содержащая точку, проверка прекращается
     * и возвращается {@code true}.
     *
     * @param p проверяемая точка
     * @return {@code true}, если точка содержится хотя бы в одной фигуре,
     *         {@code false} в противном случае
     */
    public boolean isHit(Point p) {
        return shapes.stream().anyMatch(s -> s.contains(p));
    }
}