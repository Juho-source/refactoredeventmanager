package org.example.sep_projecta;

import java.util.Locale;

/**
 * Singleton class for managing the application's locale settings.
 */
public class LocaleManager {
    private static LocaleManager instance;
    private Locale currentLocale;

    /**
     * Private constructor to prevent instantiation.
     * Sets the default locale to English (US).
     */
    private LocaleManager() {
        // Default locale
        currentLocale = new Locale("en", "US");
    }

    /**
     * Returns the singleton instance of LocaleManager.
     *
     * @return the singleton instance
     */
    public static synchronized LocaleManager getInstance() {
        if (instance == null) {
            instance = new LocaleManager();
        }
        return instance;
    }

    /**
     * Gets the current locale.
     *
     * @return the current locale
     */
    public Locale getCurrentLocale() {
        return currentLocale;
    }

    /**
     * Sets the current locale.
     *
     * @param locale the new locale to set
     */
    public void setCurrentLocale(Locale locale) {
        this.currentLocale = locale;
    }

    /**
     * Gets the language code of the current locale.
     *
     * @return the language code of the current locale
     */
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

