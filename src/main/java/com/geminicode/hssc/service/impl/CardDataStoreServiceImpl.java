package com.geminicode.hssc.service.impl;

import com.geminicode.hssc.model.Card;
import com.geminicode.hssc.service.CardDatastoreService;
import com.geminicode.hssc.service.OfyServiceImpl;
import com.googlecode.objectify.Key;

import java.util.List;

public class CardDatastoreServiceImpl implements CardDatastoreService{

    @Override
    public void putIntoDataStore(List<Card> basics) {
        deleteOldCards();
        saveNewCards(basics);
    }

    @Override
    public Card getCard(String idCard) {
        return OfyServiceImpl.ofy().load().key(Key.create(Card.class, idCard)).now();
    }

    private void saveNewCards(List<Card> basics) {
        OfyServiceImpl.ofy().save().entities(basics).now();
    }

    private void deleteOldCards() {
        final List<Card> oldCards = OfyServiceImpl.ofy().load().type(Card.class).list();
        OfyServiceImpl.ofy().delete().entities(oldCards).now();
    }
}
