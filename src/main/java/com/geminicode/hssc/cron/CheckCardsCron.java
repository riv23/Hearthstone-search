package com.geminicode.hssc.cron;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.geminicode.hssc.service.SearchApiService;
import com.geminicode.hssc.utils.ServiceFactory;

public class CheckCardsCron extends HttpServlet {

    private final SearchApiService searchApiService = ServiceFactory.get().getSearchApiService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        searchApiService.checkNewCards();
	}
}
