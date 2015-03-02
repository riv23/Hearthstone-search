package com.geminicode.hssc.utils;

import com.geminicode.hssc.service.DataStoreService;
import com.geminicode.hssc.service.SearchApiService;
import com.geminicode.hssc.service.impl.DataStoreServiceImpl;
import com.geminicode.hssc.service.impl.SearchApiServiceImpl;

public class ServiceFactory {
    private static ServiceFactory instance = null;

    private DataStoreService dataStoreService;
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

    public DataStoreService getDataStoreService() {
        if (dataStoreService == null) {
            dataStoreService = new DataStoreServiceImpl();
        }
        return dataStoreService;
    }

    public SearchApiService getSearchApiService() {
        if (searchApiService == null) {
            searchApiService = new SearchApiServiceImpl();
        }
        return searchApiService;
    }

}
