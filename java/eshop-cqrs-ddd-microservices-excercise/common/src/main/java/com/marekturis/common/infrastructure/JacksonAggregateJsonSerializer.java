package com.marekturis.common.infrastructure;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marekturis.common.application.AggregateJsonSerializer;
import com.marekturis.common.application.JsonSerializationException;
import com.marekturis.common.domain.aggregate.AggregateRoot;

import java.io.IOException;

/**
 * @author Marek Turis
 */
public class JacksonAggregateJsonSerializer implements AggregateJsonSerializer {

	private final ObjectMapper mapper;

	public JacksonAggregateJsonSerializer() {
		mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
	}

	@Override
	public String fromAggregate(AggregateRoot aggregateRoot) {
		try {
			return mapper.writeValueAsString(aggregateRoot);
		} catch (JsonProcessingException e) {
			throw new JsonSerializationException(e);
		}
	}

	@Override
	public <TAggregateRoot> TAggregateRoot fromString(String string, Class<TAggregateRoot> type) {
		try {
			return mapper.readValue(string, type);
		} catch (IOException e) {
			throw new JsonSerializationException(e);
		}
	}
}
