package com.geminicode.hssc.rest;

import com.geminicode.hssc.model.Card;
import com.geminicode.hssc.service.SearchApiService;
import com.geminicode.hssc.utils.ServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class JSONService {

    private final SearchApiService searchApiService = ServiceFactory.get().getSearchApiService();

	@GET
	@Path("/search")
	public List<Card> doQuery(@QueryParam("q") String query) {
		return searchApiService.search(query);
	}

    @GET
    @Path("/card/{id}")
    public Card getCard(@PathParam("id") String cardId) {
        return searchApiService.searchById(cardId);
    }
}
