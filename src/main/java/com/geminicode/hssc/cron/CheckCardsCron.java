package com.geminicode.hssc.cron;

import com.geminicode.hssc.model.CardType;
import com.geminicode.hssc.model.TypesEnum;
import com.geminicode.hssc.service.SearchApiService;
import com.geminicode.hssc.utils.ServiceFactory;
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

    private final SearchApiService searchApiService = ServiceFactory.get().getSearchApiService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		final URL url = new URL(URL_API);
		final BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
		final Gson gson = new Gson();

		final CardType cardType = gson.fromJson(br, CardType.class);

        searchApiService.addToSearch(cardType, TypesEnum.BASIC);
        searchApiService.addToSearch(cardType, TypesEnum.CLASSIC);
        searchApiService.addToSearch(cardType, TypesEnum.CURSE_OF_NAXXRAMAS);
        searchApiService.addToSearch(cardType, TypesEnum.GOBLINS_VS_GNOMES);
        searchApiService.addToSearch(cardType, TypesEnum.PROMOTION);

	}
}
