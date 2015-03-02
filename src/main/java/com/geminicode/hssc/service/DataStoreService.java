package com.geminicode.hssc.service;

import com.geminicode.hssc.model.Card;

import java.util.List;

public interface DataStoreService {
    void putIntoDataStore(List<Card> basics);
    Card getCard(String idCard);
}
