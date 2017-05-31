package com.marekturis.common.domain.process;

import com.marekturis.common.domain.event.EventBase;

/**
 * @author Marek Turis
 */
public abstract class AbstractProcessTimedOutEvent extends EventBase implements ProcessTimedOutEvent {

	private boolean totallyTimedOut;

	public AbstractProcessTimedOutEvent(boolean totallyTimedOut) {
		this.totallyTimedOut = totallyTimedOut;
	}

	@Override
	public boolean hasTotallyTimedOut() {
		return totallyTimedOut;
	}
}
