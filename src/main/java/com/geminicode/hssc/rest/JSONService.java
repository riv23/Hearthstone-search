package com.geminicode.hssc.rest;

import com.geminicode.hssc.model.Card;
import com.geminicode.hssc.service.SearchService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class JSONService {
	private final SearchService searchService = new SearchService();

	@GET
	@Path("/search")
	public List<Card> doQuery(@QueryParam("q") String query) {
		return searchService.search(query);
	}
}
