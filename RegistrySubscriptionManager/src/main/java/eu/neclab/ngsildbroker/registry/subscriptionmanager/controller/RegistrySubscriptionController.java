package eu.neclab.ngsildbroker.registry.subscriptionmanager.controller;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.reactive.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jsonldjava.core.JsonLdProcessor;

import eu.neclab.ngsildbroker.commons.constants.AppConstants;
import eu.neclab.ngsildbroker.commons.controllers.SubscriptionControllerFunctions;
import eu.neclab.ngsildbroker.registry.subscriptionmanager.service.RegistrySubscriptionService;
import io.vertx.core.http.HttpServerRequest;

@Singleton
@Path("/ngsi-ld/v1/csourceSubscriptions")
public class RegistrySubscriptionController {
	
	private final static Logger logger = LoggerFactory.getLogger(RegistrySubscriptionController.class);

	@Inject
	RegistrySubscriptionService manager;

	@ConfigProperty(name = "ngsild.corecontext", defaultValue = "https://uri.etsi.org/ngsi-ld/v1/ngsi-ld-core-context.jsonld")
	String coreContext;

	@ConfigProperty(name = "scorpio.subscription.default-limit", defaultValue = "50")
	private int defaultLimit;
	@ConfigProperty(name = "scorpio.subscription.max-limit", defaultValue = "1000")
	private int maxLimit;

	@PostConstruct
	public void init() {
		JsonLdProcessor.init(coreContext);
	}

	@POST
	public RestResponse<Object> subscribeRest(HttpServerRequest request, String payload) {
		return SubscriptionControllerFunctions.subscribeRest(manager, request, payload,
				AppConstants.CSOURCE_SUBSCRIPTIONS_URL, logger);
	}

	@GET
	public RestResponse<Object> getAllSubscriptions(HttpServerRequest request) {
		return SubscriptionControllerFunctions.getAllSubscriptions(manager, request, defaultLimit, maxLimit, logger);
	}

	@Path("/{id}")
	@GET
	public RestResponse<Object> getSubscriptionById(HttpServerRequest request, @PathParam(value = "id") String id,
			@QueryParam(value = "limit") int limit) {
		return SubscriptionControllerFunctions.getSubscriptionById(manager, request, id, limit, logger);

	}

	@Path("/{id}")
	@DELETE
	public RestResponse<Object> deleteSubscription(HttpServerRequest request, @PathParam(value = "id") String id) {
		return SubscriptionControllerFunctions.deleteSubscription(manager, request, id, logger);
	}

	@Path("/{id}")
	@PATCH
	public RestResponse<Object> updateSubscription(HttpServerRequest request, @PathParam(value = "id") String id,
			String payload) {
		return SubscriptionControllerFunctions.updateSubscription(manager, request, id, payload, logger);
	}

}