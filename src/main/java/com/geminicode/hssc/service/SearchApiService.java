package com.geminicode.hssc.service;

import java.io.IOException;
import java.util.List;

import com.geminicode.hssc.model.Card;
import com.geminicode.hssc.model.CardType;
import com.geminicode.hssc.model.TypesEnum;
import com.google.appengine.api.search.SearchException;

public interface SearchApiService {
    List<Card> search(String query) throws SearchException;
    Card searchById(String id) throws SearchException;
    void checkNewCards() throws IOException;
}
