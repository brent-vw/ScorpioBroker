package eu.neclab.ngsildbroker.registry.subscriptionmanager.messaging;

import jakarta.inject.Singleton;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment.Strategy;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import eu.neclab.ngsildbroker.commons.constants.AppConstants;
import eu.neclab.ngsildbroker.commons.datatypes.requests.BaseRequest;
import eu.neclab.ngsildbroker.commons.datatypes.requests.subscription.SubscriptionRequest;
import eu.neclab.ngsildbroker.commons.tools.MicroServiceUtils;
import io.quarkus.arc.profile.UnlessBuildProfile;
import io.smallrye.mutiny.Uni;

@Singleton
@UnlessBuildProfile("in-memory")
public class RegistrySubscriptionMessagingKafka extends RegistrySubscriptionMessagingBase {

	@ConfigProperty(name = "scorpio.messaging.duplicate", defaultValue = "false")
	boolean duplicate;

	@Incoming(AppConstants.REGISTRY_RETRIEVE_CHANNEL)
	@Acknowledgment(Strategy.PRE_PROCESSING)
	public Uni<Void> handleCsource(BaseRequest busMessage) {
		if (duplicate) {
			return baseHandleCsource(MicroServiceUtils.deepCopyRequestMessage(busMessage));
		}
		return baseHandleCsource(busMessage);
	}

	@Incoming(AppConstants.INTERNAL_RETRIEVE_SUBS_CHANNEL)
	@Acknowledgment(Strategy.PRE_PROCESSING)
	public Uni<Void> handleSubscription(SubscriptionRequest busMessage) {
		if (duplicate) {
			return baseHandleSubscription(MicroServiceUtils.deepCopySubscriptionMessage(busMessage));
		}
		return baseHandleSubscription(busMessage);
	}
}
