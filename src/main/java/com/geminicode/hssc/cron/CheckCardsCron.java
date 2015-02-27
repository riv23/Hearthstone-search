package com.geminicode.hssc.cron;

import com.geminicode.hssc.model.CardType;
import com.geminicode.hssc.model.TypesEnum;
import com.geminicode.hssc.service.SearchService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class CheckCardsCron extends HttpServlet {

	public static final String URL_API = "http://hearthstonejson.com/json/AllSets.frFR.json";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		final URL url = new URL(URL_API);
		final BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
		final Gson gson = new Gson();

		final CardType cardType = gson.fromJson(br, CardType.class);

		final SearchService searchService = new SearchService();
		searchService.addToSearch(cardType, TypesEnum.BASIC);
		searchService.addToSearch(cardType, TypesEnum.CLASSIC);
		searchService.addToSearch(cardType, TypesEnum.CURSE_OF_NAXXRAMAS);
		searchService.addToSearch(cardType, TypesEnum.GOBLINS_VS_GNOMES);
		searchService.addToSearch(cardType, TypesEnum.PROMOTION);

	}
}
