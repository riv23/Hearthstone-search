package com.geminicode.hssc.service;


import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Set;

public interface  InternalizationService {
    /**
     * This method allows to retrieve internationalized strings from a specified Locale java.
     */
    String getString(String key, Locale locale) throws UnsupportedEncodingException;

    Set<String> getKeys(Locale locale);
}
