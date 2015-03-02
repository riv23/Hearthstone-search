package com.geminicode.hssc.service;

import com.geminicode.hssc.model.Card;

import java.util.List;

public interface CardDatastoreService {
    void putIntoDataStore(List<Card> basics);
    Card getCard(String idCard);
}
