package com.marekturis.common.domain.aggregate;

import com.marekturis.common.domain.event.Event;

import java.io.Serializable;
import java.util.List;

/**
 * @author Marek Turis
 */
public interface AggregateRoot extends Serializable {
	Integer identity();
	void replayEvent(Event event);

	/**
	 * Return all changes, oldest first
	 * @return
	 */
	List<Event> changes();
	int currentVersion();
	int versionWhenLoaded();
}
