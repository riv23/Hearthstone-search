package com.geminicode.hssc.utils;

import com.geminicode.hssc.service.InternalizationService;
import com.google.common.base.Strings;

import java.util.Locale;
import java.util.logging.Logger;

/**
 * This class util allows translate some strings.
 */
public class TranslateUtil {

    private static final Logger LOGGER = Logger.getLogger(TranslateUtil.class.getName());

    private final static InternalizationService internalizationService = ServiceFactory.get().getInternalizationService();

    public static String translateRarity(String value, Locale locale) {
        if (Strings.isNullOrEmpty(value)) {
            return "";
        }

        return internalizationService.getString(value, locale);
    }

    public static String translatePlayerClass(String value, Locale locale) {

        if (Strings.isNullOrEmpty(value)) {
            return internalizationService.getString("All", locale);
        }

        return internalizationService.getString(value, locale);
    }

    public static String translateType(String value, Locale locale) {
        if (Strings.isNullOrEmpty(value)) {
            return "";
        }
        return internalizationService.getString(value, locale);
    }

    public static String translateMechanic(String value, Locale locale) {

        LOGGER.info("Translation to " + locale + "Value : |"+value+"|");

        if (Strings.isNullOrEmpty(value)) {
            return "";
        }

        if (value.contains("Divine Shield")) {
            return internalizationService.getString("DivineShield", locale);
        }

        return internalizationService.getString(value, locale);

    }

    public static String translateRace(String value, Locale locale) {

        if (Strings.isNullOrEmpty(value)) {
            return "";
        }

        return internalizationService.getString(value, locale);
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
