package com.marekturis.common.application.eventforwarding;

import java.util.Date;

/**
 * @author Marek Turis
 */
public interface LatestForwardedEventsProvider {
	Date latestForwardedEvents();
	void setLatestForwardedEvents(Date date);
}
