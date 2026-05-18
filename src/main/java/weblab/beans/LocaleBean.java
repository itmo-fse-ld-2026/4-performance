package weblab.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Locale;

/**
 * Хранит выбранный язык интерфейса и синхронизирует его с JSF {@link Locale}.
 *
 * <p>Бин является {@link SessionScoped}, поэтому выбранный язык сохраняется
 * на протяжении всей сессии пользователя. Поддерживается динамическое
 * переключение языка без перезапуска приложения.
 *
 * <p>Доступные языки определяются в JSF-страницах через выпадающий список.
 * При изменении языка автоматически обновляется локаль в корне представления.
 *
 * @author Vladislav Dyadev
 * @version 1.0
 */
@Named("localeBean")
@SessionScoped
public class LocaleBean implements Serializable {
    /**
     * Конструктор по умолчанию.
     */
    public LocaleBean() {
        // Конструктор по умолчанию
    }

    /**
     * Язык интерфейса по умолчанию — русский.
     */
    private static final String DEFAULT_LANGUAGE = "ru";

    /**
     * Текущий выбранный язык.
     */
    private String language = DEFAULT_LANGUAGE;

    /**
     * Инициализация бина после создания.
     * Применяет текущую локаль к JSF-контексту.
     */
    @PostConstruct
    public void init() {
        applyLocale();
    }

    /**
     * Возвращает текущий выбранный язык.
     *
     * @return код языка (например, "ru", "en")
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Устанавливает новый язык интерфейса.
     *
     * @param language код языка (например, "ru", "en")
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Возвращает объект {@link Locale}, соответствующий текущему языку.
     *
     * @return локаль для текущего языка
     */
    public Locale getLocale() {
        return Locale.forLanguageTag(language);
    }

    /**
     * Применяет текущую локаль к корню представления JSF.
     *
     * <p>Метод вызывается при инициализации бина и при переключении языка
     * через JSF. Если {@link FacesContext} недоступен (например, при тестировании),
     * метод ничего не делает.
     */
    public void applyLocale() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context == null || context.getViewRoot() == null) {
            return;
        }
        context.getViewRoot().setLocale(getLocale());
    }
}