package com.geminicode.hssc.service;

import com.geminicode.hssc.model.Card;
import com.googlecode.objectify.Key;

import java.util.List;

public class CardDataStoreService {

    public void putIntoDataStore(List<Card> basics) {
        OfyService.ofy().save().entities(basics).now();
    }

    public Card getCard(String idCard) {
        return OfyService.ofy().load().key(Key.create(Card.class, idCard)).now();
    }
}
