package com.geminicode.hssc.service;


import com.geminicode.hssc.model.Card;
import com.geminicode.hssc.model.NameCard;

import java.util.List;
import java.util.Locale;

public interface DatastoreService {
    List<NameCard> searchNameCards(String query);

    void putCards(List<Card> basics, Locale locale);

    void removeAllCards();

    void putOtherString(Locale locale);
}
