package com.geminicode.hssc.utils;

import com.geminicode.hssc.model.Card;
import com.google.appengine.api.urlfetch.*;
import com.google.appengine.api.utils.SystemProperty;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

/**
 * This class read the JSON API.
 */
public class CardReader {

    private static final String URL_API_FR = "http://hearthstonejson.com/json/AllSets.frFR.json";
    private static final String URL_API_EN = "http://hearthstonejson.com/json/AllSets.enUS.json";
    private static final String URL_LOCAL_API_FR = "http://hearthstone-search.com/AllSets.frFR.json";
    private static final String URL_LOCAL_API_EN = "http://hearthstone-search.com/AllSets.enUS.json";

    private final static Logger LOGGER = Logger.getLogger(CardReader.class.getName());

    public static  Map<String, List<Card>> read(Locale locale) throws IOException {
        URL url = new URL(URL_API_EN);
        if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Development) {
            if(Locale.FRANCE.equals(locale)) {
                url = new URL(URL_LOCAL_API_FR);
            }
            if(Locale.US.equals(locale)) {
                url = new URL(URL_LOCAL_API_EN);
            }
        }else {
            if(Locale.FRANCE.equals(locale)) {
                url = new URL(URL_API_FR);
            }
            if(Locale.US.equals(locale)) {
                url = new URL(URL_API_EN);
            }
        }

        BufferedReader bufferedReader;
        try {
            bufferedReader =  getBufferReader(url);
        }catch (SocketTimeoutException e) {

            LOGGER.warning("Error while fetching " + url.toString() + " ERROR : " + e.getLocalizedMessage());

            if(Locale.FRANCE.equals(locale)) {
                url = new URL(URL_LOCAL_API_FR);
            }
            if(Locale.US.equals(locale)) {
                url = new URL(URL_LOCAL_API_EN);
            }

            bufferedReader = getBufferReader(url);
        }
        final Gson gson = new Gson();

        return gson.fromJson(bufferedReader, new TypeToken<Map<String, List<Card>>>(){}.getType());
    }

    public static BufferedReader getBufferReader(URL url) throws IOException {
        final URLFetchService fetcher = URLFetchServiceFactory.getURLFetchService();
        final FetchOptions lFetchOptions = FetchOptions.Builder.validateCertificate().setDeadline(60D);
        final HTTPRequest request = new HTTPRequest(url, HTTPMethod.GET, lFetchOptions);
        final HTTPResponse response = fetcher.fetch(request);
        return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getContent()), "UTF-8"));
    }
}
