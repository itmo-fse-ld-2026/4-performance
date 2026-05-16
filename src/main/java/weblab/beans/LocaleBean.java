package weblab.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Locale;

/**
 * Хранит выбранный язык интерфейса и синхронизирует его с JSF {@link Locale}.
 */
@Named("localeBean")
@SessionScoped
public class LocaleBean implements Serializable {
    private static final String DEFAULT_LANGUAGE = "ru";

    private String language = DEFAULT_LANGUAGE;

    @PostConstruct
    public void init() {
        applyLocale();
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Locale getLocale() {
        return Locale.forLanguageTag(language);
    }

    public void applyLocale() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context == null || context.getViewRoot() == null) {
            return;
        }
        context.getViewRoot().setLocale(getLocale());
    }

}
