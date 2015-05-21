package com.geminicode.hssc.service;

import com.geminicode.hssc.model.NameCard;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class OfyService {
    static {
        factory().register(NameCard.class);
    }

    private OfyService() {}

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    private static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}
