package com.geminicode.hssc.utils;

import com.geminicode.hssc.service.DatastoreService;
import com.geminicode.hssc.service.InternalizationService;
import com.geminicode.hssc.service.MailService;
import com.geminicode.hssc.service.SearchApiService;
import com.geminicode.hssc.service.impl.DatastoreServiceImpl;
import com.geminicode.hssc.service.impl.InternalizationServiceImpl;
import com.geminicode.hssc.service.impl.MailServiceImpl;
import com.geminicode.hssc.service.impl.SearchApiServiceImpl;

public class ServiceFactory {
    private static ServiceFactory instance = null;

    private SearchApiService searchApiService;
    private DatastoreService datastoreService;
    private MailService mailService;
    private InternalizationService internalizationService;

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

    public SearchApiService getSearchApiService() {
        if (searchApiService == null) {
            searchApiService = new SearchApiServiceImpl();
        }
        return searchApiService;
    }

    public DatastoreService getDatastoreService() {
        if(datastoreService == null) {
            datastoreService = new DatastoreServiceImpl();
        }
        return datastoreService;
    }

    public MailService getMailService() {
        if(mailService == null) {
            mailService = new MailServiceImpl();
        }
        return mailService;
    }

    public InternalizationService getInternalizationService() {
        if(internalizationService == null) {
            internalizationService = new InternalizationServiceImpl();
        }
        return internalizationService;
    }

}
