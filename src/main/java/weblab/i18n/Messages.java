package weblab.i18n;

import jakarta.faces.context.FacesContext;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Доступ к строкам локализации из {@code weblab/messages*.properties}.
 */
public final class Messages {
    private static final String BUNDLE_BASE = "weblab.messages";
    private static final Locale DEFAULT_LOCALE = Locale.forLanguageTag("ru");

    private Messages() {
    }

    public static String get(String key) {
        return get(key, currentLocale());
    }

    public static String format(String key, Object... args) {
        return String.format(currentLocale(), get(key), args);
    }

    public static String get(String key, Locale locale) {
        try {
            return ResourceBundle.getBundle(BUNDLE_BASE, locale).getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    private static Locale currentLocale() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null && context.getViewRoot() != null) {
            return context.getViewRoot().getLocale();
        }
        return DEFAULT_LOCALE;
    }
}
