package com.marekturis.common.infrastructure;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marekturis.common.application.EventJsonSerializer;
import com.marekturis.common.application.JsonSerializationException;
import com.marekturis.common.domain.Event;

import javax.inject.Named;

/**
 * @author Marek Turis
 */
@Named
public class JacksonEventJsonSerializer implements EventJsonSerializer {

	private final ObjectMapper mapper;

	public JacksonEventJsonSerializer() {
		mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
	}

	public String fromEvent(Event event) {
		try {
			return mapper.writeValueAsString(event);
		} catch (JsonProcessingException e) {
			throw new JsonSerializationException(e);
		}
	}
}
