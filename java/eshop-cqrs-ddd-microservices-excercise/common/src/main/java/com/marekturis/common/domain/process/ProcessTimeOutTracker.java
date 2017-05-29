package com.marekturis.common.domain.process;

/**
 * @author Marek Turis
 */
public class ProcessTimeOutTracker {

	private Process process;

	public ProcessTimeOutTracker(Process process) {
		this.process = process;
	}

	public boolean hasTimedOut() {
		return process.hasTimedOut();
	}
}
