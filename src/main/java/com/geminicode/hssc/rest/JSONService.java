package com.geminicode.hssc.rest;

import com.geminicode.hssc.model.Card;
import com.geminicode.hssc.service.CardDataStoreService;
import com.geminicode.hssc.service.SearchService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class JSONService {
	private final SearchService searchService = new SearchService();
    private final CardDataStoreService cardDataStoreService = new CardDataStoreService();

	@GET
	@Path("/search")
	public List<Card> doQuery(@QueryParam("q") String query) {
		return searchService.search(query);
	}

    @GET
    @Path("/card/{id}")
    public Card getCard(@PathParam("id") String cardId) {
        return cardDataStoreService.getCard(cardId);
    }
}
