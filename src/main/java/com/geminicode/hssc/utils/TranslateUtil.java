package com.geminicode.hssc.utils;

import com.geminicode.hssc.service.InternalizationService;
import com.google.common.base.Strings;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

/**
 * This class util allows translate some strings.
 */
public class TranslateUtil {

    private final static InternalizationService internalizationService = ServiceFactory.get().getInternalizationService();

    public static String buildLanguageField(Locale locale) {
        return locale.getLanguage();
    }

    public static String translatePlayerClass(String value, Locale locale) throws UnsupportedEncodingException {
        if (Strings.isNullOrEmpty(value)) {
            return internalizationService.getString("All", locale);
        }
        return internalizationService.getString(value, locale);
    }

    public static String translate(String value, Locale locale) throws UnsupportedEncodingException {
        if (Strings.isNullOrEmpty(value)) {
            return "";
        }

        if (value.contains("Divine Shield")) { //Needed for values with spaces
            return internalizationService.getString("DivineShield", locale);
        }

        return internalizationService.getString(value, locale);
    }
}
