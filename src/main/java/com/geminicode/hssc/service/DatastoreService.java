package com.geminicode.hssc.service;


import com.geminicode.hssc.model.Card;
import com.geminicode.hssc.model.NameCard;

import java.util.List;

public interface DatastoreService {
    List<NameCard> searchNameCards(String query);

    void putCards(List<Card> basics);

    void removeAllCards();

    void putOtherString();
}
