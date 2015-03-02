package com.geminicode.hssc.service;

import com.geminicode.hssc.model.Card;
import com.googlecode.objectify.Key;

import java.util.List;

public class CardDataStoreService {

    public void putIntoDataStore(List<Card> basics) {
        deleteOldCards();
        saveNewCards(basics);
    }

    private void saveNewCards(List<Card> basics) {
        OfyService.ofy().save().entities(basics).now();
    }

    private void deleteOldCards() {
        final List<Card> oldCards = OfyService.ofy().load().type(Card.class).list();
        OfyService.ofy().delete().entities(oldCards);
    }

    public Card getCard(String idCard) {
        return OfyService.ofy().load().key(Key.create(Card.class, idCard)).now();
    }
}
