package com.geminicode.hssc.rest;

import com.geminicode.hssc.model.Card;
import com.geminicode.hssc.model.NameCard;
import com.geminicode.hssc.service.DatastoreService;
import com.geminicode.hssc.service.SearchApiService;
import com.geminicode.hssc.utils.ServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class JSONService {

    private final SearchApiService searchApiService = ServiceFactory.get().getSearchApiService();
    private final DatastoreService datastoreService = ServiceFactory.get().getDatastoreService();

	@GET
	@Path("/names")
	public List<NameCard> getNames(@QueryParam("q") String query) {
		return datastoreService.searchNameCards(query);
	}

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
