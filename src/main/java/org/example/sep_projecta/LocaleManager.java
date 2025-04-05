package org.example.sep_projecta;

import java.util.Locale;

public class LocaleManager {
    private static LocaleManager instance;
    private Locale currentLocale;

    private LocaleManager() {
        // Default locale
        currentLocale = new Locale("en", "US");
    }

    public static synchronized LocaleManager getInstance() {
        if (instance == null) {
            instance = new LocaleManager();
        }
        return instance;
    }

    public Locale getCurrentLocale() {
        return currentLocale;
    }

    public void setCurrentLocale(Locale locale) {
        this.currentLocale = locale;
    }

    public String getLanguageCode() {
        switch (currentLocale.getLanguage()) {
            case "en":
                return "en";
            case "uk":
                return "uk";
            case "zh":
                return "zh";
            default:
                return "en";
        }
    }
}
