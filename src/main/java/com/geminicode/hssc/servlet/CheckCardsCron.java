package com.geminicode.hssc.servlet;

import com.geminicode.hssc.service.DatastoreService;
import com.geminicode.hssc.service.SearchApiService;
import com.geminicode.hssc.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * This servlet save new cards to the Index and the DataStore.
 * It's launched every days at 0 o'clock (Paris Time) refers to cron.xml file
 */
public class CheckCardsCron extends HttpServlet {

    private final SearchApiService searchApiService = ServiceFactory.get().getSearchApiService();
    private final DatastoreService datastoreService = ServiceFactory.get().getDatastoreService();

    private static final Logger LOGGER = Logger.getLogger(CheckCardsCron.class.getName());


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (searchApiService.isLastedVersion()) {
            searchApiService.deleteAllCards();
            datastoreService.removeAllCards();
            searchApiService.checkNewCards(Locale.FRANCE);
            searchApiService.checkNewCards(Locale.US);
        } else {
            LOGGER.info("Nothing to update");
        }

    }
}
