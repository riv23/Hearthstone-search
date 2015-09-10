package com.geminicode.hssc.service.impl;

import com.geminicode.hssc.service.InternalizationService;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Logger;

public class InternalizationServiceImpl implements InternalizationService {

    private static final Logger LOGGER = Logger.getLogger(InternalizationServiceImpl.class.getName());

    @Override
    public String getString(String key, Locale locale) throws UnsupportedEncodingException {

        if (!locale.equals(Locale.FRANCE)) {
            locale = Locale.ENGLISH;
        }

        final ResourceBundle bundle = ResourceBundle.getBundle("app", locale);
        final String string = bundle.getString(key);
        return  new String(string.getBytes("ISO-8859-1"), "UTF-8");
    }

    @Override
    public Set<String> getKeys(Locale locale) {

        if (!locale.equals(Locale.FRANCE)) {
            locale = Locale.ENGLISH;
        }

        final ResourceBundle bundle = ResourceBundle.getBundle("app", locale);

        return bundle.keySet();

    }

}
