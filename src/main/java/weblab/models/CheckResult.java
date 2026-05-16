package weblab.models;

/**
 * Результат проверки одной точки на попадание в область.
 *
 * <p>Этот record объединяет проверяемую точку и результат проверки.
 * Используется как возвращаемое значение сервисом {@link weblab.services.AreaCheckService}.</p>
 *
 * <p>Record обеспечивает неизменяемость данных и автоматически генерирует
 * конструктор, геттеры, {@code equals()}, {@code hashCode()} и {@code toString()}.</p>
 *
 * @param point проверяемая точка (не может быть {@code null})
 * @param hit   результат проверки: {@code true} - точка попала в область,
 *             {@code false} - точка не попала
 * @author Vladislav Dyadev
 * @version 1.0
 * @see Point
 * @see weblab.services.AreaCheckService
 */
public record CheckResult(Point point, boolean hit) {}