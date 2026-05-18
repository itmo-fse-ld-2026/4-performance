package weblab.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * JSF-бин для управления идентификатором HTTP-сессии.
 *
 * <p>Бин является {@link SessionScoped}, поэтому его состояние сохраняется
 * на протяжении всей пользовательской сессии. Предоставляет доступ
 * к уникальному идентификатору текущей HTTP-сессии.
 *
 * <p>Используется для привязки результатов проверки точек к конкретному
 * пользователю и для фильтрации истории по сессии.
 *
 * @author Vladislav Dyadev
 * @version 1.0
 * @see jakarta.faces.context.FacesContext
 * @see HttpSession
 */
@Named
@SessionScoped
public class SessionBean implements Serializable {
    /**
     * Конструктор по умолчанию.
     */
    public SessionBean() {
        // Конструктор по умолчанию
    }

    /**
     * Возвращает уникальный идентификатор текущей HTTP-сессии.
     *
     * <p>Метод получает текущий JSF-контекст, извлекает из него
     * HTTP-сессию (создавая её, если она не существует) и возвращает
     * её уникальный идентификатор.
     *
     * <p>Идентификатор сессии используется в {@link HistoryBean}
     * для фильтрации результатов проверок по конкретному пользователю.
     *
     * @return уникальный идентификатор HTTP-сессии, никогда не {@code null}
     */
    public String getId() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        return session.getId();
    }
}