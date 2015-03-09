package com.geminicode.hssc.utils;

import java.util.Locale;

public class TranslateUtil {

    public static String translateRarityToFrench(String value, Locale locale) {
        if(Locale.FRENCH.equals(locale)) {
            switch (value) {
                case HSSCStrings.EPIC_EN:
                    return HSSCStrings.EPIC_FR;
                case HSSCStrings.LEGENDARY_EN:
                    return HSSCStrings.LEGENDARY_FR;
                case HSSCStrings.COMMON_EN:
                    return HSSCStrings.COMMON_FR;
                case HSSCStrings.FREE_EN:
                    return HSSCStrings.FREE_FR;
            }
        }

            return value;
    }

    public static String translatePlayerClassToFrench(String value, Locale locale) {
        if(Locale.FRENCH.equals(locale)) {
            switch (value) {
                case HSSCStrings.WARRIOR_EN:
                    return HSSCStrings.WARRIOR_FR;
                case HSSCStrings.ROGUE_EN:
                    return HSSCStrings.ROGUE_FR;
                case HSSCStrings.HUNTER_EN:
                    return HSSCStrings.HUNTER_FR;
                case HSSCStrings.DRUID_EN:
                    return HSSCStrings.DRUID_FR;
                case HSSCStrings.SHAMAN_EN:
                    return HSSCStrings.SHAMAN_FR;
                case HSSCStrings.WARLOCK_EN:
                    return HSSCStrings.WARLOCK_FR;
                case HSSCStrings.PRIEST_EN:
                    return HSSCStrings.PRIEST_FR;
            }
        }

        return value;
    }

    public static String translateTypeToFrench(String value, Locale locale) {
        if(Locale.FRENCH.equals(locale)) {
            switch (value) {
                case HSSCStrings.MINION_EN:
                    return HSSCStrings.MINION_FR;
                case HSSCStrings.SPELL_EN:
                    return HSSCStrings.SPELL_FR;

            }
        }

        return value;
    }
}
