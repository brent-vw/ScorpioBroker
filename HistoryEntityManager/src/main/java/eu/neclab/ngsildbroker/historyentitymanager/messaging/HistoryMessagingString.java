package eu.neclab.ngsildbroker.historyentitymanager.messaging;

import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment.Strategy;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import eu.neclab.ngsildbroker.commons.constants.AppConstants;
import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.scheduler.Scheduled;
import io.smallrye.common.annotation.RunOnVirtualThread;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Singleton;

@Singleton
@IfBuildProfile(anyOf = { "sqs", "kafka" })
public class HistoryMessagingString extends HistoryMessagingBase {

	@Incoming(AppConstants.REGISTRY_RETRIEVE_CHANNEL)
	@Acknowledgment(Strategy.PRE_PROCESSING)
	public Uni<Void> handleCsource(String byteMessage) {
		return handleCsourceRaw(byteMessage);
	}

	@Incoming(AppConstants.ENTITY_RETRIEVE_CHANNEL)
	@Acknowledgment(Strategy.PRE_PROCESSING)
	public Uni<Void> handleEntity(String byteMessage) {
		return handleEntityRaw(byteMessage);
	}

	@Incoming(AppConstants.ENTITY_BATCH_RETRIEVE_CHANNEL)
	@Acknowledgment(Strategy.PRE_PROCESSING)
	public Uni<Void> handleBatchEntities(String byteMessage) {
		return handleBatchEntitiesRaw(byteMessage);
	}

	@Scheduled(every = "20s", delayed = "${scorpio.startupdelay}")
	void purge() {
		super.purge();
	}

	@Scheduled(every = "5s", delayed = "${scorpio.startupdelay}")
	Uni<Void> checkBuffer() {
		return super.checkBuffer();
	}

}
