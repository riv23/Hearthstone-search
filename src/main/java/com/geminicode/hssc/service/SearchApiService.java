package com.geminicode.hssc.service;

import com.geminicode.hssc.model.Card;
import com.google.appengine.api.search.SearchException;

import java.io.IOException;
import java.util.List;

public interface SearchApiService {
    List<Card> search(String query) throws SearchException;
    Card searchById(String id) throws SearchException;
    void checkNewCards() throws IOException;
}
