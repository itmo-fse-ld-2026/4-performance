package weblab.i18n;

import jakarta.faces.context.FacesContext;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Доступ к строкам локализации из {@code weblab/messages*.properties}.
 *
 * <p>Этот класс предоставляет статические методы для получения локализованных
 * сообщений из ресурсных файлов. Поддерживает два языка: русский (по умолчанию)
 * и английский.
 *
 * <p>Методы автоматически определяют текущую локаль из JSF-контекста,
 * что позволяет динамически переключать язык интерфейса.
 *
 * @author Vladislav Dyadev
 * @version 1.0
 */
public final class Messages {

    /**
     * Базовое имя для файлов ресурсов с локализацией.
     * Соответствует пути: {@code weblab/messages*.properties}.
     */
    private static final String BUNDLE_BASE = "weblab.messages";

    /**
     * Локаль по умолчанию — русский язык.
     * Используется, когда JSF-контекст недоступен.
     */
    private static final Locale DEFAULT_LOCALE = Locale.forLanguageTag("ru");

    /**
     * Приватный конструктор, запрещающий создание экземпляров утилитарного класса.
     */
    private Messages() {
    }

    /**
     * Возвращает локализованное сообщение для текущей локали JSF-контекста.
     *
     * @param key ключ сообщения в файле ресурсов
     * @return локализованное сообщение, или {@code !ключ!} если ключ не найден
     */
    public static String get(String key) {
        return get(key, currentLocale());
    }

    /**
     * Возвращает форматированное локализованное сообщение с подстановкой аргументов.
     *
     * <p>Пример использования:
     * <pre>
     * Messages.format("result.message", x, y, hit);
     * </pre>
     *
     * @param key  ключ сообщения в файле ресурсов
     * @param args аргументы для форматирования (подставляются в {@code %s})
     * @return отформатированное локализованное сообщение
     */
    public static String format(String key, Object... args) {
        return String.format(currentLocale(), get(key), args);
    }

    /**
     * Возвращает локализованное сообщение для указанной локали.
     *
     * @param key    ключ сообщения в файле ресурсов
     * @param locale целевая локаль
     * @return локализованное сообщение, или {@code !ключ!} если ключ не найден
     */
    public static String get(String key, Locale locale) {
        try {
            return ResourceBundle.getBundle(BUNDLE_BASE, locale).getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    /**
     * Определяет текущую локаль из JSF-контекста.
     *
     * <p>Если JSF-контекст недоступен (например, при тестировании),
     * возвращается локаль по умолчанию (русская).
     *
     * @return текущая локаль, полученная из {@code FacesContext.getViewRoot().getLocale()},
     *         или {@link #DEFAULT_LOCALE} если контекст недоступен
     */
    private static Locale currentLocale() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null && context.getViewRoot() != null) {
            return context.getViewRoot().getLocale();
        }
        return DEFAULT_LOCALE;
    }
}