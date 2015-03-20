package com.geminicode.hssc.cron;

import com.geminicode.hssc.service.SearchApiService;
import com.geminicode.hssc.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CheckCardsCron extends HttpServlet {

    private final SearchApiService searchApiService = ServiceFactory.get().getSearchApiService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        searchApiService.checkNewCards();
    }
}
