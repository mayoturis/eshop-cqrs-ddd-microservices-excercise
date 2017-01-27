package com.marekturis.common;

import com.marekturis.common.application.AggregateJsonSerializer;
import com.marekturis.common.domain.aggregate.AggregateRoot;
import com.marekturis.common.domain.event.Event;
import com.marekturis.common.domain.event.EventHandler;
import com.marekturis.common.domain.event.EventPublisher;
import com.marekturis.common.domain.event.ParsableEvent;
import com.marekturis.common.infrastructure.JacksonAggregateJsonSerializer;
import com.marekturis.common.infrastructure.JacksonEventJsonSerializer;
import com.marekturis.common.infrastructure.messaging.RabbitMQEventPublisher;
import org.springframework.util.SerializationUtils;

import java.util.Date;

/**
 * @author Marek Turis
 */
public class Main {

	public static void main(String[] args) throws Exception {
		/*try (EventPublisher eventPublisher = new RabbitMQEventPublisher(new JacksonEventJsonSerializer())) {

			Event event = new SomeEvent();
			EventHandler handler = new SomeEventHandler();
			//eventPublisher.registerHandler(handler);
			eventPublisher.publish(event);
		}*/
		byte[] data = SerializationUtils.serialize(new SomeAggregate());
		AggregateRoot yourObject = (AggregateRoot) SerializationUtils.deserialize(data);
	}

	public static class SomeAggregate implements AggregateRoot {
		private String neco = "nieco";
		private Event event = new SomeEvent();
	}

	public static class SomeEvent implements Event {

		private Date occuredOn = new Date();
		private String name = "ProductCreated";
		private int nieco = 5;

		public String identity() { return "d"; }

		public Date occuredOn() {
			return new Date();
		}

		public String name() {
			return name;
		}
	}

	public static class SomeEventHandler implements EventHandler {

		public String eventToHandle() {
			return "namo";
		}

		public void handle(ParsableEvent event) {
			System.out.println(event.occuredOn());
			System.out.println(event.name());
		}
	}
}
