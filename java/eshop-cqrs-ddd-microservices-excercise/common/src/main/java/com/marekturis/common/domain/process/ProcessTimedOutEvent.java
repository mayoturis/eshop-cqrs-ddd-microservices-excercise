package com.marekturis.common.domain.process;

import com.marekturis.common.domain.event.Event;

/**
 * @author Marek Turis
 */
public interface ProcessTimedOutEvent extends Event {
	boolean hasTotallyTimedOut();
}
