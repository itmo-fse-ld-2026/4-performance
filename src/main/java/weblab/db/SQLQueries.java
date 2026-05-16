package weblab.db;

/**
 * Класс, содержащий SQL-запросы для работы с таблицей результатов.
 *
 * <p>Все запросы хранятся в виде текстовых блоков (Java 15+ text blocks)
 * для удобочитаемости и лёгкости поддержки.</p>
 *
 * <p>Таблица {@code results} предназначена для хранения истории проверок точек
 * и имеет следующую структуру:
 * <ul>
 *   <li>{@code id} - первичный ключ, автоинкремент</li>
 *   <li>{@code x} - координата X точки (double)</li>
 *   <li>{@code y} - координата Y точки (double)</li>
 *   <li>{@code r} - радиус R (double)</li>
 *   <li>{@code hit} - результат проверки (попадание/промах)</li>
 *   <li>{@code session_id} - идентификатор HTTP-сессии</li>
 *   <li>{@code timestamp} - время проверки</li>
 *   <li>{@code exec_time} - время выполнения проверки в миллисекундах</li>
 * </ul>
 * </p>
 *
 * @author Vladislav Dyadev
 * @version 1.0
 * @see weblab.models.HistoryEntry
 */
public class SQLQueries {
    /**
     * SQL-запрос для создания таблицы результатов.
     *
     * <p>Использует {@code IF NOT EXISTS} для безопасного повторного вызова.
     * Тип {@code SERIAL} автоматически генерирует уникальные идентификаторы.</p>
     */
    public static final String CREATE_TABLE_RESULTS = """
        CREATE TABLE IF NOT EXISTS results (
            id SERIAL PRIMARY KEY,
            x DOUBLE PRECISION NOT NULL,
            y DOUBLE PRECISION NOT NULL,
            r DOUBLE PRECISION NOT NULL,
            hit BOOLEAN NOT NULL,
            session_id VARCHAR(128),
            timestamp TIMESTAMP,
            exec_time DOUBLE PRECISION
        );
    """;

    /**
     * SQL-запрос для вставки новой записи о проверке точки.
     *
     * <p>Использует параметризованные значения (?) для защиты от SQL-инъекций.
     * Порядок параметров:
     * <ol>
     *   <li>x - координата X</li>
     *   <li>y - координата Y</li>
     *   <li>r - радиус R</li>
     *   <li>hit - результат проверки</li>
     *   <li>session_id - идентификатор сессии</li>
     *   <li>timestamp - время проверки</li>
     *   <li>exec_time - время выполнения</li>
     * </ol>
     * </p>
     */
    public static final String INSERT_RESULT = """
        INSERT INTO results (x, y, r, hit, session_id, timestamp, exec_time) 
        VALUES (?, ?, ?, ?, ?, ?, ?);
    """;

    /**
     * SQL-запрос для получения всех записей результатов.
     *
     * <p>Результаты сортируются по времени создания в порядке убывания
     * (самые новые записи первыми).</p>
     */
    public static final String SELECT_ALL_RESULTS = """
        SELECT x, y, r, hit, session_id, timestamp, exec_time 
        FROM results 
        ORDER BY timestamp DESC;
    """;

    /**
     * SQL-запрос для получения записей результатов по идентификатору сессии.
     *
     * <p>Использует параметр {@code session_id} для фильтрации.
     * Результаты сортируются по времени создания в порядке убывания.</p>
     */
    public static final String SELECT_RESULTS_BY_SESSION = """
        SELECT x, y, r, hit, session_id, timestamp, exec_time 
        FROM results 
        WHERE session_id = ? 
        ORDER BY timestamp DESC;
    """;

    /**
     * SQL-запрос для полной очистки таблицы результатов.
     *
     * <p>Удаляет все записи без возможности восстановления.</p>
     */
    public static final String DELETE_ALL_RESULTS = """
        DELETE FROM results;
    """;

    /**
     * SQL-запрос для удаления результатов по идентификатору сессии.
     *
     * <p>Удаляет все записи, связанные с указанной сессией.</p>
     */
    public static final String DELETE_RESULTS_BY_SESSION = """
        DELETE FROM results WHERE session_id = ?;
    """;
}