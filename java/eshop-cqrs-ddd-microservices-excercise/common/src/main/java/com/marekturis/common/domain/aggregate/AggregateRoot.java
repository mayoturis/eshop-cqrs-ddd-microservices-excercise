package com.marekturis.common.domain.aggregate;

import com.marekturis.common.domain.event.AggregateEvent;

import java.io.Serializable;
import java.util.List;

/**
 * @author Marek Turis
 */
public interface AggregateRoot extends Serializable {
	Integer identity();
	void replayEvent(AggregateEvent event);

	/**
	 * Return all pullChanges, oldest first
	 * @return
	 */
	List<AggregateEvent> pullChanges();
	int currentVersion();
	int versionWhenLoaded();

	/**
	 * Used just for loading aggregates
	 *
	 * @param version
	 */
	void setVersions(int version);
}
