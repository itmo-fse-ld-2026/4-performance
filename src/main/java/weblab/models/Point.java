package weblab.models;

import java.io.Serial;
import java.io.Serializable;

/**
 * Запись, представляющая точку для проверки попадания в область.
 *
 * <p>Содержит координаты точки X, Y и значение радиуса R, которое определяет
 * масштаб области проверки. Реализует {@link Serializable} для возможности
 * сохранения в HTTP-сессии и передачи между компонентами.</p>
 *
 * <p>Валидация допустимых значений выполняется в {@link weblab.beans.PointValidationBean}:
 * <ul>
 *   <li>X: от -3 до 5 (для формы) или от -6 до 6 (для клика по графику)</li>
 *   <li>Y: целые значения от -3 до 5 (для формы) или от -6 до 6 (для клика по графику)</li>
 *   <li>R: {1, 1.5, 2, 2.5, 3}</li>
 * </ul>
 * </p>
 *
 * @param x координата X точки
 * @param y координата Y точки
 * @param r значение радиуса R (масштабирует область проверки)
 * @author Vladislav Dyadev
 * @version 1.0
 * @see weblab.beans.PointValidationBean
 * @see weblab.services.AreaCheckService
 */
public record Point(double x, double y, double r) implements Serializable {
    /**
     * Уникальный идентификатор версии для сериализации.
     * Обеспечивает совместимость при десериализации объектов.
     */
    @Serial
    private static final long serialVersionUID = 32069245872435L;
}