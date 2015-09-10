package com.geminicode.hssc.service.impl;

import com.geminicode.hssc.service.InternalizationService;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Logger;

public class InternalizationServiceImpl implements InternalizationService {

    private static final Logger LOGGER = Logger.getLogger(InternalizationServiceImpl.class.getName());

    @Override
    public String getString(String key, Locale locale) {

        if (!locale.equals(Locale.FRANCE)) {
            locale = Locale.ENGLISH;
        }

        final ResourceBundle bundle = ResourceBundle.getBundle("app", locale);

        return bundle.getString(key);
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
