package com.geminicode.hssc.rest;

import com.geminicode.hssc.model.Card;
import com.geminicode.hssc.model.Message;
import com.geminicode.hssc.model.NameCard;
import com.geminicode.hssc.service.DatastoreService;
import com.geminicode.hssc.service.MailService;
import com.geminicode.hssc.service.SearchApiService;
import com.geminicode.hssc.utils.ServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * This class class expose the API
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class RESTService {

    private final SearchApiService searchApiService = ServiceFactory.get().getSearchApiService();
    private final DatastoreService datastoreService = ServiceFactory.get().getDatastoreService();
    private final MailService mailService = ServiceFactory.get().getMailService();

	@GET
	@Path("/names")
	public List<NameCard> getNames(@QueryParam("q") String query, @QueryParam("lang") String lang) {
		return datastoreService.searchNameCards(query, lang);
	}

    @GET
    @Path("/search")
    public List<Card> doQuery(@QueryParam("q") String query, @QueryParam("lang") String lang, @QueryParam("cost") String cost) {
        return searchApiService.search(query, lang, cost);
    }

    @GET
    @Path("/card/{id}")
    public Card getCard(@PathParam("id") String cardId, @QueryParam("lang") String lang) {
        return searchApiService.searchById(cardId, lang);
    }

    @POST
    @Path("/message")
    @Consumes(MediaType.APPLICATION_JSON)
    public void sendMessage(Message message) {
        mailService.send(message.getName(), message.getEmail(), message.getMessage());
    }
}
