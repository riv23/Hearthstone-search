package com.geminicode.hssc.utils;

import com.geminicode.hssc.model.CardType;
import com.google.appengine.api.utils.SystemProperty;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class CardReader {

    public static final String URL_API = "http://hearthstonejson.com/json/AllSets.frFR.json";
    public static final String URL_LOCAL_API = "http://localhost:8080/AllSets.frFR.json";

    public static  CardType read() throws IOException {
        URL url;
        if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Development) {
            url = new URL(URL_LOCAL_API);
        }else {
            url = new URL(URL_API);
        }

        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
        final Gson gson = new Gson();

        return gson.fromJson(bufferedReader, CardType.class);
    }
}
