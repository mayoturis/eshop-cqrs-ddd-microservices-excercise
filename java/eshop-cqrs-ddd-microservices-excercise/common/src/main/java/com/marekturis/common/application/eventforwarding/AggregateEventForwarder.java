package com.marekturis.common.application.eventforwarding;

import com.marekturis.common.domain.event.AggregateEvent;
import com.marekturis.common.domain.event.AggregateEventStore;
import com.marekturis.common.domain.event.EventPublisher;
import org.springframework.scheduling.annotation.Scheduled;

import javax.inject.Named;
import java.util.Date;
import java.util.List;

/**
 * @author Marek Turis
 */
@Named
public class AggregateEventForwarder {

	private LatestForwardedEventsProvider latestForwardedEventsProvider;
	private AggregateEventStore eventStore;
	private EventPublisher eventPublisher;

	public AggregateEventForwarder(
			LatestForwardedEventsProvider latestForwardedEventsProvider,
			AggregateEventStore eventStore,
			EventPublisher eventPublisher) {
		this.latestForwardedEventsProvider = latestForwardedEventsProvider;
		this.eventStore = eventStore;
		this.eventPublisher = eventPublisher;
	}

	@Scheduled(fixedRate = 5000)
	public void forwardEvents() {
		Date latestsForwardedEventsTime = latestForwardedEventsProvider.latestForwardedEvents();
		List<AggregateEvent> events = eventStore.getEventsNewerThan(latestsForwardedEventsTime);
		Date currentTime = new Date();

		for (AggregateEvent event : events) {
			eventPublisher.publish(event);
		}

		latestForwardedEventsProvider.setLatestForwardedEvents(currentTime);
	}
}
