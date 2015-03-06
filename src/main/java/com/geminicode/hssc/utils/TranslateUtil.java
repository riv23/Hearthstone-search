package com.geminicode.hssc.utils;

import java.util.Locale;

public class TranslateUtil {

    public static String translateRarity(String value, Locale locale) {
        if(Locale.FRENCH.equals(locale)) {
            switch (value) {
                case "Epic" :
                    return "Épique";
                case "Legendary" :
                    return "Légendaire";
                case "Common" :
                    return "Commune";
                case "Free" :
                    return "Gratuite";
            }
        }

            return value;
    }

    public static String translatePlayerClass(String value, Locale locale) {
        if(Locale.FRENCH.equals(locale)) {
            switch (value) {
                case "Warrior" :
                    return "Guerrier";
                case "Rogue" :
                    return "Voleur";
                case "Hunter" :
                    return "Chasseur";
                case "Druid" :
                    return "Druide";
                case "Shaman" :
                    return "Chaman";
                case "Warlock" :
                    return "Démoniste";
                case "Priest" :
                    return "Prêtre";
            }
        }

        return value;
    }
}
