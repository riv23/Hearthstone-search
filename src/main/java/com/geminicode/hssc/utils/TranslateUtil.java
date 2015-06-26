package com.geminicode.hssc.utils;

import com.geminicode.hssc.service.InternalizationService;
import com.google.common.base.Strings;

import java.util.Locale;

/**
 * This class util allows translate some strings.
 */
public class TranslateUtil {

    private final static InternalizationService internalizationService = ServiceFactory.get().getInternalizationService();

    public static String translateRarity(String value, Locale locale) {
        if (Locale.FRENCH.equals(locale)) {
            if (value.equals(internalizationService.getString(value, locale))) {
                return internalizationService.getString(value, locale);
            }
        }

        return value;
    }

    public static String translatePlayerClass(String value, Locale locale) {

        if (Strings.isNullOrEmpty(value)) {
            return internalizationService.getString("All", locale);
        }

        if (Locale.FRENCH.equals(locale)) {
            if (value.equals(internalizationService.getString(value, locale))) {
                return internalizationService.getString(value, locale);
            }
        }

        return value;
    }

    public static String translateType(String value, Locale locale) {
        if (Locale.FRENCH.equals(locale)) {
            if (value.equals(internalizationService.getString(value, locale))) {
                return internalizationService.getString(value, locale);
            }
        }

        return value;
    }

    public static String translateMechanic(String value, Locale locale) {

        if (Strings.isNullOrEmpty(value)) {
            return "";
        }

        if (Locale.FRENCH.equals(locale)) {
            if ("Divine Shield".equals(value)) {
                internalizationService.getString("DivineShield", locale);
            } else if (value.equals(internalizationService.getString(value, locale))) {
                return internalizationService.getString(value, locale);
            }

        }
        return value;
    }

    public static String translateRace(String value, Locale locale) {

        if (Strings.isNullOrEmpty(value)) {
            return "";
        }

        if (Locale.FRENCH.equals(locale)) {
            if (Locale.FRENCH.equals(locale)) {
                if (value.equals(internalizationService.getString(value, locale))) {
                    return internalizationService.getString(value, locale);
                }
            }
        }
        return value;
    }

    public static String buildLanguageField(Locale locale) {
        String language = "";
        if (Locale.FRENCH.equals(locale)) {
            language = "fr";
        }
        if (Locale.ENGLISH.equals(locale)) {
            language = "en";
        }
        return language;
    }
}
