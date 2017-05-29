package com.marekturis.common.domain.process;

import java.util.Date;
import java.util.UUID;

/**
 * @author Marek Turis
 */
public abstract class AbstractProcess implements Process {
	private String id;
	private Date startedOn;
	private Date nextTimeOut = new Date();
	private int totalRetriesPermitted;
	private int secondsToTimeOut;
	private int currentAttempt = 0;

	public AbstractProcess(Date startedOn, int totalRetriesPermitted, int secondsToTimeOut) {
		this.id = UUID.randomUUID().toString();
		this.startedOn = startedOn;
		refreshNextTimeOut();
		this.totalRetriesPermitted = totalRetriesPermitted;
		this.secondsToTimeOut = secondsToTimeOut;
	}

	public void refreshNextTimeOut() {
		nextTimeOut.setTime(new Date().getTime() + secondsToTimeOut * 1000);
	}

	public boolean hasTimedOut() {
		return nextTimeOut.before(new Date());
	}

	public void increaseRetries() {
		currentAttempt++;
	}

	public boolean hasTotallyTimedOut() {
		return totalRetriesPermitted <= currentAttempt;
	}

	public String getId() {
		return id;
	}
}
