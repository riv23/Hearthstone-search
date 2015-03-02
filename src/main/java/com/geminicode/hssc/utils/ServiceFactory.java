package com.geminicode.hssc.utils;

import com.geminicode.hssc.service.CardDataStoreService;
import com.geminicode.hssc.service.SearchApiService;
import com.geminicode.hssc.service.impl.CardDataStoreServiceImpl;
import com.geminicode.hssc.service.impl.SearchApiServiceImpl;

public class ServiceFactory {
    private static ServiceFactory instance = null;

    private CardDataStoreService cardDataStoreService;
    private SearchApiService searchApiService;

    private ServiceFactory() {

    }

    public static ServiceFactory get() {
        if (instance == null) {
            synchronized (ServiceFactory.class) {
                if (instance == null) {
                    instance = new ServiceFactory();
                }
            }
        }
        return instance;
    }

    public CardDataStoreService getCardDataStoreService() {
        if (cardDataStoreService == null) {
            cardDataStoreService = new CardDataStoreServiceImpl();
        }
        return cardDataStoreService;
    }

    public SearchApiService getSearchApiService() {
        if (searchApiService == null) {
            searchApiService = new SearchApiServiceImpl();
        }
        return searchApiService;
    }

}
