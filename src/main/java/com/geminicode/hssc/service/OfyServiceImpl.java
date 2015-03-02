package com.geminicode.hssc.service;

import com.geminicode.hssc.model.Card;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class OfyServiceImpl {

    static {
        factory().register(Card.class);
    }

    private OfyServiceImpl() {
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }

}
