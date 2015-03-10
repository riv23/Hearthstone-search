package com.geminicode.hssc.servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.geminicode.hssc.service.SearchApiService;
import com.geminicode.hssc.utils.ServiceFactory;
import com.google.appengine.repackaged.com.google.api.client.util.Strings;

public class DeleteServlet extends HttpServlet {


    private final SearchApiService searchApiService = ServiceFactory.get().getSearchApiService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        searchApiService.deleteEntries();

    }
}
