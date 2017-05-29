package com.marekturis.common.domain.event;

import java.util.Date;

/**
 * @author Marek Turis
 */
public interface ParsableEvent extends Event {
	String getString(String key);
	int getInt(String key);
	double getDouble(String key);
	Date getDate(String key);
	boolean getBoolean(String key);
}
