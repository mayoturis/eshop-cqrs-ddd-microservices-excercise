package com.marekturis.common.domain.event;

import java.util.Date;
import java.util.UUID;

/**
 * @author Marek Turis
 */
public class EventBase implements Event {

	private String identity;
	private Date occuredOn;

	public EventBase() {
		occuredOn = new Date();
		identity = UUID.randomUUID().toString();
	}

	@Override
	public String identity() {
		return identity;
	}

	@Override
	public Date occuredOn() {
		return occuredOn;
	}

	@Override
	public String name() {
		return getClass().getName();
	}
}
