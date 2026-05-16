package weblab.beans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import weblab.dao.ResultDAO;
import weblab.models.HistoryEntry;

import java.util.List;

/**
 * Бин для управления историей проверок точек в области.
 *
 * <p>Обеспечивает доступ к результатам проверок точек, сохраняя их в базе данных.
 * Бин является {@link ApplicationScoped}, поэтому данные доступны на уровне всего приложения,
 * но при этом поддерживается фильтрация по сессии для получения результатов конкретного пользователя.</p>
 *
 * <p>Основные возможности:
 * <ul>
 *   <li>Получение всех результатов</li>
 *   <li>Получение результатов по идентификатору сессии</li>
 *   <li>Пакетное сохранение результатов</li>
 *   <li>Удаление результатов по сессии</li>
 *   <li>Очистка всех результатов</li>
 * </ul>
 * </p>
 *
 * @author Vladislav Dyadev
 * @version 1.0
 * @see ResultDAO
 * @see HistoryEntry
 */
@Named("historyBean")
@ApplicationScoped
public class HistoryBean {
    private final ResultDAO dao = new ResultDAO();

    /**
     * Возвращает все сохранённые результаты проверок точек.
     *
     * @return список всех записей истории, отсортированный по убыванию времени
     */
    public List<HistoryEntry> getAllResults() {
        return dao.getAllResults();
    }

    /**
     * Возвращает результаты проверок для указанной сессии.
     *
     * @param sessionID идентификатор HTTP-сессии
     * @return список записей истории, относящихся к указанной сессии
     */
    public List<HistoryEntry> getResultsBySession(String sessionID) {
        return dao.getResultsBySession(sessionID);
    }

    /**
     * Сохраняет пакет результатов проверок в базу данных.
     *
     * @param entries список записей истории для сохранения
     */
    public void saveBatch(List<HistoryEntry> entries) {
        dao.saveResultsBatch(entries);
    }

    /**
     * Удаляет все результаты проверок для указанной сессии.
     *
     * @param sessionID идентификатор HTTP-сессии
     */
    public void removeResultsBySession(String sessionID) {
        dao.removeResultsBySession(sessionID);
    }

    /**
     * Полностью очищает все результаты проверок из базы данных.
     *
     * <p>Внимание! Этот метод удаляет данные всех пользователей.
     * Для удаления только текущей сессии используйте {@link #removeResultsBySession(String)}.</p>
     */
    public void clearResults() {
        dao.clearResults();
    }

    /**
     * Очищает результаты для текущей сессии.
     *
     * <p>Метод пытается получить идентификатор текущей сессии через JSF {@link jakarta.faces.context.FacesContext}.
     * Если сессия определена, удаляются только её результаты. Если контекст недоступен или сессия не найдена,
     * выполняется полная очистка всех результатов через {@link #clearResults()}.</p>
     */
    public void clearAll() {
        jakarta.faces.context.FacesContext context = jakarta.faces.context.FacesContext.getCurrentInstance();
        if (context != null && context.getExternalContext() != null) {
            String sessionID = context.getExternalContext().getSessionId(false);
            if (sessionID != null) {
                removeResultsBySession(sessionID);
                return;
            }
        }
        clearResults();
    }

    /**
     * Возвращает результаты проверок для текущей сессии.
     *
     * <p>Если возможно определить идентификатор текущей сессии через JSF,
     * возвращаются результаты только текущего пользователя.
     * В противном случае возвращаются все результаты (например, при тестировании).</p>
     *
     * @return список записей истории для текущей сессии или все записи,
     *         если сессию не удалось определить
     */
    public List<HistoryEntry> getEntries() {
        jakarta.faces.context.FacesContext context = jakarta.faces.context.FacesContext.getCurrentInstance();
        if (context != null && context.getExternalContext() != null) {
            String sessionID = context.getExternalContext().getSessionId(false);
            if (sessionID != null) {
                return getResultsBySession(sessionID);
            }
        }
        return getAllResults();
    }
}