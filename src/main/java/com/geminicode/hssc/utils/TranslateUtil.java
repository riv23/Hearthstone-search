package com.geminicode.hssc.utils;

import java.util.Locale;

public class TranslateUtil {

    public static String translateRarity(String value, Locale locale) {
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

    public static String translatePlayerClass(String value, Locale locale) {
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

    public static String translateType(String value, Locale locale) {
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

    public static  String translateMechanic(String mechanic, Locale locale) {
        if (Locale.FRENCH.equals(locale)) {
            switch (mechanic) {
                case HSSCStrings.BATTLECRY_EN:
                    return HSSCStrings.BATTLECRY_FR;
                case HSSCStrings.FREEZE_EN:
                    return HSSCStrings.FREEZE_FR;
                case HSSCStrings.DEATHRATTLE_EN:
                    return HSSCStrings.DEATHRATTLE_FR;
                case HSSCStrings.STEALTH_EN:
                    return HSSCStrings.STEALTH_FR;
                case HSSCStrings.WINDFURY_EN:
                    return HSSCStrings.WINDFURY_FR;
                case HSSCStrings.TAUNT_EN:
                    return HSSCStrings.TAUNT_FR;
                case HSSCStrings.ENRAGE_EN:
                    return HSSCStrings.ENRAGE_FR;
                case HSSCStrings.DIVINESHIELD_EN:
                    return HSSCStrings.DIVINESHIELD_FR;
                default:
                    return mechanic;
            }
        }
        return mechanic;
    }

    public static String translateRace(String race, Locale locale) {
        if (Locale.FRENCH.equals(locale)) {
            switch (race) {
                case HSSCStrings.MECH_EN:
                    return HSSCStrings.MECH_FR;
                default:
                    return race;
            }
        }
        return race;
    }
}
