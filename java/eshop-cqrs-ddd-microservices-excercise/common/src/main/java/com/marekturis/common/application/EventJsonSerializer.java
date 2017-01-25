package com.marekturis.common.application;

import com.marekturis.common.domain.Event;

/**
 * @author Marek Turis
 */
public interface EventJsonSerializer {
	String fromEvent(Event event);
}
