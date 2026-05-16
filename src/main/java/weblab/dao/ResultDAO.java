package weblab.dao;

import weblab.models.HistoryEntry;
import weblab.models.Point;
import weblab.db.DBUtil;
import weblab.db.SQLQueries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object для работы с результатами проверок в базе данных.
 *
 * <p>Обеспечивает CRUD операции над таблицей результатов:
 * <ul>
 *   <li>Пакетное сохранение результатов проверок</li>
 *   <li>Получение всех результатов</li>
 *   <li>Получение результатов по идентификатору сессии</li>
 *   <li>Удаление результатов по сессии</li>
 *   <li>Полная очистка таблицы</li>
 * </ul>
 * </p>
 *
 * <p>Все методы используют {@link DBUtil} для получения соединения с базой данных.
 * В случае ошибок SQL информация выводится в стандартный поток ошибок.</p>
 *
 * @author Vladislav Dyadev
 * @version 1.0
 * @see HistoryEntry
 * @see DBUtil
 * @see SQLQueries
 */
public class ResultDAO {
    /**
     * Сохраняет пакет записей истории в базу данных.
     *
     * <p>Метод использует batch-операцию для эффективной вставки нескольких записей.
     * Автокоммит отключается перед выполнением пакета и включается только после
     * успешного выполнения всех операций.</p>
     *
     * <p>В случае ошибки SQL стектрейс выводится в {@code System.err},
     * транзакция не откатывается автоматически.</p>
     *
     * @param entries список записей истории для сохранения
     *                (может быть пустым, но не {@code null})
     */
    public void saveResultsBatch(List<HistoryEntry> entries) {
        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(SQLQueries.INSERT_RESULT)) {
                for (HistoryEntry entry : entries) {
                    Point p = entry.point();
                    ps.setDouble(1, p.x());
                    ps.setDouble(2, p.y());
                    ps.setDouble(3, p.r());
                    ps.setBoolean(4, entry.result());
                    ps.setString(5, entry.sessionId());
                    ps.setTimestamp(6, Timestamp.valueOf(entry.timestamp()));
                    ps.setDouble(7, entry.execTime());
                    ps.addBatch();
                }

                ps.executeBatch();
                conn.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Вспомогательный метод для выполнения SQL-запроса и маппинга результатов.
     *
     * <p>Выполняет параметризованный запрос и преобразует строки ResultSet
     * в объекты {@link HistoryEntry}. Используется методами
     * {@link #getAllResults()} и {@link #getResultsBySession(String)}.</p>
     *
     * @param sql       SQL-запрос (SELECT)
     * @param sessionId идентификатор сессии (может быть {@code null} для запросов без параметра)
     * @return список записей истории, соответствующих запросу
     */
    private List<HistoryEntry> getResultsBySQL(String sql, String sessionId) {
        List<HistoryEntry> results = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (sessionId != null) stmt.setString(1, sessionId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    double x = rs.getDouble("x");
                    double y = rs.getDouble("y");
                    double r = rs.getDouble("r");
                    boolean hit = rs.getBoolean("hit");
                    String sID = rs.getString("session_id");
                    Timestamp ts = rs.getTimestamp("timestamp");
                    double execTime = rs.getDouble("exec_time");

                    results.add(new HistoryEntry(new Point(x, y, r), hit, ts.toLocalDateTime(), execTime, sID));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    /**
     * Возвращает все сохранённые результаты проверок.
     *
     * <p>Записи возвращаются в порядке убывания времени создания
     * (согласно {@link SQLQueries#SELECT_ALL_RESULTS}).</p>
     *
     * @return список всех записей истории (может быть пустым)
     */
    public List<HistoryEntry> getAllResults() {
        return getResultsBySQL(SQLQueries.SELECT_ALL_RESULTS, null);
    }

    /**
     * Возвращает результаты проверок для указанной сессии.
     *
     * <p>Записи возвращаются в порядке убывания времени создания
     * (согласно {@link SQLQueries#SELECT_RESULTS_BY_SESSION}).</p>
     *
     * @param sessionID идентификатор HTTP-сессии
     * @return список записей истории для указанной сессии (может быть пустым)
     */
    public List<HistoryEntry> getResultsBySession(String sessionID) {
        return getResultsBySQL(SQLQueries.SELECT_RESULTS_BY_SESSION, sessionID);
    }

    /**
     * Удаляет все результаты проверок для указанной сессии.
     *
     * @param sessionID идентификатор HTTP-сессии
     */
    public void removeResultsBySession(String sessionID) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQLQueries.DELETE_RESULTS_BY_SESSION)) {
            stmt.setString(1, sessionID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Полностью очищает таблицу результатов.
     *
     * <p>Удаляет все записи из таблицы. Внимание! Эта операция необратима
     * и удаляет данные всех пользователей.</p>
     */
    public void clearResults() {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQLQueries.DELETE_ALL_RESULTS)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}