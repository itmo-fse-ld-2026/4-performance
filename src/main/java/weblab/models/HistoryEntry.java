package weblab.models;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Запись истории проверки точки, сохраняемая в базу данных.
 *
 * <p>Содержит полную информацию об одной проверке точки:
 * координаты, результат, время выполнения, идентификатор сессии.
 * Реализует {@link Serializable} для возможности сохранения в HTTP-сессии.
 *
 * <p>Объекты этого класса создаются при каждой проверке точки и передаются
 * в {@link weblab.dao.ResultDAO} для сохранения в базу данных.
 *
 * @param point     проверяемая точка с координатами X, Y и радиусом R
 * @param result    результат проверки: {@code true} - попадание, {@code false} - промах
 * @param timestamp время выполнения проверки
 * @param execTime  время выполнения проверки в миллисекундах
 * @param sessionId идентификатор HTTP-сессии для привязки к пользователю
 * @author Vladislav Dyadev
 * @version 1.0
 * @see Point
 * @see weblab.dao.ResultDAO
 */
public record HistoryEntry(Point point, boolean result, LocalDateTime timestamp, double execTime, String sessionId) implements Serializable {
    /**
     * Уникальный идентификатор версии для сериализации.
     * Обеспечивает совместимость при десериализации объектов.
     */
    @Serial
    private static final long serialVersionUID = 215215125212651L;
}