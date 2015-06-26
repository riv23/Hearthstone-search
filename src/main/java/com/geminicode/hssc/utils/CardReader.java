package com.geminicode.hssc.utils;

import com.geminicode.hssc.model.CardType;
import com.google.appengine.api.utils.SystemProperty;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Locale;

/**
 * This class read the JSON API.
 */
public class CardReader {

    private static final String URL_API_FR = "http://hearthstonejson.com/json/AllSets.frFR.json";
    private static final String URL_API_EN = "http://hearthstonejson.com/json/AllSets.enUS.json";
    private static final String URL_LOCAL_API_FR = "/AllSets.frFR.json";
    private static final String URL_LOCAL_API_EN = "/AllSets.enUS.json";

    public static  CardType read(Locale locale) throws IOException {
        URL url = new URL(URL_API_EN);
        if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Development) {
            if(Locale.FRENCH.equals(locale)) {
                url = new URL(URL_LOCAL_API_FR);
            }
            if(Locale.ENGLISH.equals(locale)) {
                url = new URL(URL_LOCAL_API_EN);
            }
        }else {
            if(Locale.FRENCH.equals(locale)) {
                url = new URL(URL_API_FR);
            }
            if(Locale.ENGLISH.equals(locale)) {
                url = new URL(URL_API_EN);
            }
        }

        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
        final Gson gson = new Gson();

        return gson.fromJson(bufferedReader, CardType.class);
    }
}
