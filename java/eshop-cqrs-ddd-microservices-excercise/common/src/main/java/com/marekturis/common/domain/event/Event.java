package com.marekturis.common.domain.event;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Marek Turis
 */
public interface Event extends Serializable {
	String identity();
	Date occuredOn();
	String name();
}
